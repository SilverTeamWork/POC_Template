package org.silverpeas.poc.client.local.blog.post;

import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.util.Messages;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.WysiwygEditor;
import org.silverpeas.poc.client.local.widget.comment.CommentWidget;
import org.silverpeas.poc.client.local.widget.rating.RatingWidget;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author ebonnet
 */
@Templated("Post.html#blogPost")
public class PostWidget extends Composite implements HasModel<Post> {

  private Post post;

  @Inject
  @DataField("comment-count")
  private InlineLabel postNbComment;

  @Inject
  @DataField("post-create-label")
  private InlineLabel postCreateLabel;

  @Inject
  @DataField("post-update-label")
  private InlineLabel postUpdateLabel;

  @Inject
  @DataField("post-author")
  private InlineLabel postAuthor;

  @Inject
  @DataField("post-updater")
  private InlineLabel postUpdater;

  @DataField("post-create-date")
  private Element postCreateDate = DOM.createElement("time");

  @DataField("post-update-date")
  private Element postUpdateDate = DOM.createElement("time");

  @Inject
  @DataField("post-rating")
  private RatingWidget ratingView;

  @DataField("post-content")
  private SilverpeasHtmlPanel content = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.DIV);

  private boolean edition = false;
  private WysiwygEditor editor = null;

  public void setEditionMode() {
    edition = true;
  }

  public boolean isInEdition() {
    return edition;
  }

  @Inject
  @DataField
  private CommentWidget commentWidget;

  public Post getModel() {
    return post;
  }

  @Override
  public void setModel(final Post post) {
    this.post = post;
    initViewFromModel();
  }

  private void initViewFromModel() {
    switchEditionMode(isInEdition());
    Date dateEvent = post.getDateEvent();
    Log.dev("custom format " + I18n.format(Messages.DATE_FORMAT) + " =" +
        DateTimeFormat.getFormat(I18n.format(Messages.DATE_FORMAT)).format(dateEvent));

    this.postNbComment.setText(post.getNbComments());
    Log.dev("create by=" + I18n.format(Messages.COMMON_BY) + " " + post.getCreator());
    this.postAuthor.setText(I18n.format(Messages.COMMON_BY) + " " + post.getCreator());
    this.postUpdater.setText(I18n.format(Messages.COMMON_BY) + " " + post.getUpdater());
    Date jsCreateDate = new Date(Long.parseLong("" + post.getCreationTimestamp()));
    this.postCreateDate.setAttribute("datetime",
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(jsCreateDate));
    this.postCreateDate.setInnerText(
        DateTimeFormat.getFormat(I18n.format(Messages.DATETIME_FORMAT)).format(jsCreateDate));

    Date jsUpdateDate = new Date(Long.parseLong("" + post.getUpdateTimestamp()));
    this.postUpdateDate.setAttribute("datetime",
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(jsUpdateDate));
    this.postUpdateDate.setInnerText(
        DateTimeFormat.getFormat(I18n.format(Messages.DATETIME_FORMAT)).format(jsUpdateDate));
    this.postCreateLabel.setText(I18n.format(Messages.PUBLISH_DATE_LABEL));
    this.postUpdateLabel.setText(I18n.format(Messages.UPDATE_DATE_LABEL));
    loadRating();
    loadComments();
  }

  public void switchEditionMode(boolean edition) {
    this.edition = edition;
    content.clear();
    if (isInEdition()) {
      loadWysiwygEditor();
    } else {
      if (editor != null) {
        editor.removeFromParent();
        editor = null;
      }
      HTML text = new HTML(getModel().getContent());
      content.add(text);
    }
  }

  private void loadRating() {
    // TODO load notation from rating services
    // URL is something like /services/rating/{blogId}/Publication/{postId}
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Rating rating = response.parseJsonEntity();
        ratingView.setModel(rating);
      }
    }).get(RatingCriteria.forPublication(this.post.getAppInstanceId(), this.post.getId()));
  }

  private WysiwygEditor loadWysiwygEditor() {
    if (editor == null) {
      editor = new WysiwygEditor(getModel().getContent());
      editor.setTextSaveHandler(new WysiwygEditor.TextSaveHandler() {
        @Override
        public void onTextSave(final String text) {
          if (!getModel().getContent().equals(text)) {
            getModel().setContent(text);
            JsonHttp.onSuccess(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                Post updatedPost = response.parseJsonEntity();
                Log.dev("Content of post " + updatedPost.getId() + " from blog " +
                    updatedPost.getAppInstanceId() + " updated");
                setModel(updatedPost);
              }
            }).withData(getModel()).put(PostCriteria.fromPost(getModel()));
          }
        }
      });
      content.add(editor);
    }
    return editor;
  }

  private void loadComments() {
    commentWidget.initCommentWidget(this.post.getAppInstanceId(), this.post.getId(),
        CurrentUser.get().getId(), UrlManager.getSilverpeasUrl(CurrentUser.get().getAvatar()), true,
        CurrentUser.token());
  }

}

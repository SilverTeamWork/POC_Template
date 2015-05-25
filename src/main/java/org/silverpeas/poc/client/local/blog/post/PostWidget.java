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
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.util.Messages;
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
  @DataField("post-content")
  private HTML postContent;

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


  public Post getModel() {
    return post;
  }

  @Override
  public void setModel(final Post post) {
    this.post = post;
    this.postContent.setHTML(post.getContent());
    Date dateEvent = post.getDateEvent();
    /*
    Log.dev("dateEvent = " + post.getDateEvent());
    Log.dev("today = " + new Date());
    Log.dev("Date(dateEvent) = " + new Date(Long.parseLong("" + post.getDateEvent())));
    Log.dev("currentTimeMillis = " + System.currentTimeMillis());
    Log.dev("today(currentTimeMillis) = " + new Date(System.currentTimeMillis()));
    Log.dev("" +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_FULL).format(jsDateEvent));
    Log.dev("" +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_LONG).format(jsDateEvent));
    Log.dev("" +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_MEDIUM).format(jsDateEvent));
    Log.dev("" +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(jsDateEvent));
    Log.dev("custom format =" +
        DateTimeFormat.getFormat("dd/MM/yyyy").format(jsDateEvent));
    */
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
}

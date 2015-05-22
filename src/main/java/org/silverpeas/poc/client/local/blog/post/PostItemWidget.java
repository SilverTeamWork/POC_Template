package org.silverpeas.poc.client.local.blog.post;

import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.util.AccessController;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.util.ValueChangeHandler;
import org.silverpeas.poc.client.local.widget.date.DateWidget;
import org.silverpeas.poc.client.local.widget.rating.RatingWidget;
import org.silverpeas.poc.client.local.widget.title.EditableTitleWidget;

import javax.inject.Inject;
import java.util.Date;

/**
 * A widget to render an excerpt of a blog content with global information about it (rating,
 * comments count, ...).
 * @author miguel
 */
@Templated
public class PostItemWidget extends Composite implements HasModel<Post> {

  @Inject
  @DataField("post-date")
  private DateWidget time;

  @Inject
  @DataField("post-title")
  private EditableTitleWidget title;

  @Inject
  @DataField("content-from-wysiwyg")
  private HTML content;

  @Inject
  @DataField("post-rating")
  private RatingWidget ratingView;

  @DataField("comment-count")
  private Element commentCount = DOM.createSpan();

  @DataField("publication")
  private Element publicationInfo = DOM.createSpan();

  @DataField("sep")
  private Element publicationContentSeparator = DOM.createSpan();

  @DataField("modification")
  private Element modificationInfo = DOM.createSpan();

  @DataField
  private Element operation = DOM.createDiv();

  private Post post;

  @Override
  public Post getModel() {
    return this.post;
  }

  @Override
  public void setModel(final Post post) {
    this.post = post;
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Rating rating = response.parseJsonEntity();
        ratingView.readonly().setModel(rating);
      }
    }).get(RatingCriteria.forPublication(post.getAppInstanceId(), post.getId()));
    title.setModel(this.post);
    title.setValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onChange(final String previousValue, final String newValue) {
        getModel().setTitle(newValue);
        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            Post post = response.parseJsonEntity();
            title.setModel(post);
          }
        })
            .onError(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                getModel().setTitle(previousValue);
              }
            })
            .withData(getModel())
            .put(PostCriteria.fromIds(getModel().getAppInstanceId(), getModel().getId()));
      }
    });
    time.setModel(
        new Date((long) this.post.getCreationTimestamp())); // long is simulated in js by a double with GWT
    commentCount.setInnerText(String.valueOf(post.getNbComments()));
    fillContentSnippet();
    fillPublicationInfo();
    fillModificationInfo();
    AccessController.on(this.post).doUnPrivileged(new AccessController.Action() {
      @Override
      public void run() {
        operation.removeFromParent();
      }
    });
  }

  private void fillPublicationInfo() {
    StringBuilder content = new StringBuilder();
    Date creationDate = this.post.getCreationDate();
    String dateTime =
        "<time datetime='" + DateTimeFormat.getFormat("yyyy-MM-dd").format(creationDate) + "'>" +
            DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT)
                .format(creationDate) + "</time>";
    content.append(I18n.format(CommonTranslations.PUBLICATION_DATE, dateTime))
        .append(" <span class='author'>")
        .append(I18n.format(CommonTranslations.PUBLICATION_AUTHOR, post.getCreator()))
        .append("</span>");
    publicationInfo.setInnerHTML(content.toString());
  }

  private void fillModificationInfo() {
    StringBuilder content = new StringBuilder();
    if (this.post.getCreationDate().compareTo(this.post.getUpdateDate()) < 0) {
      Date modificationDate = this.post.getUpdateDate();
      String dateTime =
          "<time datetime='" + DateTimeFormat.getFormat("yyyy-MM-dd").format(modificationDate) +
              "'>" +
              DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT)
                  .format(modificationDate) + "</time>";
      content.append(I18n.format(CommonTranslations.MODIFICATION_DATE, dateTime))
          .append(" <span class='author'>")
          .append(I18n.format(CommonTranslations.MODIFICATION_AUTHOR, post.getUpdater()))
          .append("</span>");
      modificationInfo.setInnerHTML(content.toString());
    } else {
      publicationContentSeparator.removeFromParent();
      modificationInfo.removeFromParent();
    }
  }

  private void fillContentSnippet() {
    String snippet = this.post.getContent();
    int idx = snippet.length();
    for (int i = snippet.indexOf(". ", 0), p = 0; p < 5 && i != -1;
         i = snippet.indexOf(". ", idx)) {
      idx = i + 1;
    }
    String htmlSnippet = snippet.substring(0, idx);
    content.setHTML(StringUtil.isDefined(htmlSnippet) ? htmlSnippet : "<br/><br/>");
  }

}

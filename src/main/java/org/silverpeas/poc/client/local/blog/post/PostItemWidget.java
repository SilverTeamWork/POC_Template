package org.silverpeas.poc.client.local.blog.post;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.widget.date.DateWidget;
import org.silverpeas.poc.client.local.widget.rating.RatingWidget;

import javax.inject.Inject;
import java.util.Date;

/**
 * @author miguel
 */
@Templated
public class PostItemWidget extends Composite implements HasModel<Post> {

  @Inject
  private TranslationService translation;

  @Inject
  @DataField("post-date")
  private DateWidget time;

  @DataField("post-title")
  private Element title = DOM.createSpan();

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
        Rating rating = JsonUtils.safeEval(response.getText());
        ratingView.readonly().setModel(rating);
      }
    }).get(RatingCriteria.forPublication(post.getComponentId(), post.getId()));
    title.setInnerText(post.getTitle());
    time.setModel(
        new Date((long) this.post.getCreateDate())); // long is simulated in js by a double with GWT
    commentCount.setInnerText(post.getNbComments());
    fillContentSnippet();
    fillPublicationInfo();
    fillModificationInfo();
  }

  private void fillPublicationInfo() {
    StringBuilder content = new StringBuilder();
    Date creationDate = new Date((long) this.post.getCreateDate());
    String dateTime =
        "<time datetime='" + DateTimeFormat.getFormat("yyyy-MM-dd").format(creationDate) + "'>" +
            DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT)
                .format(creationDate) + "</time>";
    content.append(translation.format(CommonTranslations.PUBLICATION_DATE, dateTime))
        .append(" <span class='author'>")
        .append(translation.format(CommonTranslations.PUBLICATION_AUTHOR, post.getCreator()))
        .append("</span>");
    publicationInfo.setInnerHTML(content.toString());
  }

  private void fillModificationInfo() {
    StringBuilder content = new StringBuilder();
    if (this.post.getCreateDate() < this.post.getUpdateDate()) {
      Date modificationDate = new Date((long) this.post.getUpdateDate());
      String dateTime =
          "<time datetime='" + DateTimeFormat.getFormat("yyyy-MM-dd").format(modificationDate) +
              "'>" +
              DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT)
                  .format(modificationDate) + "</time>";
      content.append(translation.format(CommonTranslations.MODIFICATION_DATE, dateTime))
          .append(" <span class='author'>")
          .append(translation.format(CommonTranslations.MODIFICATION_AUTHOR, post.getUpdater()))
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
    content.setHTML(snippet.substring(0, idx));
  }
}

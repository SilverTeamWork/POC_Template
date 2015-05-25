package org.silverpeas.poc.client.local.blog.post;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.template.BlogPageComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.calendar.DatePickerWidget;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}/post/{postId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogPostPage extends BlogPageComposite {

  @PageState
  private String postId;

  @Inject
  @DataField("blog-post")
  private SilverpeasHtmlPanel postPanel;

  @Inject
  private DatePickerWidget datePickerWidget;

  @Inject
  private PostWidget postWidget;

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
    setPageDescription(null);
    getFooterPanel().setVisible(false);
    getRightPanel().add(datePickerWidget);
    postPanel.add(postWidget);

    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Post post = response.parseJsonEntity();
        postWidget.setModel(post);
        setPageTitle(post.getTitle());
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.debug("Error while getting blog post: " + response.getStatusText());
      }
    }).get(PostCriteria.fromIds(instance.getId(), postId));

    // Posts to indicate into the calendar
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        final Set<Date> postDates = new HashSet<>();
        response.parseJsonEntities(new HttpResponse.JsonArrayLine<Post>() {
          @Override
          public void perform(final int index, final Post entity) {
            postDates.add(entity.getCreationDate());
          }
        });
        datePickerWidget.setDatesToHighlight(postDates);
      }
    }).get(PostCriteria.fromBlog(instance.getId()));
  }
}

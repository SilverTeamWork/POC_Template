package org.silverpeas.poc.client.local.blog.home;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.post.PostItemWidget;
import org.silverpeas.poc.client.local.blog.template.BlogPageComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.widget.calendar.DatePickerWidget;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends BlogPageComposite {

  @Inject
  @DataField("blog")
  private ListWidget<Post, PostItemWidget> postsView;

  @Inject
  private DatePickerWidget datePickerWidget;

  private int highestScroll = 0;
  private int scrollIncrement = 100;
  private int page = 1;

  @AfterInitialization
  protected void init() {
    Window.addWindowScrollHandler(new Window.ScrollHandler() {
      @Override
      public void onWindowScroll(final Window.ScrollEvent event) {
        if (postsView.getValue().isEmpty()) {
          return;
        } else if (highestScroll == 0) {
          highestScroll = (postsView.getWidgetCount() - 2) * scrollIncrement;
        }
        Document document = Document.get();
        int scroll = document.getScrollTop();

        if ((highestScroll + scrollIncrement) >= scroll) {
          return;
        }
        highestScroll = scroll;

        ApplicationInstance instance = getApplicationInstance();
        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            List<Post> newPosts = response.parseJsonEntities();
            postsView.getValue().addAll(newPosts);
          }
        }).get(PostCriteria.fromBlog(instance.getId()).paginatedBy(page++, 1));
      }
    });
  }

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
    getRightPanel().add(datePickerWidget);
    setPageTitle(instance.getLabel());
    setPageDescription(instance.getDescription());

    // Posts to display
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        List<Post> posts = response.parseJsonEntities();
        postsView.setItems(posts);
        page += 3;
      }
    }).get(PostCriteria.fromBlog(instance.getId()).paginatedBy(page, 3));

    // Posts to indicate into the calendar
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        final Set<Date> postDates = new HashSet<>();
        response.parseJsonEntities(new HttpResponse.JsonArrayLine<Post>() {
          @Override
          public void perform(final int index, final Post entity) {
            postDates.add(entity.getDateEvent());
          }
        });
        datePickerWidget.setDatesToHighlight(postDates);
      }
    }).get(PostCriteria.fromBlog(instance.getId()));
  }


}

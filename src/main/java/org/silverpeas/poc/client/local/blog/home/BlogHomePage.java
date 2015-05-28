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
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.bundle.BlogBundle;
import org.silverpeas.poc.client.local.blog.post.BlogPostPage;
import org.silverpeas.poc.client.local.blog.post.PostItemWidget;
import org.silverpeas.poc.client.local.blog.template.BlogPageComposite;
import org.silverpeas.poc.client.local.blog.widget.BlogDatePickerWidget;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.util.ContributionList;
import org.silverpeas.poc.client.local.widget.menu.ToPageAction;

import javax.inject.Inject;
import java.util.List;

import static org.silverpeas.poc.client.local.widget.menu.MenuAction.TYPE.CREATION;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends BlogPageComposite {

  private static final int INITIAL_POSTS_COUNT = 5;
  private static final int POSTS_COUNT_PER_SCROLL = 1;
  private static final int SCROLL_INCREMENT = 100;

  @Inject
  @DataField("blog")
  private ListWidget<Post, PostItemWidget> postsView;

  @Inject
  private BlogDatePickerWidget blogDatePickerWidget;

  private ToPageAction createPostAction;

  private int highestScroll = 0;
  private int page = 1;
  private boolean parallelLock = false;

  @AfterInitialization
  protected void init() {
    Window.addWindowScrollHandler(new Window.ScrollHandler() {
      @Override
      public void onWindowScroll(final Window.ScrollEvent event) {
        if (!parallelLock) {
          parallelLock = true;
          if (postsView.getValue().isEmpty()) {
            return;
          } else if (highestScroll == 0) {
            highestScroll = (postsView.getWidgetCount() - 2) * SCROLL_INCREMENT;
          }
          Document document = Document.get();
          int scroll = document.getScrollTop();

          if (highestScroll >= scroll) {
            parallelLock = false;
            return;
          }
          highestScroll = scroll;

          ApplicationInstance instance = getApplicationInstance();
          JsonHttp.onSuccess(new JsonResponse() {
            @Override
            public void process(final HttpResponse response) {
              List<Post> newPosts = response.<ContributionList<Post>>parseJsonEntity().getEntities();
              postsView.getValue().addAll(newPosts);
              page += POSTS_COUNT_PER_SCROLL;
              parallelLock = false;
            }
          }).get(PostCriteria.fromBlog(instance.getId()).paginatedBy(page, POSTS_COUNT_PER_SCROLL));
        }
      }
    });
  }

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
    createPostAction =
        getMenuWidget().addPrivilegedToPageAction().ofType(CREATION, BlogBundle.msg().post("a"))
            .toPage(BlogPostPage.class);
    createPostAction.getParameters().putAll(NavigationProvider.get().getCurrentPageState());
    createPostAction.getParameters().get("postId").clear();
    createPostAction.getParameters().put("postId", "new");

    getRightPanel().add(blogDatePickerWidget);
    setPageTitle(instance.getLabel());
    setPageDescription(instance.getDescription());

    //Initialize highest scroll for each display
    highestScroll = 0;

    // Posts to display
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        ContributionList<Post> posts = response.parseJsonEntity();
        postsView.setItems(posts.getEntities());

        // Verify if this action is possible according to list privileges
        createPostAction.verify(posts);
      }
    }).get(PostCriteria.fromBlog(instance.getId()).paginatedBy(page, INITIAL_POSTS_COUNT));
    page += INITIAL_POSTS_COUNT;

    // Posts to indicate into the calendar
    blogDatePickerWidget.forceLoadFor(instance);
  }
}

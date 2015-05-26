package org.silverpeas.poc.client.local.blog.post;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.DisplayedInternalApplicationInstancePage;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.bundle.BlogBundle;
import org.silverpeas.poc.client.local.blog.home.BlogHomePage;
import org.silverpeas.poc.client.local.blog.template.BlogPageComposite;
import org.silverpeas.poc.client.local.blog.widget.BlogDatePickerWidget;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.util.EventsProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.menu.ClickAction;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.widget.menu.MenuAction.TYPE.MODIFICATION;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}/post/{postId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogPostPage extends BlogPageComposite {
  private final BlogPostPage me = this;

  @PageState
  private String postId;

  @Inject
  @DataField("blog-post")
  private SilverpeasHtmlPanel postPanel;

  @Inject
  private BlogDatePickerWidget blogDatePickerWidget;

  @Inject
  private PostWidget postWidget;

  private ClickAction modifyPostAction;

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
    modifyPostAction = getMenuWidget().addPrivilegedClickAction()
        .ofType(MODIFICATION, BlogBundle.msg().post("the"));

    setPageDescription(null);
    getFooterPanel().setVisible(false);
    getRightPanel().add(blogDatePickerWidget);
    postPanel.add(postWidget);

    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Post post = response.parseJsonEntity();
        postWidget.setModel(post);
        setPageTitle(post.getTitle());

        EventsProvider.get().getDisplayedInternalApplicationInstancePageEvent()
            .fire(new DisplayedInternalApplicationInstancePage(me));

        // Verify that the modify action can be performed
        modifyPostAction.verify(post);

      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.debug("Error while getting blog post: " + response.getStatusText());
        Message.notifies("The post does not exist...").error(new Callback() {
          @Override
          public void invoke(final Object... parameters) {
            NavigationProvider navigation = NavigationProvider.get();
            navigation.goTo(BlogHomePage.class, navigation.getCurrentPageState());
          }
        });
      }
    }).get(PostCriteria.fromIds(instance.getId(), postId));

    // Posts to indicate into the calendar
    blogDatePickerWidget.loadFor(instance);
  }
}

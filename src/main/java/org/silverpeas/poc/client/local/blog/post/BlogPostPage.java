package org.silverpeas.poc.client.local.blog.post;

import com.google.common.collect.ImmutableMultimap;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.event.widget.ResourceSaveEvent;
import org.silverpeas.poc.api.event.widget.ResourceSaveHandler;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.bundle.BlogBundle;
import org.silverpeas.poc.client.local.blog.home.BlogHomePage;
import org.silverpeas.poc.client.local.blog.template.BlogPageComposite;
import org.silverpeas.poc.client.local.blog.widget.BlogDatePickerWidget;
import org.silverpeas.poc.client.local.breadcrumb.ToPageBreadCrumbItem;
import org.silverpeas.poc.client.local.util.BundleProvider;
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

  @PageState
  private String postId;

  @PageState
  private String state;

  @Inject
  @DataField("blog-post")
  private SilverpeasHtmlPanel postPanel;

  @Inject
  private BlogDatePickerWidget blogDatePickerWidget;

  @Inject
  private PostWidget postWidget;

  private ClickAction modifyPostAction;

  private ToPageBreadCrumbItem postPageCrumbItem;

  /**
   * Contains treatments that must be performed during the page shown lifecycle.
   */
  @Override
  public void beforeOnPageShowing() {
    if (postPageCrumbItem == null) {
      postPageCrumbItem = ToPageBreadCrumbItem.to(BlogPostPage.this).add();
    }

    if ("edition".equals(state) || "new".equals(postId)) {
      postWidget.setEditionMode();
    }
    postWidget.initDefaultDisplay();
    postWidget.addResourceSaveHandler(new ResourceSaveHandler<Post>() {
      @Override
      public void onResourceSave(final ResourceSaveEvent<Post> event) {
        updateViewFromModel(event.getValue());
      }
    });
  }

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
    modifyPostAction = getMenuWidget().addPrivilegedClickAction()
        .ofType(MODIFICATION, BlogBundle.msg().post("the"));

    setPageDescription(null);
    getFooterPanel().setVisible(false);
    getRightPanel().add(blogDatePickerWidget);
    postPanel.add(postWidget);

    if ("new".equals(postId)) {
      Post post = Post.newInstance(instance);
      setModel(post);
      blogDatePickerWidget.displayOnlyCurrentPost(post);
    } else {
      JsonHttp.onSuccess(new JsonResponse() {
        @Override
        public void process(final HttpResponse response) {
          final Post post = response.parseJsonEntity();
          setModel(post);
          blogDatePickerWidget.displayOnlyCurrentPost(post);
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
    }

    // Posts to indicate into the calendar
    //blogDatePickerWidget.loadFor(instance);
  }

  private void setModel(Post post) {
    postWidget.setModel(post);
    updateViewFromModel(post);

    // Verify that the modify action can be performed
    if (!postWidget.isInEdition()) {
      modifyPostAction.clickCallback(new Callback() {
        @Override
        public void invoke(final Object... parameters) {
          postWidget.switchEditionMode(true);
          getMenuWidget().setVisible(false);
        }
      }).verify(post);
    }
  }

  private void updateViewFromModel(Post post) {
    setPageTitle(post.getTitle());
    postPageCrumbItem.withParameters(
        ImmutableMultimap.of("instanceId", getApplicationInstance().getId(), "postId", postId))
        .withLabel(postWidget.getModel().getTitle());
  }

  private void updateDateWidgetFromModel(Post post) {
    blogDatePickerWidget.displayOnlyCurrentPost(post);
  }
}

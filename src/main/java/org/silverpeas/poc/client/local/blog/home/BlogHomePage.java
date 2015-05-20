package org.silverpeas.poc.client.local.blog.home;

import com.google.gwt.core.client.JsonUtils;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.blog.post.PostItemWidget;
import org.silverpeas.poc.client.local.template.SilverpeasApplicationLayout;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{blogId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasApplicationLayout applicationLayout;

  @PageState
  private String blogId;

  @Inject
  @DataField
  private PostItemWidget post;

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return applicationLayout;
  }

  @Override
  public void onPageShowing() {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Post fetchedPost = JsonUtils.safeEval(response.getText());
        post.setModel(fetchedPost);
      }
    }).get(PostCriteria.fromIds(blogId, "2"));
  }
}

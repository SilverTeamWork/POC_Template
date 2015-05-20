package org.silverpeas.poc.client.local.blog.home;

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

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends BlogPageComposite {

  @Inject
  @DataField
  private PostItemWidget post;

  @Override
  public void onApplicationInstanceLoaded(ApplicationInstance instance) {
//    dockContainer.add(new Label(StringUtil
//        .format("{0} / {1} / {2}", instance.getLabel(), instance.getComponentName(),
//            instance.getId())));
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Post fetchedPost = response.parseJsonEntity();
        post.setModel(fetchedPost);
      }
    }).get(PostCriteria.fromIds(instance.getId(), "2"));
  }
}

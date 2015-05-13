package org.silverpeas.poc.client.local.blog.home;

import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.test.yocha.layout.template.SilverpeasApplicationComposite;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.test.yocha.layout.template.SilverpeasMainComposite
    .DOCK_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "poc/blog/{blogId}/home.html")
@Templated(DOCK_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasApplicationComposite applicationTemplateComposite;

  @PageState
  private String blogId;

  @Inject
  @DataField("dock-container")
  private Label dockContainer;

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return applicationTemplateComposite;
  }

  @Override
  public void onPageShowing() {
    dockContainer.setText(blogId);
  }
}

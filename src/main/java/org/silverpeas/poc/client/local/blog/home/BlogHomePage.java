package org.silverpeas.poc.client.local.blog.home;

import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasApplicationLayout;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainLayout.DOCK_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "blog/{instanceId}")
@Templated(DOCK_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class BlogHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasApplicationLayout applicationLayout;

  @PageState
  private String instanceId;

  @Inject
  @DataField("dock-container")
  private SilverpeasHtmlPanel dockContainer;

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return applicationLayout;
  }

  @Override
  public void onPageShowing() {
    dockContainer.add(new Label(instanceId));
  }
}

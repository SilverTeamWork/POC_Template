package org.silverpeas.poc.client.local;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasMainLayout;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "home.html")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class HomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasMainLayout mainLayout;

  @Override
  public void onPageShowing() {
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainLayout;
  }
}

package org.silverpeas.poc.client.local.test.yocha.layout.page;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasMainLayout;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.test.yocha.ui.YochaHomePage;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainLayout.DOCK_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "poc/silverpeas/home.html")
@Templated(DOCK_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class YochaTwoHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasMainLayout mainTemplateComposite;

  @Inject
  @DataField("dock-container")
  private TransitionAnchor<YochaHomePage> nextPage;

  @AfterInitialization
  private void setup() {
    nextPage.setText("Allez vers YochaHomePage depuis SilverpeasHomePage");
  }

  @Override
  public void onPageShowing() {

  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainTemplateComposite;
  }
}

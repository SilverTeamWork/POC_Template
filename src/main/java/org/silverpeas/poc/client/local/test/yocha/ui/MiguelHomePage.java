package org.silverpeas.poc.client.local.test.yocha.ui;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.test.yocha.ui.common.SpaceTemplate;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.test.yocha.ui.common.SpaceTemplate.DOCK_LAYOUT;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "miguel/home.html")
@Templated(DOCK_LAYOUT)
@Bundle(BundleProvider.JSON_MESSAGES)
public class MiguelHomePage extends SpaceTemplate {

  @Inject
  private TransitionAnchor<HomePage> nextPage;

  @AfterInitialization
  private void setup() {
    nextPage.setText("---------------------------------> Allez vers HomePage");
  }

  public void onShowing() {
    //getContentContainer().add(nextPage);
  }
}

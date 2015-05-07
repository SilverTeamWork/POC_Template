package org.silverpeas.poc.client.local.test.yocha.ui;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.SilverpeasMainTemplate;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "tata/yochahome.html")
@Templated(MAIN_HTML_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class YochaHomePage extends SilverpeasMainTemplate {

  @Inject
  private TransitionAnchor<HomePage> nextPage;

  @AfterInitialization
  private void setup() {
    nextPage.setText("---------------------------------> Allez vers HomePage");
    getContentContainer().add(nextPage);
  }
}

package org.silverpeas.poc.client.local;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.test.yocha.ui.YochaHomePage;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "toto/home.html")
@Templated(MAIN_HTML_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class HomePage extends SilverpeasMainTemplate {

  @Inject
  private TransitionAnchor<YochaHomePage> nextPage;

  @AfterInitialization
  private void setup() {
    nextPage.setText("Allez vers YochaHomePage depuis HomePage");
  }

  @Override
  public void pageShowing() {
    super.pageShowing();
    getContentContainer().add(nextPage);
  }
}

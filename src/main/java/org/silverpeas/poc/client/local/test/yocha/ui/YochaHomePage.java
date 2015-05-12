package org.silverpeas.poc.client.local.test.yocha.ui;

import com.google.gwt.user.client.ui.VerticalPanel;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasMainTemplate;
import org.silverpeas.poc.client.local.test.yocha.ui.common.SpaceTemplate;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;
import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate
    .MAIN_HTML_TEMPLATE_CONTENT_CONTAINER;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "tata/home.html")
@Templated(MAIN_HTML_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class YochaHomePage extends SpaceTemplate {

  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private VerticalPanel content = new VerticalPanel();

  @Inject
  private TransitionAnchor<MiguelHomePage> nextPage;

  @Inject
  private SilverpeasMainTemplate example;

  @AfterInitialization
  private void setup() {
    nextPage.setText("---------------------------------> Allez vers Miguel Home Page");
  }

  @Override
  public void pageShowing() {
    super.pageShowing();
    content.add(example);
    content.add(nextPage);
  }
}

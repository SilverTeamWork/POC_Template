package org.silverpeas.poc.client.local.test.yocha.ui;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasMainLayout;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.test.yocha.TemplateExample;
import org.silverpeas.poc.client.local.test.yocha.layout.page.YochaTwoHomePage;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "poc/yocha/home.html")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class YochaHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasMainLayout mainTemplateComposite;

  @Inject
  @DataField("home-content")
  private SilverpeasHtmlPanel homeContent;

  @Inject
  private TransitionAnchor<YochaTwoHomePage> nextPage;

  @Inject
  private TemplateExample example;

  @AfterInitialization
  private void setup() {
    nextPage.setText("Allez vers SilverpeasHomePage depuis YochaHomePage");
  }

  @Override
  public void onPageShowing() {
    homeContent.add(example);
    homeContent.add(nextPage);
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainTemplateComposite;
  }
}

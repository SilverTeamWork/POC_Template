package org.silverpeas.poc.client.local;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasMainTemplate;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author Yohann Chastagnier
 */
@Page(path = "home.html")
@Templated(MAIN_HTML_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
public class HomePage extends SilverpeasMainTemplate {

  @Inject
  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private HomePageMainBody content;

}
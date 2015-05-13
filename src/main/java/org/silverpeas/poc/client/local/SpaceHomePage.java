package org.silverpeas.poc.client.local;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.PageMainTemplate;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.enterprise.context.ApplicationScoped;

import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author miguel
 */
@Page(path = "space/{spaceId}")
@Templated(MAIN_HTML_TEMPLATE)
@Bundle(BundleProvider.JSON_MESSAGES)
@ApplicationScoped
public class SpaceHomePage extends PageMainTemplate {

  @PageState
  private String spaceId;

}

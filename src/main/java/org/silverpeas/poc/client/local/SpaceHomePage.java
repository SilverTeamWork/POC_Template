package org.silverpeas.poc.client.local;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.template.SilverpeasSpaceLayout;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author miguel
 */
@Page(path = "space/{spaceId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class SpaceHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasSpaceLayout spaceLayout;

  @PageState
  private String spaceId;

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return spaceLayout;
  }

  @Override
  public void onPageShowing() {

  }
}

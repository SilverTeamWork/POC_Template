package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.api.navigation.TransitionProvider;
import org.silverpeas.poc.client.local.SpaceHomePage;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
public class BreadCrumbSpaceItem extends BreadCrumbItem {

  private final Space space;

  public BreadCrumbSpaceItem(final Space space) {
    this.space = space;
  }


  @Override
  protected TransitionTo<SpaceHomePage> getTargetPage() {
    return TransitionProvider.getTransitionTo(SpaceHomePage.class);
  }

  @Override
  public String getLabel() {
    return space.getLabel();
  }

  @Override
  public String getStyleClass() {
    return "space-breadcrump";
  }

  @Override
  protected void applyParameters(final Multimap<String, String> parameters) {
    parameters.put("spaceId", space.getId());
  }
}

package org.silverpeas.poc.client.local.space;

import com.google.common.collect.ImmutableMultimap;
import com.google.gwt.event.dom.client.ClickHandler;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.SpaceHomePage;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
public class SpaceTransitionAnchor extends SilverpeasTransitionAnchor implements ClickHandler {

  @Inject
  private EventsProvider eventsProvider;

  private Space space;

  @Override
  public void onClickEvent() {
    eventsProvider.getSpaceSelectionEvent().fire(new SpaceSelection(this.space));
  }

  public void setSpace(Space space) {
    this.space = space;
    setText(space.getLabel());
    if (space.isHome()) {
      initHref(HomePage.class, ImmutableMultimap.<String, String>of());
    } else {
      initHref(SpaceHomePage.class, ImmutableMultimap.of("spaceId", space.getId()));
    }
  }
}

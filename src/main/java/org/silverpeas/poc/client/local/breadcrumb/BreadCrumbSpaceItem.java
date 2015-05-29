package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.SpaceHomePage;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.enterprise.event.Event;

/**
 * @author miguel
 */
public class BreadCrumbSpaceItem extends BreadCrumbItem<BreadCrumbSpaceItem> {

  private final Space space;

  public BreadCrumbSpaceItem(final Space space) {
    this.space = space;
  }

  @Override
  protected String getTargetPageName() {
    return (space.isHome() ? HomePage.class : SpaceHomePage.class).getSimpleName();
  }

  @Override
  public String getLabel() {
    return space.getLabel();
  }

  @Override
  public String getStyleClass() {
    return (space.isHome() ? "home-breadcrump" : "space-breadcrump");
  }

  @Override
  protected Multimap<String, String> getTransitionParameters() {
    return (space.isHome() ? ImmutableMultimap.<String, String>of() :
        ImmutableMultimap.of("spaceId", space.getId()));
  }

  @Override
  protected void fireItemEvent() {
    EventsProvider eventsProvider = BeanManager.getInstanceOf(EventsProvider.class);
    Event<SelectedSpace> event = eventsProvider.getSpaceSelectionEvent();
    event.fire(new SelectedSpace(space));
  }
}

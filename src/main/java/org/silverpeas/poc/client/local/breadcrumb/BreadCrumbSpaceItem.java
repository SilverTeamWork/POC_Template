package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.SpaceHomePage;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.enterprise.event.Event;

/**
 * @author miguel
 */
public class BreadCrumbSpaceItem extends BreadCrumbItem {

  private final Space space;

  public BreadCrumbSpaceItem(final Space space) {
    this.space = space;
  }

  @Override
  protected Class<? extends SilverpeasPageComposite> getTargetPage() {
    return (space.isHome() ? HomePage.class : SpaceHomePage.class);
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
  protected void fireEvent() {
    EventsProvider eventsProvider = BeanManager.getInstanceOf(EventsProvider.class);
    Event<SpaceSelection> event = eventsProvider.getSpaceSelectionEvent();
    event.fire(new SpaceSelection(space));
  }

}

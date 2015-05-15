package org.silverpeas.poc.client.local.util;

import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A provider of all available events. It acts as a single entry point of all events.
 * @author miguel
 */
@Singleton
public class EventsProvider {

  @Inject
  private Event<SpaceSelection> spaceSelectionEvent;
  @Inject
  private Event<SpaceLoaded> spaceLoadedEvent;
  @Inject
  private Event<SpaceContentLoaded> spaceContentLoadedEvent;

  public Event<SpaceSelection> getSpaceSelectionEvent() {
    return spaceSelectionEvent;
  }

  public Event<SpaceLoaded> getSpaceLoadedEvent() {
    return spaceLoadedEvent;
  }

  public Event<SpaceContentLoaded> getSpaceContentLoadedEvent() {
    return spaceContentLoadedEvent;
  }
}

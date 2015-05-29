package org.silverpeas.poc.client.local.util;

import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.application.event.SelectedApplicationInstance;
import org.silverpeas.poc.client.local.space.event.LoadedRootSpaces;
import org.silverpeas.poc.client.local.space.event.LoadedSpaceContent;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A provider of all available events. It acts as a single entry point of all events.
 * @author miguel
 */
@Singleton
public class EventsProvider {

  public static EventsProvider get() {
    return BeanManager.getInstanceOf(EventsProvider.class);
  }

  @Inject
  private Event<SelectedSpace> spaceSelectionEvent;
  @Inject
  private Event<LoadedRootSpaces> spaceLoadedEvent;
  @Inject
  private Event<LoadedSpaceContent> spaceContentLoadedEvent;

  @Inject
  private Event<SelectedApplicationInstance> applicationInstanceSelectionEvent;
  @Inject
  private Event<LoadedApplicationInstance> loadedApplicationInstanceEvent;

  public Event<SelectedSpace> getSpaceSelectionEvent() {
    return spaceSelectionEvent;
  }

  public Event<LoadedRootSpaces> getSpaceLoadedEvent() {
    return spaceLoadedEvent;
  }

  public Event<LoadedSpaceContent> getSpaceContentLoadedEvent() {
    return spaceContentLoadedEvent;
  }

  public Event<SelectedApplicationInstance> getApplicationInstanceSelectionEvent() {
    return applicationInstanceSelectionEvent;
  }

  public Event<LoadedApplicationInstance> getApplicationInstanceLoadedEvent() {
    return loadedApplicationInstanceEvent;
  }
}

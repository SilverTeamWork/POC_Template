package org.silverpeas.poc.client.local.application.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.api.event.ResourceEvent;
import org.silverpeas.poc.client.local.application.ApplicationInstance;

/**
 * @author miguel
 */
@LocalEvent
public class LoadedApplicationInstance extends ResourceEvent<ApplicationInstance> {

  public LoadedApplicationInstance(final ApplicationInstance instance) {
    super(instance);
  }
}

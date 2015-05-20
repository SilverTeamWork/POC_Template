package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.api.event.ResourceEvent;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
@LocalEvent
public class LoadedSpace extends ResourceEvent<Space> {
  public LoadedSpace(final Space resource) {
    super(resource);
  }
}

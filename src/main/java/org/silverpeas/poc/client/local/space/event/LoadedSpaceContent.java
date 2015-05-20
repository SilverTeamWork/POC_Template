package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.api.event.ResourceEvent;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
@LocalEvent
public class LoadedSpaceContent extends ResourceEvent<Space> {
  public LoadedSpaceContent(final Space resource) {
    super(resource);
  }
}

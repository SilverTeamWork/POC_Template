package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
@LocalEvent
public class SpaceContentLoaded {

  private Space space;

  public SpaceContentLoaded(final Space space) {
    this.space = space;
  }

  public Space getSpaceWithLoadedContent() {
    return space;
  }
}

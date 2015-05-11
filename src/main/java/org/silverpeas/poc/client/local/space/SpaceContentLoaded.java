package org.silverpeas.poc.client.local.space;

import org.jboss.errai.common.client.api.annotations.LocalEvent;

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

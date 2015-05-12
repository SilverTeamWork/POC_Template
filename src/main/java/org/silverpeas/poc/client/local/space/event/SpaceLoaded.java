package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.client.local.space.Space;

import java.util.List;

/**
 * @author miguel
 */
@LocalEvent
public class SpaceLoaded {

  private List<Space> spaces;

  public SpaceLoaded(final List<Space> spaces) {
    this.spaces = spaces;
  }

  public List<Space> getLoadedSpaces() {
    return spaces;
  }
}

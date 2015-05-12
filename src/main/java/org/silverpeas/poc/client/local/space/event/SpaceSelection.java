package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
@LocalEvent
public class SpaceSelection {

  private Space space;

  public SpaceSelection(final Space selectedSpace) {
    this.space = selectedSpace;
  }

  public Space getSelectedSpace() {
    return space;
  }
}

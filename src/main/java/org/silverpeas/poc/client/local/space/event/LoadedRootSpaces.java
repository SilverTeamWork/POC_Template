package org.silverpeas.poc.client.local.space.event;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.client.local.space.Space;

import java.util.List;

/**
 * This Event is triggered one time and observed by one bean at the start of the application.
 * @author miguel
 */
@LocalEvent
public class LoadedRootSpaces {

  private List<Space> spaces;

  public LoadedRootSpaces(final List<Space> spaces) {
    this.spaces = spaces;
  }

  public List<Space> getLoadedSpaces() {
    return spaces;
  }
}

package org.silverpeas.poc.client.local.application;

import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;

/**
 * @author miguel
 */
public class ApplicationInstance extends SpaceContent<Space> {

  protected ApplicationInstance() {
  }

  public final native String getComponentName() /*-{
    return this.name;
  }-*/;
}

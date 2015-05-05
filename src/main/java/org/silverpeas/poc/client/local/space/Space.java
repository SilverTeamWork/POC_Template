package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author miguel
 */
public class Space extends SpaceContent {

  protected Space() {
  }

  public final native String getSpacesUri() /*-{
    return this.spacesURI;
  }-*/;

  public final native String getComponentsUri() /*-{
    return this.componentsURI;
  }-*/;

  public final native int getLevel() /*-{
    return this.level;
  }-*/;

  public final native int getRank() /*-{
    return this.rank;
  }-*/;

  public final native boolean isRoot() /*-{
    return this.level == 0;
  }-*/;

  public final native boolean isCurrent() /*-{
    return this.selected == true;
  }-*/;

  public final native void setAsCurrent() /*-{
    this.selected = true;
  }-*/;

  public final native void unsetAsCurrent() /*-{
    this.selected = false;
  }-*/;

  public final native boolean isEqual(final Space anotherSpace) /*-{
    return anotherSpace != null && this.id.equals(anotherSpace.id);
  }-*/;
}

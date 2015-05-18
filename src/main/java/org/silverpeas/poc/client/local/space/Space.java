package org.silverpeas.poc.client.local.space;

import java.util.List;

/**
 * @author miguel
 */
public class Space extends SpaceContent<Space> {

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

  public final native void setContent(final List<SpaceContent> content) /*-{
    this.content = content;
  }-*/;

  public final native List<SpaceContent> getContent() /*-{
    return this.content;
  }-*/;

  public final native boolean isHome() /*-{
    return this.home;
  }-*/;
}

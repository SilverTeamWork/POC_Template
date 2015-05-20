package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author miguel
 */
public abstract class SpaceContent<PARENT extends SpaceContent> extends JavaScriptObject {

  protected SpaceContent() {
  }

  public final boolean isApplication() {
    return "component".equalsIgnoreCase(getType());
  }

  public final native String getType() /*-{
    return this.type;
  }-*/;

  public final native String getParentUri() /*-{
    return this.parentURI;
  }-*/;

  public final native String getLabel() /*-{
    return this.label;
  }-*/;

  public final native void setLabel(String label) /*-{
    return this.label = label;
  }-*/;

  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  public final native String getUri() /*-{
    return this.uri;
  }-*/;

  public final native String getId() /*-{
    return this.id;
  }-*/;

  public final native int getRank() /*-{
    return this.rank;
  }-*/;

  public final native PARENT getParent() /*-{
    return this.parent;
  }-*/;

  public final native void setParent(PARENT parent) /*-{
    return this.parent = parent;
  }-*/;
}

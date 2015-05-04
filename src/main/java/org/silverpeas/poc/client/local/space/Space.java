package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author miguel
 */
public class Space extends JavaScriptObject {

  protected Space() {
  }

  public final native String getLabel() /*-{
    return this.label;
  }-*/;

  public final native String getUri() /*-{
    return this.uri;
  }-*/;
}

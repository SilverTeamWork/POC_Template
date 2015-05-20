package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author miguel
 */
public abstract class Contribution extends JavaScriptObject {

  protected Contribution() {
  }

  public final native String getUri() /*-{
    return this.uri;
  }-*/;

  public final native String getId() /*-{
    return this.id;
  }-*/;

  public final native String getAppInstanceId() /*-{
    return this.componentId;
  }-*/;
}

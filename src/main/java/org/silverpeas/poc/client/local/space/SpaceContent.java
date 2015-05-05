package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * @author miguel
 */
public abstract class SpaceContent extends JavaScriptObject {

  protected SpaceContent() {
  }

  public final native String getLabel() /*-{
    return this.label;
  }-*/;

  public final native String getUri() /*-{
    return this.uri;
  }-*/;
}

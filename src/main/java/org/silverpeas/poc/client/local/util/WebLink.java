package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Yohann Chastagnier
 */
public class WebLink extends JavaScriptObject {

  protected WebLink() {
  }

  public final native String getUrl() /*-{
    return this.href;
  }-*/;

  public final native String getRelationship() /*-{
    return this.rel;
  }-*/;

  public final native String getHttpMethod() /*-{
    return this.method;
  }-*/;
}

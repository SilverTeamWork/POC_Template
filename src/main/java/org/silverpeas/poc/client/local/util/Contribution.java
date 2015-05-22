package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A contribution is a data that is pushed by the user to Silverpeas and that can be shared with
 * others users. A contribution can be an image, a text, a rating, and so on.
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

  public final native Link[] getLinks() /*-{
    return this.links;
  }-*/;


  public static class Link extends JavaScriptObject {

    protected Link() {
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
}

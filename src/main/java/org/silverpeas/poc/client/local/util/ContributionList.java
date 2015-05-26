package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.List;

/**
 * A contribution is a data that is pushed by the user to Silverpeas and that can be shared with
 * others users. A contribution can be an image, a text, a rating, and so on.
 * @author miguel
 */
public class ContributionList<ENTITY> extends JavaScriptObject {

  protected ContributionList() {
  }

  public final native List<ENTITY> getEntities() /*-{
    return @org.turbogwt.core.collections.JsArrayList::new([Ljava/lang/Object;)(this.entities);
  }-*/;

  public final native Contribution.Link[] getLinks() /*-{
    return this.links;
  }-*/;
}

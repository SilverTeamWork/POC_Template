package org.silverpeas.poc.client.local.comment;

import org.silverpeas.poc.client.local.util.Contribution;

/**
 * @author miguel
 */
public class Comment extends Contribution {
  protected Comment() {
  }

  public final native String getHtml() /*-{
    return this.textForHtml;
  }-*/;
}

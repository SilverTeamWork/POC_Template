package org.silverpeas.poc.client.local.util;

import java.util.Date;

/**
 * A publication is a contribution with a title and a content. For example, an office document, a
 * comment or an image are both some publications.
 * @author miguel
 */
public class Publication extends Contribution {
  protected Publication() {
  }

  public final native String getTitle() /*-{
    return this.title;
  }-*/;

  public final native void setTitle(String title) /*-{
    this.title = title;
  }-*/;

  public final native String getContent() /*-{
    return this.content;
  }-*/;

  public final native double getCreationTimestamp() /*-{
    return this.createDate;
  }-*/;

  public final native String getCreator() /*-{
    return this.creator;
  }-*/;

  public final native double getUpdateTimestamp() /*-{
    return this.updateDate;
  }-*/;

  public final native String getUpdater() /*-{
    return this.updater;
  }-*/;

  public final Date getCreationDate() {
    return new Date((long)this.getCreationTimestamp());
  }

  public final Date getUpdateDate() {
    return new Date((long)this.getUpdateTimestamp());
  }
}

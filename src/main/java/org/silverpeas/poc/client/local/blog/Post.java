package org.silverpeas.poc.client.local.blog;

import com.google.gwt.core.client.JsonUtils;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.util.Messages;
import org.silverpeas.poc.client.local.util.Publication;

import java.util.Date;

/**
 * Warning: GWT simulates long type with a double type in js.
 * @author ebonnet
 */
public class Post extends Publication {

  /**
   * The key provider that provides the unique ID of a post.
   */
  public static Post newInstance(ApplicationInstance applicationInstance) {
    Post post = (Post) Publication.newInstance(applicationInstance);
    post.setDateEventTimestamp(post.getCreationTimestamp());
    return post;
  }

  // Overlay types always have protected, zero argument constructors.
  protected Post() {
  }

  public final native double getDateEventTimestamp() /*-{
    return this.dateEvent;
  }-*/;

  public final native void setDateEventTimestamp(double time) /*-{
    this.dateEvent = time;
  }-*/;

  public final Date getDateEvent() {
    return new Date((long) getDateEventTimestamp());
  }

  public final native String getNbComments() /*-{
    return this.nbComments;
  }-*/;

}

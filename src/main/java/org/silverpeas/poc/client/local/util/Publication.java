package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JsonUtils;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.client.local.application.ApplicationInstance;

import java.util.Date;

/**
 * A publication is a contribution with a title and a content. For example, an office document, a
 * comment or an image are both some publications.
 * @author miguel
 */
public class Publication extends Contribution {

  /**
   * The key provider that provides the unique ID of a post.
   */
  public static Publication newInstance(ApplicationInstance applicationInstance) {
    Publication publication = JsonUtils.safeEval("{\"uri\":\"\",\"id\":\"\",\"componentId\":\"\"," +
        "\"title\":\"\",\"content\":\"\",\"dateEvent\":0,\"nbComments\":0,\"createDate\":0," +
        "\"creator\":\"\",\"updateDate\":0,\"updater\":\"\",\"links\":[]}");
    publication.setAppInstanceId("blog" + applicationInstance.getId());
    publication.setTitle(I18n.format(Messages.POST_NEW_TITLE_LABEL));
    publication.setContent("");
    publication.setCreationTimestamp(new Date().getTime());
    return publication;
  }

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

  public final native void setContent(String content) /*-{
    this.content = content;
  }-*/;

  public final native double getCreationTimestamp() /*-{
    return this.createDate;
  }-*/;

  public final native void setCreationTimestamp(double time) /*-{
    this.createDate = time;
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
    return new Date((long) this.getCreationTimestamp());
  }

  public final Date getUpdateDate() {
    return new Date((long) this.getUpdateTimestamp());
  }
}

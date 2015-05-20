package org.silverpeas.poc.client.local.blog;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Warning: GWT simulates long type with a double type in js.
 * @author ebonnet
 */
public class Post extends JavaScriptObject {

  /**
   * The key provider that provides the unique ID of a post.
   */
  public static final ProvidesKey<Post> PROVIDES_KEY = new ProvidesKey<Post>() {
    @Override
    public Object getKey(Post post) {
      return post == null ? null : post.getId();
    }
  };

  // Overlay types always have protected, zero argument constructors.
  protected Post() {
  }

  public final native String getUri() /*-{
    return this.uri;
  }-*/;

  public final native String getId() /*-{
    return this.id;
  }-*/;

  public final native String getComponentId() /*-{
    return this.componentId;
  }-*/;

  public final native String getTitle() /*-{
    return this.title;
  }-*/;

  public final native String getContent() /*-{
    return this.content;
  }-*/;

  public final native double getDateEvent() /*-{
    return this.dateEvent;
  }-*/;

  public final native String getNbComments() /*-{
    return this.nbComments;
  }-*/;

  public final native double getCreateDate() /*-{
    return this.createDate;
  }-*/;

  public final native String getCreator() /*-{
    return this.creator;
  }-*/;

  public final native double getUpdateDate() /*-{
    return this.updateDate;
  }-*/;

  public final native String getUpdater() /*-{
    return this.updater;
  }-*/;


  //TODO add links metadata
  /*
  @XmlElement
  private List<LinkMetadataEntity> links;
   */

}

package org.silverpeas.poc.client.local.blog;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.view.client.ProvidesKey;
import org.silverpeas.poc.client.local.util.Contribution;
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
  public static final ProvidesKey<Post> PROVIDES_KEY = new ProvidesKey<Post>() {
    @Override
    public Object getKey(Post post) {
      return post == null ? null : post.getId();
    }
  };

  // Overlay types always have protected, zero argument constructors.
  protected Post() {
  }

  public final native double getDateEvent() /*-{
    return this.dateEvent;
  }-*/;

  public final native String getNbComments() /*-{
    return this.nbComments;
  }-*/;

}

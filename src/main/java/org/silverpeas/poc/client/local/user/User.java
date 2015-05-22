package org.silverpeas.poc.client.local.user;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.view.client.ProvidesKey;

/**
 * @author Yohann Chastagnier
 */
public class User extends JavaScriptObject {

  /**
   * The key provider that provides the unique ID of a user.
   */
  public static final ProvidesKey<User> PROVIDES_KEY = new ProvidesKey<User>() {
    @Override
    public Object getKey(User user) {
      return user == null ? null : user.getId();
    }
  };

  // Overlay types always have protected, zero argument constructors.
  protected User() {
  }

  public final native String getId() /*-{
    return this.id;
  }-*/;

  public final native String getFirstName() /*-{
    return this.firstName;
  }-*/;

  public final native String getLastName() /*-{
    return this.lastName;
  }-*/;

  public final native String getAccessLevel() /*-{
    return this.accessLevel;
  }-*/;

  public final native void setTags(String tags) /*-{
    this.tags = tags;
  }-*/;

  public final native String getTags() /*-{
    return this.accessLevel;
  }-*/;

  public final native String getAvatar() /*-{
    return this.avatar;
  }-*/;

  public final native String getName() /*-{
    return this.firstName + ' ' + this.lastName;
  }-*/;
}

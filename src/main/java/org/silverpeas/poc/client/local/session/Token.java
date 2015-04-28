package org.silverpeas.poc.client.local.session;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.view.client.ProvidesKey;

import static org.silverpeas.poc.api.web.components.common.Message.notifies;

/**
 * @author Yohann Chastagnier
 */
public class Token extends JavaScriptObject {

  public static boolean firstAccess = true;
  public static Token current = null;

  public static boolean isCurrent() {
    return getCurrent() != null;
  }

  public static Token getCurrent() {
    if (firstAccess) {
      try {
        current =
            JsonUtils.safeEval(Storage.getSessionStorageIfSupported().getItem("current-user"));
      } catch (Exception e) {
        notifies(e.getMessage()).error();
      }
      firstAccess = false;
    }
    return current;
  }

  public static void setCurrent(Token user) {
    if (user == null) {
      current = null;
      Storage.getSessionStorageIfSupported().setItem("current-user", null);
    } else {
      Storage.getSessionStorageIfSupported().setItem("current-user", JsonUtils.stringify(user));
      current = user;
    }
  }


  /**
   * The key provider that provides the unique ID of a user.
   */
  public static final ProvidesKey<Token> PROVIDES_KEY = new ProvidesKey<Token>() {
    @Override
    public Object getKey(Token user) {
      return user == null ? null : user.getId();
    }
  };

  // Overlay types always have protected, zero argument constructors.
  protected Token() {
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

  public final String getName() {
    return getFirstName() + " " + getLastName();
  }
}

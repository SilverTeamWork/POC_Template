package org.silverpeas.poc.client.local.session;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.storage.client.Storage;
import org.silverpeas.poc.client.local.admin.User;

import static org.silverpeas.poc.api.web.components.common.Message.notifies;

/**
 * @author Yohann Chastagnier
 */
public class CurrentUser {

  public static boolean firstAccess = true;
  public static User currentUser = null;

  public static boolean exists() {
    return get() != null;
  }

  public static User get() {
    if (firstAccess) {
      try {
        currentUser =
            JsonUtils.safeEval(Storage.getSessionStorageIfSupported().getItem("current-user"));
      } catch (Exception e) {
        notifies(e.getMessage()).error();
      }
      firstAccess = false;
    }
    return currentUser;
  }

  public static String token() {
    return Storage.getSessionStorageIfSupported().getItem("current-user-token");
  }

  public static void markAsDisconnected() {
    currentUser = null;
    Storage.getSessionStorageIfSupported().setItem("current-user", null);
    Storage.getSessionStorageIfSupported().setItem("current-user-token", null);
  }

  public static void setConnected(User user, String token) {
    Storage.getSessionStorageIfSupported().setItem("current-user", JsonUtils.stringify(user));
    Storage.getSessionStorageIfSupported().setItem("current-user-token", token);
    currentUser = user;
  }
}

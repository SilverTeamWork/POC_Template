package org.silverpeas.poc.client.local.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.storage.client.Storage;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceCriteria;

import java.util.ArrayList;
import java.util.List;

import static org.silverpeas.poc.api.web.components.common.Message.notifies;

/**
 * @author Yohann Chastagnier
 */
public class CurrentUser {

  private static final String USER_PROFILE_KEY = "user-profile";
  private static final String USER_AUTH_TOKEN_KEY = "user-auth-token";

  public static User currentUser = null;

  public static boolean exists() {
    return get() != null;
  }

  public static User get() {
    try {
      currentUser =
          JsonUtils.safeEval(Storage.getLocalStorageIfSupported().getItem(USER_PROFILE_KEY));
    } catch (Exception e) {
      notifies(e.getMessage()).error();
    }
    return currentUser;
  }

  public static String token() {
    return Storage.getLocalStorageIfSupported().getItem(USER_AUTH_TOKEN_KEY);
  }

  public static void markAsDisconnected() {
    currentUser = null;
    Storage.getLocalStorageIfSupported().setItem(USER_PROFILE_KEY, null);
    Storage.getLocalStorageIfSupported().setItem(USER_AUTH_TOKEN_KEY, null);
  }

  public static void setConnected(User user, String token) {
    Storage.getLocalStorageIfSupported().setItem(USER_PROFILE_KEY, JsonUtils.stringify(user));
    Storage.getLocalStorageIfSupported().setItem(USER_AUTH_TOKEN_KEY, token);
    currentUser = user;
  }
}

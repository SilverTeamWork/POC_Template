package org.silverpeas.poc.api.util;

import com.google.gwt.user.client.Window;
import org.jboss.errai.ui.nav.client.local.Navigation;

/**
 * @author Yohann Chastagnier
 */
public class UrlManager {
  private static final String JSON_DATA_SERVER = "/services/";

  /**
   * Gets the server URL of the resource at the given path.
   * @param path the path from which the server URL must be computed.
   * @return the server URL of the resource at the given path.
   */
  public static String getServerUrl(String path) {
    String appContext = Navigation.getAppContext();
    if (StringUtil.isDefined(appContext) && !path.startsWith(appContext)) {
      path = (appContext + "/" + path).replaceAll("/{2,}", "/");
    }
    return Window.Location.createUrlBuilder().setPath(path).buildString();
  }

  /**
   * Gets the server URL of the resource at the given path.
   * @param path the path from which the server URL must be computed.
   * @return the server URL of the resource at the given path.
   */
  public static native String getSilverpeasRootUrl(String path) /*-{
    if (!$wnd.silverpeasServerRootUrl) {
      $wnd.silverpeasServerRootUrl = $wnd.silverpeas.replace(/\/[^\/]*$/gi, '');
    }
    var silverpeasUrl = $wnd.silverpeasServerRootUrl;
    if (path && path.length > 0) {
      if (path.charAt(0) === '/') {
        silverpeasUrl += path;
      } else {
        silverpeasUrl += '/' + path;
      }
    } else {
      silverpeasUrl += '/';
    }
    return silverpeasUrl;
  }-*/;

  /**
   * Gets the server URL of the resource at the given path.
   * @param path the path from which the server URL must be computed.
   * @return the server URL of the resource at the given path.
   */
  public static native String getSilverpeasUrl(String path) /*-{
    var silverpeasUrl = $wnd.silverpeas;
    if (path && path.length > 0) {
      if (path.charAt(0) === '/') {
        silverpeasUrl += path;
      } else {
        silverpeasUrl += '/' + path;
      }
    } else {
      silverpeasUrl += '/';
    }
    return silverpeasUrl;
  }-*/;

  /**
   * Gets the server URL of the resource at the given path.
   * @param path the path from which the server URL must be computed.
   * @return the server URL of the resource at the given path.
   */
  public static String getSilverpeasServiceUrl(String path) {
    return getSilverpeasUrl(JSON_DATA_SERVER) + path;
  }

  /**
   * Replaces the browser window location.href by the given path.<br/>
   * (The URL refers to a plain HTML page (unmanaged GWT page) in the server)
   * @param path the path to replace.
   */
  public static void goToPlainPage(String path) {
    Log.debug(getServerUrl(path));
    Window.Location.assign(getServerUrl(path));
  }
}

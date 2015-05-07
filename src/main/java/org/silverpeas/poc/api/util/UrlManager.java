package org.silverpeas.poc.api.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * @author Yohann Chastagnier
 */
public class UrlManager {

  /**
   * Gets the server URL of the resource at the given path.
   * @param path the path from which the server URL must be computed.
   * @return the server URL of the resource at the given path.
   */
  public static String getServerUrl(String path) {
    return Window.Location.createUrlBuilder().setPath(path).buildString();
  }

  /**
   * Replaces the browser window location.href by the given path.<br/>
   * (The URL refers to a plain HTML page (unmanaged GWT page) in the server)
   * @param path the path to replace.
   */
  public static void goToPlainPage(String path) {
    GWT.log(getServerUrl(path));
    Window.Location.assign(getServerUrl(path));
  }
}

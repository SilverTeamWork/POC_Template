package org.silverpeas.poc.api.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * @author Yohann Chastagnier
 */
public class UrlManager {

  /**
   * Gets the full server URL for the given path.
   * @param path the path from which the full server URL must be computed.
   * @return the full server url.
   */
  public static String fullUrl(String path) {
    return Window.Location.createUrlBuilder().setPath(path).buildString();
  }

  /**
   * Replaces the browser window location.href by the given path.
   * @param path the path to replace.
   */
  public static void goTo(String path) {
    GWT.log(fullUrl(path));
    Window.Location.assign(fullUrl(path));
  }
}

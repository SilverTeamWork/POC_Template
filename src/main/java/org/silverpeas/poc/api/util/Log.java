package org.silverpeas.poc.api.util;

import com.google.gwt.core.client.GWT;

/**
 * Centralizes the way to write logs into browser console.
 * @author Yohann Chastagnier
 */
public class Log {

  /**
   * Logs a message for development context.
   * @param message the message to log.
   * @param params the parameters of the message.
   */
  public static void dev(String message, Object... params) {
    dev(message, null, params);
  }

  /**
   * Logs a message for development context.
   * @param message the message to log.
   * @param t the exception to display.
   * @param params the parameters of the message.
   */
  public static void dev(String message, Throwable t, Object... params) {
    String theMessage = message;

    int i = 0;
    for (Object param : params) {
      theMessage = theMessage.replace("{" + (i++) + "}", param.toString());
    }

    GWT.log(theMessage, t);
  }
}

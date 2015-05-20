package org.silverpeas.poc.api.util;

import com.google.gwt.core.client.GWT;

import static org.silverpeas.poc.api.util.StringUtil.format;

/**
 * Centralizes the way to write logs into browser console.
 * @author Yohann Chastagnier
 */
public class Log {

  public static boolean debugEnabled = false;

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
    log(message, t, params);
  }

  /**
   * Logs a message for debug context.
   * @param message the message to log.
   * @param params the parameters of the message.
   */
  public static void debug(String message, Object... params) {
    debug(message, null, params);
  }

  /**
   * Logs a message for debug context.
   * @param message the message to log.
   * @param t the exception to display.
   * @param params the parameters of the message.
   */
  public static void debug(String message, Throwable t, Object... params) {
    if (debugEnabled) {
      log(message, t, params);
    }
  }

  /**
   * Logs a message for development context.
   * @param message the message to log.
   * @param t the exception to display.
   * @param params the parameters of the message.
   */
  private static void log(String message, Throwable t, Object... params) {
    GWT.log(format(message, params), t);
  }
}

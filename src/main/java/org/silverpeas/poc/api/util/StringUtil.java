package org.silverpeas.poc.api.util;

/**
 * @author Yohann Chastagnier
 */
public class StringUtil {

  public static boolean isDefined(String string) {
    if (string == null || string.length() == 0) {
      return false;
    }
    String tmp = string.trim();
    return tmp.length() != 0 && !"null".equals(tmp);
  }

  public static boolean isNotDefined(String string) {
    return !isDefined(string);
  }
}

package org.silverpeas.poc.api.util;

/**
 * @author Yohann Chastagnier
 */
public class StringUtil {
  private static final String TRUNCATED_TEXT_SUFFIX = "...";

  /**
   * Indicates if the given string can be parsed as an integer.
   * @param string the string to verify.
   * @return true if it can be parsed as an integer, false otherwise
   */
  public static boolean isInteger(String string) {
    try {
      Integer.parseInt(string);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * @param text The string to truncate if its size is greater than the maximum length given as
   * parameter.
   * @param maxLength The maximum length required.
   * @return The truncated string followed by '...' if needed. Returns the string itself if its
   * length is smaller than the required maximum length.
   */
  public static String truncate(String text, int maxLength) {
    if (text == null || text.length() <= maxLength) {
      return text;
    } else if (maxLength <= TRUNCATED_TEXT_SUFFIX.length()) {
      return TRUNCATED_TEXT_SUFFIX;
    } else {
      return text.substring(0, maxLength - TRUNCATED_TEXT_SUFFIX.length()) + TRUNCATED_TEXT_SUFFIX;
    }
  }

  /**
   * Indicates if the given string is defined.<br/>
   * A string is defined if it is:
   * <ul>
   * <li>not null</li>
   * <li>and not empty</li>
   * <li>and not composed only with spaces</li>
   * <li>and not equals to "null" string</li>
   * </ul>
   * @param string the string to verify.
   * @return true if defined, false otherwise.
   */
  public static boolean isDefined(String string) {
    if (string == null || string.length() == 0) {
      return false;
    }
    String tmp = string.trim();
    return tmp.length() != 0 && !"null".equals(tmp);
  }

  /**
   * Indicates if the given string is not defined.<br/>
   * Please consult {@link #isDefined(String)}.
   * @param string the string to verify.
   * @return true if not defined, false otherwise.
   */
  public static boolean isNotDefined(String string) {
    return !isDefined(string);
  }

  /**
   * <p>Capitalizes a String changing the first letter to title case as
   * per {@link Character#toTitleCase(char)}. No other letters are changed.</p>
   * <p/>
   * <p>For a word based algorithm, see {@link org.apache.commons.lang3.text.WordUtils#capitalize
   * (String)}.
   * A {@code null} input String returns {@code null}.</p>
   * <p/>
   * <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   * </pre>
   * @param str the String to capitalize, may be null
   * @return the capitalized String, {@code null} if null String input
   * @see org.apache.commons.lang3.text.WordUtils#capitalize(String)
   * @see #uncapitalize(String)
   * @since 2.0
   */
  public static String capitalize(final String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }

    String firstChar = new String(new char[]{str.charAt(0)});

    return new StringBuilder(strLen).append(firstChar.toUpperCase()).append(str.substring(1))
        .toString();
  }

  /**
   * <p>Uncapitalizes a String changing the first letter to title case as
   * per {@link Character#toLowerCase(char)}. No other letters are changed.</p>
   * <p/>
   * <p>For a word based algorithm, see {@link org.apache.commons.lang3.text
   * .WordUtils#uncapitalize(String)}.
   * A {@code null} input String returns {@code null}.</p>
   * <p/>
   * <pre>
   * StringUtils.uncapitalize(null)  = null
   * StringUtils.uncapitalize("")    = ""
   * StringUtils.uncapitalize("Cat") = "cat"
   * StringUtils.uncapitalize("CAT") = "cAT"
   * </pre>
   * @param str the String to uncapitalize, may be null
   * @return the uncapitalized String, {@code null} if null String input
   * @see org.apache.commons.lang3.text.WordUtils#uncapitalize(String)
   * @see #capitalize(String)
   * @since 2.0
   */
  public static String uncapitalize(final String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0) {
      return str;
    }

    String firstChar = new String(new char[]{str.charAt(0)});

    return new StringBuilder(strLen).append(firstChar.toLowerCase()).append(str.substring(1))
        .toString();
  }

  /**
   * Formats the given message by adding the given parameters.
   * @param message the message.
   * @param params the parameters to include into the message.
   * @return the formatted message.
   */
  public static String format(String message, Object... params) {
    String theMessage = message;

    int i = 0;
    for (Object param : params) {
      theMessage = theMessage.replace("{" + (i++) + "}", param.toString());
    }

    return theMessage;
  }
}

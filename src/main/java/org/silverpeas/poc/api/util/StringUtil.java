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
}

package org.silverpeas.poc.api.util;

import static org.silverpeas.poc.api.util.StringUtil.isNotDefined;

/**
 * @author Yohann Chastagnier
 */
public class Base64Util {

  /**
   * An array mapping size but values to the characters that will be used to
   * represent them. Note that this is not identical to the set of characters
   * used by MIME-Base64.
   */
  private static final char[] base64Chars = new char[] {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
      'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
      'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
      'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
      '4', '5', '6', '7', '8', '9', '$', '_'};

  /**
   * Converts a byte array into a base 64 encoded string. Null is encoded as
   * null, and an empty array is encoded as an empty string. Otherwise, the byte
   * data is read 3 bytes at a time, with bytes off the end of the array padded
   * with zeros. Each 24-bit chunk is encoded as 4 characters from the sequence
   * [A-Za-z0-9$_]. If one of the source positions consists entirely of padding
   * zeros, an '=' character is used instead.
   *
   * @param string a string, which may be null or empty
   * @return a String
   */
  public static String encode(String string) {
    if (isNotDefined(string)) {
      return "";
    }

    byte[] data = string.getBytes();
    int len = data.length;

    int olen = 4 * ((len + 2) / 3);
    char[] chars = new char[olen];

    int iidx = 0;
    int oidx = 0;
    int charsLeft = len;
    while (charsLeft > 0) {
      int b0 = data[iidx++] & 0xff;
      int b1 = (charsLeft > 1) ? data[iidx++] & 0xff : 0;
      int b2 = (charsLeft > 2) ? data[iidx++] & 0xff : 0;
      int b24 = (b0 << 16) | (b1 << 8) | b2;

      int c0 = (b24 >> 18) & 0x3f;
      int c1 = (b24 >> 12) & 0x3f;
      int c2 = (b24 >> 6) & 0x3f;
      int c3 = b24 & 0x3f;

      chars[oidx++] = base64Chars[c0];
      chars[oidx++] = base64Chars[c1];
      chars[oidx++] = (charsLeft > 1) ? base64Chars[c2] : '=';
      chars[oidx++] = (charsLeft > 2) ? base64Chars[c3] : '=';

      charsLeft -= 3;
    }

    return new String(chars);
  }
}

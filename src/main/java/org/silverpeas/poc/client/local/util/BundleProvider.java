package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.GWT;

/**
 * @author Yohann Chastagnier
 */
public class BundleProvider {
  private static BundleProvider me = new BundleProvider();

  public final static String JSON_MESSAGES = "/org/silverpeas/poc/client/local/messages.json";
  public final static String JSON_TRANSLATIONS =
      "/org/silverpeas/poc/client/local/translations.json";

  private final Messages messageBundle = GWT.create(Messages.class);

  public static Messages msg() {
    return me.messageBundle;
  }
}

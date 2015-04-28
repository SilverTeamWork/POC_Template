package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;

/**
 * @author Yohann Chastagnier
 */
public class BundleProvider {
  private static BundleProvider me = new BundleProvider();

  private final Messages messageBundle = GWT.create(Messages.class);

  public static Messages msg() {
    return me.messageBundle;
  }
}

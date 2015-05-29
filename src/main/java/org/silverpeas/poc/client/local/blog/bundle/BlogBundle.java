package org.silverpeas.poc.client.local.blog.bundle;

import com.google.gwt.core.client.GWT;

/**
 * @author Yohann Chastagnier
 */
public class BlogBundle {
  private static BlogBundle me = new BlogBundle();

  private final Messages messageBundle = GWT.create(Messages.class);

  public static Messages msg() {
    return me.messageBundle;
  }
}

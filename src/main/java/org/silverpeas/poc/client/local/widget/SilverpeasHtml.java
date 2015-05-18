package org.silverpeas.poc.client.local.widget;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author Yohann Chastagnier
 */
public class SilverpeasHtml extends HTML {

  public SilverpeasHtml() {
    this("");
  }

  public SilverpeasHtml(final String html) {
    super(html);
    setStyleName("");
  }
}

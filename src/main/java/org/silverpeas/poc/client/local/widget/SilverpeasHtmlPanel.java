package org.silverpeas.poc.client.local.widget;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * @author Yohann Chastagnier
 */
public class SilverpeasHtmlPanel extends HTMLPanel {

  public enum TYPE {
    DIV, H1, H2, H3, P, ASIDE, SPAN, FOOTER, A
  }

  public SilverpeasHtmlPanel() {
    this(TYPE.DIV);
  }

  public SilverpeasHtmlPanel(final TYPE type) {
    super(type.name().toLowerCase(), "");
  }
}

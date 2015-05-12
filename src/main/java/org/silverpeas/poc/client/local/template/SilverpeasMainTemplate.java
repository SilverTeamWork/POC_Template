package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.header.HeaderWidget;

import javax.inject.Inject;

/**
 * The main template of the pages that made the Silverpeas web application.
 * It is made up of a header, a body, and a footer container for widgets and viewable components.
 * @author miguel
 */
@Templated
public class SilverpeasMainTemplate extends Composite {

  public final static String MAIN_HTML_TEMPLATE =
      "/org/silverpeas/poc/client/local/template/SilverpeasMainTemplate.html";

  public final static String MAIN_HTML_TEMPLATE_CONTENT_CONTAINER = "main-content";

  @Inject
  @DataField("header-container")
  public HeaderWidget header;

  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private HTMLPanel contentContainer = new HTMLPanel("");

  public HTMLPanel getContentContainer() {
    return contentContainer;
  }
}

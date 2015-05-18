package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.header.HeaderWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * The main template of the pages that made the Silverpeas web application.
 * It is made up of a header, a body, and a footer container for widgets and viewable components.
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasMainLayout extends SilverpeasComposite {

  public final static String DOCK_TEMPLATE = "/org/silverpeas/poc/client/local/template/dock.html";

  @Inject
  @DataField("header-container")
  private HeaderWidget header;

  @Inject
  @DataField("main-content")
  private SilverpeasHtmlPanel contentPanel;

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    // No parent for the main layout
    return null;
  }

  @Override
  public void onPageShowing() {
  }
}

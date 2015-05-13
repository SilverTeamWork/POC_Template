package org.silverpeas.poc.client.local.test.yocha.layout.template;

import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.header.HeaderWidget;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@ApplicationScoped
@Templated("MainTemplate.html")
public class SilverpeasMainComposite extends SilverpeasComposite {

  public final static String MAIN_HTML_TEMPLATE_CONTENT_CONTAINER = "main-content";
  public final static String DOCK_TEMPLATE =
      "/org/silverpeas/poc/client/local/test/yocha/layout/template/dock.html";

  @Inject
  @DataField("header-container")
  private HeaderWidget header;

  @Inject
  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private SilverpeasHtmlPanel contentContainer;

  @Override
  public void onPageShowing() {
  }

  @Override
  public Panel getContentPanel() {
    return contentContainer;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    // No parent for the main template composite
    return null;
  }
}

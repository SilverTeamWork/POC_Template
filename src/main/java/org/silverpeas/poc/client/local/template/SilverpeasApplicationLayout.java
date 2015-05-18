package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.menu.ApplicationMenuWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasApplicationLayout extends SilverpeasComposite {

  @Inject
  private SilverpeasSpaceLayout spaceLayout;

  @DataField
  private SilverpeasHtmlPanel title = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.H2);

  @Inject
  @DataField
  private ApplicationMenuWidget menu;

  @DataField
  private SilverpeasHtmlPanel edito = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.P);

  @DataField("aside-app")
  private SilverpeasHtmlPanel rightPanel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.ASIDE);

  @Inject
  @DataField("application-content")
  private SilverpeasHtmlPanel contentPanel;

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  public Panel getRightPanel() {
    return rightPanel;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return spaceLayout;
  }

  @Override
  public void onPageShowing() {
  }
}

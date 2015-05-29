package org.silverpeas.poc.client.local.template;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.SpaceContentMenuWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.menu.MenuWidget;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel.TYPE.*;

/**
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasApplicationLayout extends SilverpeasComposite {

  @Inject
  private SilverpeasMainLayout mainLayout;

  @Inject
  @DataField("breadcrumb")
  private SilverpeasHtmlPanel breadcrumbPanel;

  @Inject
  @DataField("nav-gauche")
  private SpaceContentMenuWidget spaceContentMenu;

  @DataField("app-section")
  private Element appSection = DOM.createElement("section");

  @DataField
  private SilverpeasHtmlPanel title = new SilverpeasHtmlPanel(SPAN);

  @Inject
  @DataField
  private MenuWidget menu;

  @DataField
  private SilverpeasHtmlPanel description = new SilverpeasHtmlPanel(P);

  @DataField("aside-app")
  private SilverpeasHtmlPanel rightPanel = new SilverpeasHtmlPanel(ASIDE);

  @Inject
  @DataField("application-footer")
  private SilverpeasHtmlPanel footerPanel;

  @Inject
  @DataField("application-content")
  private SilverpeasHtmlPanel contentPanel;

  private ApplicationInstance currentApplicationInstance;

  @AfterInitialization
  private void init() {
    Window.addWindowScrollHandler(new Window.ScrollHandler() {
      @Override
      public void onWindowScroll(final Window.ScrollEvent event) {
        if (Document.get().getScrollTop() >= 273) {
          getRightPanel().addStyleName("fixed");
        } else {
          getRightPanel().removeStyleName("fixed");
        }
      }
    });
  }

  public void onApplicationInstanceLoaded(@Observes LoadedApplicationInstance event) {
    final ApplicationInstance instance = event.getResource();
    setApplicationData(instance);
  }

  private void setApplicationData(ApplicationInstance instance) {
    currentApplicationInstance = instance;
    appSection.addClassName(instance.getComponentName());
  }

  public Panel getHeaderTitlePanel() {
    return this.title;
  }

  public Panel getHeaderDescriptionPanel() {
    return this.description;
  }

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  public Panel getRightPanel() {
    return rightPanel;
  }

  public Panel getFooterPanel() {
    return footerPanel;
  }

  public MenuWidget getMenuWidget() {
    return menu;
  }

  public ApplicationInstance getCurrentApplicationInstance() {
    return currentApplicationInstance;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainLayout;
  }

  @Override
  public void onPageShowing() {
    breadcrumbPanel.add(new BreadCrumbWidget());
  }

  @Override
  public void onPageHidden() {
    super.onPageHidden();
    breadcrumbPanel.clear();
    menu.clear();
    rightPanel.clear();
    footerPanel.clear();
  }

  public void setPageTitle(String title) {
    this.title.getElement().setInnerText(StringUtil.isDefined(title) ? title : "");
  }

  /**
   * For Application HomePage (description of application instance)
   * TODO improve this part...
   * @param description
   */
  public void setPageDescription(String description) {
    this.description.getElement()
        .setInnerText(StringUtil.isDefined(description) ? description : "");
  }
}

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
import org.silverpeas.poc.client.local.menu.ApplicationMenuWidget;
import org.silverpeas.poc.client.local.space.SpaceContentMenuWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasApplicationLayout extends SilverpeasComposite {

  @Inject
  private SilverpeasMainLayout mainLayout;

  @Inject
  @DataField
  private BreadCrumbWidget breadcrumb;

  @Inject
  @DataField("nav-gauche")
  private SpaceContentMenuWidget spaceContentMenu;

  @DataField("app-section")
  private Element appSection = DOM.createElement("section");

  @DataField
  private Element title = DOM.createSpan();

  @Inject
  @DataField
  private ApplicationMenuWidget menu;

  @DataField
  private Element edito = DOM.createElement("p");

  @DataField("aside-app")
  private SilverpeasHtmlPanel rightPanel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.ASIDE);

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
    title.setInnerText(instance.getLabel());
    if (StringUtil.isDefined(instance.getDescription())) {
      edito.setInnerHTML(instance.getDescription());
    }
    appSection.addClassName(instance.getComponentName());
  }

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  public Panel getRightPanel() {
    return rightPanel;
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
  }

  @Override
  public void onPageHidden() {
    super.onPageHidden();
    rightPanel.clear();
  }
}

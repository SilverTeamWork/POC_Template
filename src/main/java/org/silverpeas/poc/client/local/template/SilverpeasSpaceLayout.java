package org.silverpeas.poc.client.local.template;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbSpaceItem;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentListWidget;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainLayout
    .MAIN_HTML_TEMPLATE_CONTENT_CONTAINER;

/**
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasSpaceLayout extends SilverpeasComposite {

  @Inject
  private SilverpeasMainLayout mainLayout;

  @Inject
  @DataField("menu-content")
  private SpaceContentListWidget spaceMenu;

  @Inject
  @DataField
  private BreadCrumbWidget breadcrumb;

  @Inject
  @DataField
  private Anchor menuToggle;

  @DataField
  private Element mainTitle = DOM.createSpan();

  @Inject
  @DataField("main-content")
  private SilverpeasHtmlPanel contentPanel;

  private boolean menuIsShowed = true;

  @AfterInitialization
  private void init() {
    menuToggle.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        menuIsShowed = !menuIsShowed;
        if (menuIsShowed) {
          DOM.getElementById("nav-gauche").setClassName("aside-gauche");
        } else {
          DOM.getElementById("nav-gauche").setClassName("aside-gauche minimize");
        }
      }
    });
    breadcrumb.refresh();
  }

  public void onSpaceSelection(@Observes SpaceSelection spaceSelection) {
    final Space selectedSpace = spaceSelection.getSelectedSpace();
    Log.dev("Space selected: " + selectedSpace.getLabel());
    spaceMenu.setModel(selectedSpace);
    breadcrumb.getModel().setItem(new BreadCrumbSpaceItem(selectedSpace));
    mainTitle.setInnerText(selectedSpace.getLabel());
    breadcrumb.refresh();
  }

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainLayout;
  }

  @Override
  public void onPageShowing() {
  }
}

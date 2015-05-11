package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumb;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbItem;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.header.HeaderWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentListWidget;
import org.silverpeas.poc.client.local.space.SpaceSelection;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import static org.silverpeas.poc.client.local.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author miguel
 */
@Templated(MAIN_HTML_TEMPLATE)
public class SilverpeasMainTemplate extends Composite {

  public final static String MAIN_HTML_TEMPLATE =
      "/org/silverpeas/poc/client/local/SilverpeasMainTemplate.html";

  public final static String MAIN_HTML_TEMPLATE_CONTENT_CONTAINER = "main-content";

  @Inject
  @DataField("header-container")
  private HeaderWidget header;

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

  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private HTMLPanel contentContainer = new HTMLPanel("");

  private Space selectedSpace = null;
  private boolean menuIsShowed = true;

  @PageShowing
  public void pageShowing() {
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
  }

  public void onSpaceSelection(@Observes SpaceSelection spaceSelection) {
    selectedSpace = spaceSelection.getSelectedSpace();
    GWT.log("Space selected: " + selectedSpace.getLabel());
    spaceMenu.setModel(selectedSpace);
    BreadCrumb breadCrumbModel = new BreadCrumb();
    breadCrumbModel.getItems().add(new BreadCrumbItem(selectedSpace.getLabel(), selectedSpace));
    breadcrumb.setModel(breadCrumbModel);
    mainTitle.setInnerText(selectedSpace.getLabel());
  }

  public HTMLPanel getContentContainer() {
    return contentContainer;
  }
}

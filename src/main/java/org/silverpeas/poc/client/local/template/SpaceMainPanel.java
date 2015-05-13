package org.silverpeas.poc.client.local.template;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.PageShowing;
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
import java.util.ArrayList;
import java.util.List;

import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate
    .MAIN_HTML_TEMPLATE_CONTENT_CONTAINER;

/**
 * @author miguel
 */
@Templated
public class SpaceMainPanel extends Composite {
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
  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private SilverpeasHtmlPanel contentContainer;

  private boolean menuIsShowed = true;

  /*@AfterInitialization
  private void removeApplicationScopedWidgets() {
    Log.dev(this.getClass().getName() + ".beforeDisplaying() call");
    // Remove parent of application scoped elements
    for (Widget applicationScopedWidget : getApplicationScopedWidgets()) {
      if (applicationScopedWidget != null) {
        applicationScopedWidget.removeFromParent();
        Log.dev(this.getClass().getName() + ".beforeDisplaying() call - remove " +
            applicationScopedWidget.getElement().getId());
      }
    }
  }*/

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

  /**
   * Gets the list of widget annotated with {@link ApplicationScoped} or {@link EntryPoint}.
   * If a widget is not null, then it is removed from the DOM before to be again injected in the
   * DOM.
   */
  public List<Widget> getApplicationScopedWidgets() {
    List<Widget> widgets = new ArrayList<>();
    widgets.add(breadcrumb);
    return widgets;
  }

  public void onSpaceSelection(@Observes SpaceSelection spaceSelection) {
    final Space selectedSpace = spaceSelection.getSelectedSpace();
    Log.dev("Space selected: " + selectedSpace.getLabel());
    spaceMenu.setModel(selectedSpace);
    breadcrumb.getModel().setItem(new BreadCrumbSpaceItem(selectedSpace));
    mainTitle.setInnerText(selectedSpace.getLabel());
    breadcrumb.refresh();
  }

  public SilverpeasHtmlPanel getContentContainer() {
    return contentContainer;
  }
}

package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumb;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbItem;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentListWidget;
import org.silverpeas.poc.client.local.space.SpaceCriteria;
import org.silverpeas.poc.client.local.space.SpaceSelection;
import org.silverpeas.poc.client.local.space.SpaceWidget;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.silverpeas.poc.client.local.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;

/**
 * @author miguel
 */
@Templated(MAIN_HTML_TEMPLATE)
public class SilverpeasMainTemplate extends Composite {

  public final static String MAIN_HTML_TEMPLATE =
      "/org/silverpeas/poc/client/local/SilverpeasMainTemplate.html";

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<Space, SpaceWidget> spaces;

  @Inject
  @DataField("menu-content")
  private SpaceContentListWidget spaceMenu;

  @Inject
  @DataField("breadcrumb-content")
  private BreadCrumbWidget breadcrumb;

  @Inject
  @DataField
  private Anchor menuToggle;

  @DataField
  private Element mainTitle = DOM.createSpan();

  @DataField("main-content")
  private HTMLPanel contentContainer = new HTMLPanel("");

  private Space selectedSpace = null;
  private boolean menuIsShowed = true;

  @PageShowing
  public void init() {
    loadRootSpaces();
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

  private void loadRootSpaces() {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        JsArray<Space> jsRootSpaces = JsonUtils.safeEval(response.getText());
        List<Space> rootSpaces = new ArrayList<>(jsRootSpaces.length());
        for (int i = 0; i < jsRootSpaces.length(); i++) {
          Space space = jsRootSpaces.get(i);
          if (space.isRoot()) {
            if (space.getRank() == 0) {
              space.setAsCurrent();
              selectedSpace = space;
            }
            rootSpaces.add(space.getRank(), space);
          }
        }
        spaces.setItems(rootSpaces);
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        GWT.log("Error while getting root spaces: " + response.getStatusText());
      }
    }).get(new SpaceCriteria());
  }

  public void onSpaceSelection(@Observes SpaceSelection spaceSelection) {
    selectedSpace = spaceSelection.getSelectedSpace();
    GWT.log("Space selected: " + selectedSpace.getLabel());
    spaceMenu.setItems(selectedSpace.getContent());
    BreadCrumb breadCrumbModel = new BreadCrumb();
    breadCrumbModel.getItems().add(new BreadCrumbItem(selectedSpace.getLabel(), selectedSpace));
    breadcrumb.setModel(breadCrumbModel);
    mainTitle.setInnerText(selectedSpace.getLabel());
  }

  public HTMLPanel getContentContainer() {
    return contentContainer;
  }
}

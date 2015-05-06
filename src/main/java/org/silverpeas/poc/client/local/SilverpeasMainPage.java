package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumb;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbItem;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;
import org.silverpeas.poc.client.local.space.SpaceContentListWidget;
import org.silverpeas.poc.client.local.space.SpaceContentWidget;
import org.silverpeas.poc.client.local.space.SpaceCriteria;
import org.silverpeas.poc.client.local.space.SpaceSelection;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miguel
 */
@Page(path = "home")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
@EntryPoint
public class SilverpeasMainPage extends Composite {

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

  private Space selectedSpace = null;
  private boolean menuIsShowed = true;

  @PostConstruct
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
}

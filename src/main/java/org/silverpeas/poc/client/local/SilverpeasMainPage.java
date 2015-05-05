package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
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
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceCriteria;
import org.silverpeas.poc.client.local.space.SpaceSelection;
import org.silverpeas.poc.client.local.space.SpaceWidget;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miguel
 */
@Page(path = "home")
@Templated("SilverpeasMainPage.html")
@Bundle("messages.json")
@EntryPoint
public class SilverpeasMainPage extends Composite {

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<Space, SpaceWidget> spaces;

  private Space selectedSpace = null;

  @PostConstruct
  public void init() {
    loadRootSpaces();
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
            if (space.isEqual(selectedSpace)) {
              space.setAsCurrent();
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

  public void onnSpaceSelection(@Observes SpaceSelection spaceSelection) {
    selectedSpace = spaceSelection.getSelectedSpace();
    GWT.log("Space selected: " + selectedSpace.getLabel());
  }
}

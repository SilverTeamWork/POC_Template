package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miguel
 */
@Templated("../SilverpeasMainPage.html#spaces-item")
public class SpaceWidget extends Composite implements HasModel<Space> {

  private Space space;

  @Inject
  @DataField("spaces-item-label")
  private Anchor spaceLabel;

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<SpaceContent, SpaceContentWidget> spaceContents;

  @Inject
  private Event<SpaceSelection> spaceSelected;

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public Space getModel() {
    return space;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final Space model) {
    this.space = model;
    this.spaceLabel.setText(this.space.getLabel());
    this.spaceLabel.setHref("#");
    this.spaceLabel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        spaceSelected.fire(new SpaceSelection(getModel()));
      }
    });
    loadSpaceContent(getModel());
  }

  public void unselect(@Observes SpaceSelection selection) {
    if (selection.getSelectedSpace() == this.space) {
      getModel().setAsCurrent();
      addStyleName("selected");
    } else {
      getModel().unsetAsCurrent();
      removeStyleName("selected");
    }
  }

  private void loadSpaceContent(final Space currentSpace) {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        JsArray<Space> jsSpaces = JsonUtils.safeEval(response.getText());
        final List<SpaceContent> spaceContent = new ArrayList<>(jsSpaces.length());
        for (int i = 0; i < jsSpaces.length(); i++) {
          Space space = jsSpaces.get(i);
          spaceContent.add(space.getRank(), space);
        }

        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            JsArray<SilverpeasApplication> jsApps = JsonUtils.safeEval(response.getText());
            for (int i = 0; i < jsApps.length(); i++) {
              SilverpeasApplication app = jsApps.get(i);
              spaceContent.add(app);
            }
            spaceContents.setItems(spaceContent);
            getModel().setContent(spaceContent);
            // once we loaded all the space data, we fire an event in the case the space is the
            // current one.
            if (getModel().isCurrent()) {
              addStyleName("selected");
              spaceSelected.fire(new SpaceSelection(getModel()));
            }
            spaceSelected.fire(new SpaceSelection(getModel()));
          }
        }).onError(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            GWT.log("Error while getting root spaces: " + response.getStatusText());
          }
        }).get(new SpaceCriteria().forUrl(currentSpace.getComponentsUri()));

      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        GWT.log("Error while getting root spaces: " + response.getStatusText());
      }
    }).get(new SpaceCriteria().forUrl(currentSpace.getSpacesUri()));
  }
}

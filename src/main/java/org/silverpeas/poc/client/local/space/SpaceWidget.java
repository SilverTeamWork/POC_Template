package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.LoadedSpaceContent;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author miguel
 */
@Templated
public class SpaceWidget extends Composite implements HasModel<Space> {

  private Space space;

  @Inject
  @DataField("space-item-label")
  private SpaceTransitionAnchor spaceAnchor;

  @Inject
  @DataField("space-item-content")
  private SpaceContentListWidget spaceContentMenu;

  @Inject
  private SpaceLoader spaceLoader;

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
    this.spaceAnchor.setSpace(this.space);
    setCurrentSelected(this.space.isCurrent());
    loadSpaceContent(getModel());
  }

  public void unselect(Space selectedSpace) {
    setCurrentSelected(selectedSpace.getId().equals(this.space.getId()));
    Log.debug("SpaceWidget - unselect - selection.spaceId={0}, current spaceId={1}",
        selectedSpace.getId(), getModel().getId());
  }

  private void setCurrentSelected(boolean selected) {
    if (selected) {
      getModel().setAsCurrent();
      addStyleName("selected");
    } else {
      getModel().unsetAsCurrent();
      removeStyleName("selected");
    }
  }

  private void loadSpaceContent(final Space currentSpace) {
    spaceLoader.loadSpaceContent(currentSpace);
  }

  protected void onSpaceContentLoaded(@Observes LoadedSpaceContent loadedSpaceContent) {
    if (loadedSpaceContent.getResource() == getModel()) {
      spaceContentMenu.setModel(loadedSpaceContent.getResource());
    }
  }

  protected void onSpaceSelected(@Observes SelectedSpace event) {
    Space selectedSpace = event.getResource();
    if (selectedSpace.isHome()) {
      Log.debug("SpaceWidget - onSpaceSelected - select spaceId={0}, current spaceId={1}",
          selectedSpace.getId(), getModel().getId());
      unselect(selectedSpace);
    }
  }

  protected void onSpaceLoaded(@Observes LoadedSpace event) {
    Space loadedSpace = event.getResource();
    Log.debug("SpaceWidget - onSpaceLoaded - select spaceId={0}, current spaceId={1}",
        loadedSpace.getId(), getModel().getId());
    unselect(loadedSpace);
  }

  protected void onApplicationInstanceLoaded(
      @Observes LoadedApplicationInstance loadedApplicationInstance) {
    Space parentApplicationInstanceSpace = loadedApplicationInstance.getResource().getParent();
    Log.debug("SpaceWidget - onApplicationInstanceLoaded - select spaceId={0}, current spaceId={1}",
        parentApplicationInstanceSpace.getId(), getModel().getId());
    unselect(parentApplicationInstanceSpace);
  }
}

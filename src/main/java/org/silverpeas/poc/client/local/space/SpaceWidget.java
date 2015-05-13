package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

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
  private SpaceContentListWidget spaceContents;

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
    spaceLoader.loadSpaceContent(currentSpace);
  }

  protected void onSpaceContentLoaded(@Observes SpaceContentLoaded spaceContentLoaded) {
    if (spaceContentLoaded.getSpaceWithLoadedContent() == getModel()) {
      spaceContents.setModel(spaceContentLoaded.getSpaceWithLoadedContent());
    }
  }
}

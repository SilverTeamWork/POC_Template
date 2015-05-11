package org.silverpeas.poc.client.local.header;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.SpaceLoaded;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.SpaceSelection;
import org.silverpeas.poc.client.local.space.SpaceWidget;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author miguel
 */
@Templated
public class HeaderWidget extends Composite {

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<Space, SpaceWidget> spaces;

  @Inject
  private SpaceLoader spaceLoader;

  @Inject
  private Event<SpaceSelection> spaceSelection;

  private Space selectedSpace = null;

  @AfterInitialization
  public void loadRootSpaces() {
    spaceLoader.loadRootSpaces();
  }

  protected void onRootSpacesLoaded(@Observes SpaceLoaded spaceLoaded) {
    if (!spaceLoaded.getLoadedSpaces().isEmpty()) {
      selectedSpace = spaceLoaded.getLoadedSpaces().get(0);
      selectedSpace.setAsCurrent();
    }
    spaces.setItems(spaceLoaded.getLoadedSpaces());
  }

  protected void onRootSpaceContentLoaded(@Observes SpaceContentLoaded spaceContentLoaded) {
    Space space = spaceContentLoaded.getSpaceWithLoadedContent();
    if (space == selectedSpace) {
      spaceSelection.fire(new SpaceSelection(selectedSpace));
    }
  }
}

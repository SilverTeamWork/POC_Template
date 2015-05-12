package org.silverpeas.poc.client.local.header;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

/**
 * A widget to be rendered at the head of a page in Silverpeas. It lets currently the user to
 * navigate among the different root workspaces in Silverpeas.
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
  protected void loadRootSpaces() {
    spaceLoader.loadRootSpaces();
  }

  protected void onRootSpacesLoaded(@Observes SpaceLoaded spaceLoaded) {
    addVirtualHomeSpace(spaceLoaded.getLoadedSpaces());
    if (!spaceLoaded.getLoadedSpaces().isEmpty()) {
      selectedSpace = spaceLoaded.getLoadedSpaces().get(0);
      selectedSpace.setAsCurrent();
    }
    spaces.setItems(spaceLoaded.getLoadedSpaces());
  }

  private void addVirtualHomeSpace(final List<Space> spaces) {
    Space homeSpace = JsonUtils.safeEval("{\"label\":\"Home sweet home\"," +
            "\"spacesURI\":\"http://localhost:8000/silverpeas/services/spaces/1/spaces\"," +
            "\"componentsURI\":\"http://localhost:8000/silverpeas/services/spaces/1/spaces\"," +
            "\"level\":0," +
            "\"rank\": 0," +
            "\"id\":-1," +
            "\"home\": true," +
            " \"content\":[]}");
    spaces.add(0, homeSpace);
  }

  protected void onRootSpaceContentLoaded(@Observes SpaceContentLoaded spaceContentLoaded) {
    Space space = spaceContentLoaded.getSpaceWithLoadedContent();
    if (space == selectedSpace) {
      spaceSelection.fire(new SpaceSelection(selectedSpace));
    }
  }
}

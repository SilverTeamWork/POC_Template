package org.silverpeas.poc.client.local.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
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
    List<Space> rootSpaces = new ArrayList<>(spaceLoaded.getLoadedSpaces());
    addVirtualHomeSpace(rootSpaces);
    if (!rootSpaces.isEmpty()) {
      selectedSpace = rootSpaces.get(0);
      selectedSpace.setAsCurrent();
    }
    spaces.setItems(rootSpaces);
  }

  private void addVirtualHomeSpace(final List<Space> spaces) {
    spaces.add(0, HomeSpaceProvider.getHomeSpace());
  }

  protected void onRootSpaceContentLoaded(@Observes SpaceContentLoaded spaceContentLoaded) {
    Space space = spaceContentLoaded.getSpaceWithLoadedContent();
    if (space == selectedSpace) {
      spaceSelection.fire(new SpaceSelection(selectedSpace));
    }
  }
}

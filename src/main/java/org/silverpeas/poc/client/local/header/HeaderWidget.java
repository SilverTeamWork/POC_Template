package org.silverpeas.poc.client.local.header;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.silverpeas.poc.client.local.space.event.LoadedRootSpaces;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
  private Event<SelectedSpace> spaceSelection;

  @AfterInitialization
  protected void loadRootSpaces() {
    HomeSpaceProvider.getHomeSpace().setAsCurrent();
    spaceLoader.loadRootSpaces();
  }

  protected void onRootSpacesLoaded(@Observes LoadedRootSpaces loadedRootSpaces) {
    List<Space> rootSpaces = new ArrayList<>(loadedRootSpaces.getLoadedSpaces());
    addVirtualHomeSpace(rootSpaces);
    spaces.setItems(rootSpaces);
  }

  private void addVirtualHomeSpace(final List<Space> spaces) {
    spaces.add(0, HomeSpaceProvider.getHomeSpace());
  }
}

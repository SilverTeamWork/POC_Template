package org.silverpeas.poc.client.local.header;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.silverpeas.poc.client.local.space.event.LoadedRootSpaces;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.user.User;
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
  @DataField
  private Image avatar;

  @DataField
  private Element name = DOM.createSpan();

  @Inject
  @DataField
  private Anchor disconnection;

  @Inject
  private SpaceLoader spaceLoader;

  @Inject
  private Event<SelectedSpace> spaceSelection;

  @AfterInitialization
  protected void loadRootSpaces() {
    HomeSpaceProvider.getHomeSpace().setAsCurrent();
  }

  protected void onRootSpacesLoaded(@Observes LoadedRootSpaces loadedRootSpaces) {
    List<Space> rootSpaces = new ArrayList<>(loadedRootSpaces.getLoadedSpaces());
    addVirtualHomeSpace(rootSpaces);
    spaces.setItems(rootSpaces);
  }

  /**
   * If a widget contains one or more child widgets that are not in the logical
   * widget hierarchy (the child is physically connected only on the DOM level),
   * it must override this method and call {@link #onAttach()} for each of its
   * child widgets.
   * @see #onAttach()
   */
  @Override
  protected void doAttachChildren() {
    super.doAttachChildren();
    if (spaces.getValue().isEmpty()) {
      Log.dev("ATTACH HEADER");
      spaceLoader.loadRootSpaces();
      User currentUser = CurrentUser.get();
      name.setInnerText(currentUser.getName());
      avatar.setUrl(UrlManager.getSilverpeasUrl(currentUser.getAvatar()));
      avatar.setAltText("Avatar " + currentUser.getName());
      disconnection.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          CurrentUser.markAsDisconnected();
        }
      });
    }
  }

  private void addVirtualHomeSpace(final List<Space> spaces) {
    spaces.add(0, HomeSpaceProvider.getHomeSpace());
  }
}

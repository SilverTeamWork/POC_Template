package org.silverpeas.poc.client.local.space;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import io.reinert.gdeferred.DoneCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@ApplicationScoped
@Templated
public class SpaceContentMenuComposite extends Composite {

  @Inject
  @DataField("menu-content")
  private SpaceContentListWidget spaceContentMenu;

  @Inject
  @DataField
  private Anchor menuToggle;

  private boolean menuIsShowed = true;

  @AfterInitialization
  private void init() {
    menuToggle.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        menuIsShowed = !menuIsShowed;
        if (menuIsShowed) {
          removeStyleName("minimize");
        } else {
          addStyleName("minimize");
        }
      }
    });
  }

  public void onSpaceSelected(@Observes SelectedSpace event) {
    final Space selectedSpace = event.getResource();
    Log.debug("SpaceContentMenuWidget - onSpaceSelected - selected spaceId={0}",
        selectedSpace.getId());
    loadSpaceMenu(selectedSpace);
  }

  public void onSpaceLoaded(@Observes LoadedSpace event) {
    final Space loadedSpace = event.getResource();
    if (spaceContentMenu.getModel() == null) {
      // The user accessed the space directly by the URL input of its browser
      Log.debug("SpaceContentMenuWidget - onSpaceLoaded - loading space content from id={0}",
          loadedSpace.getId());
      SpaceLoader.get().loadSpaceContent(loadedSpace).then(new DoneCallback<Space>() {
        @Override
        public void onDone(final Space space) {
          loadSpaceMenu(space);
        }
      });
    }
  }

  public void onApplicationInstanceLoaded(@Observes LoadedApplicationInstance event) {
    final ApplicationInstance instance = event.getResource();
    SpaceLoader.get().loadSpaceContent(instance.getParent()).then(new DoneCallback<Space>() {
      @Override
      public void onDone(final Space space) {
        loadSpaceMenu(space);
      }
    });
  }

  private void loadSpaceMenu(Space space) {
    spaceContentMenu.setModel(space);
  }
}
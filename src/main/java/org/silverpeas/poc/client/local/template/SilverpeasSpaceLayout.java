package org.silverpeas.poc.client.local.template;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;
import io.reinert.gdeferred.DoneCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentListWidget;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author miguel
 */
@ApplicationScoped
@Templated
public class SilverpeasSpaceLayout extends SilverpeasComposite {

  @Inject
  private SilverpeasMainLayout mainLayout;

  @Inject
  @DataField("menu-content")
  private SpaceContentListWidget spaceMenu;

  @Inject
  @DataField
  private BreadCrumbWidget breadcrumb;

  @Inject
  @DataField
  private Anchor menuToggle;

  @DataField
  private Element mainTitle = DOM.createSpan();

  @Inject
  @DataField("main-content")
  private SilverpeasHtmlPanel contentPanel;

  private boolean menuIsShowed = true;

  @AfterInitialization
  private void init() {
    menuToggle.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        menuIsShowed = !menuIsShowed;
        if (menuIsShowed) {
          DOM.getElementById("nav-gauche").setClassName("aside-gauche");
        } else {
          DOM.getElementById("nav-gauche").setClassName("aside-gauche minimize");
        }
      }
    });
  }

  public void onSpaceSelected(@Observes SelectedSpace event) {
    final Space selectedSpace = event.getResource();
    Log.debug("SilverpeasSpaceLayout - onSpaceSelected - selected spaceId={0}",
        selectedSpace.getId());
    loadSpaceMenu(selectedSpace);
  }

  public void onSpaceLoaded(@Observes LoadedSpace event) {
    final Space loadedSpace = event.getResource();
    if (spaceMenu.getModel() == null) {
      // The user accessed the space directly by the URL input of its browser
      Log.debug("SilverpeasSpaceLayout - onSpaceLoaded - loading space content from id={0}",
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
    spaceMenu.setModel(space);
    mainTitle.setInnerText(space.getLabel());
  }

  @Override
  public Panel getContentPanel() {
    return contentPanel;
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return mainLayout;
  }

  @Override
  public void onPageShowing() {
  }
}

package org.silverpeas.poc.client.local.template;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.breadcrumb.BreadCrumbWidget;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContentMenuWidget;
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
  @DataField
  private BreadCrumbWidget breadcrumb;

  @Inject
  @DataField("nav-gauche")
  private SpaceContentMenuWidget spaceContentMenu;

  @DataField
  private Element mainTitle = DOM.createSpan();

  @Inject
  @DataField("space-content")
  private SilverpeasHtmlPanel contentPanel;


  public void onSpaceSelected(@Observes SelectedSpace event) {
    final Space selectedSpace = event.getResource();
    Log.debug("SilverpeasSpaceLayout - onSpaceSelected - selected spaceId={0}",
        selectedSpace.getId());
    setMainTitle(selectedSpace);
  }

  public void onSpaceLoaded(@Observes LoadedSpace event) {
    final Space loadedSpace = event.getResource();
    setMainTitle(loadedSpace);
  }

  private void setMainTitle(Space space) {
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

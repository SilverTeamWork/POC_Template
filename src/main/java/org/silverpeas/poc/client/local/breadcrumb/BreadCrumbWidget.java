package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.DisplayedInternalApplicationInstancePage;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A widget to render a breadcrumb indicating the navigation level of the user in the current page.
 * @author miguel
 */

@Templated
public class BreadCrumbWidget extends Composite {

  @Inject
  @DataField
  private SilverpeasHtmlPanel content;

  @Inject
  private Instance<BreadCrumbTransitionAnchor> breadCrumbItemAnchorProvider;

  private List<BreadCrumbItem> items = new ArrayList<>();

  @AfterInitialization
  protected void init() {
    items.clear();
    items.add(new BreadCrumbSpaceItem(HomeSpaceProvider.getHomeSpace()));
  }

  private void onSpaceSelection(@Observes SelectedSpace spaceSelection) {
    Space selectedSpace = spaceSelection.getResource();
    if (selectedSpace.isHome()) {
      init();
      refresh();
    }
  }

  private void onSpaceLoaded(@Observes LoadedSpace event) {
    init();
    Space space = event.getResource();
    Log.dev("BreadCrumbWidget - onSpaceLoaded - {0} (id={1})", space.getLabel(), space.getId());
    performSpaceContent(space);
    refresh();
  }

  private void onApplicationInstanceLoaded(@Observes LoadedApplicationInstance instanceLoaded) {
    init();
    ApplicationInstance instance = instanceLoaded.getResource();
    Log.debug("BreadCrumbWidget - onApplicationInstanceLoaded - {0} (type={1}, id={2})",
        instance.getLabel(), instance.getComponentName(), instance.getId());
    performSpaceContent(instance);
    refresh();
  }

  private void performSpaceContent(SpaceContent spaceContent) {
    if (spaceContent != null) {
      if (spaceContent.isApplication()) {
        items.add(1, new BreadCrumbAppItem((ApplicationInstance) spaceContent));
      } else {
        items.add(1, new BreadCrumbSpaceItem((Space) spaceContent));
      }
      performSpaceContent(spaceContent.getParent());
    }
  }

  private void onInternalApplicationInstancePageDisplayed(
      @Observes DisplayedInternalApplicationInstancePage event) {
    ListIterator<BreadCrumbItem> itemListIterator = items.listIterator(2);
    boolean positionFound = false;
    while (itemListIterator.hasNext()) {
      BreadCrumbItem item = itemListIterator.next();
      if (positionFound || item.getTargetPageName().equals(event.getPageName())) {
        positionFound = true;
        itemListIterator.remove();
      }
    }
    items.add(new BreadCrumbInternalAppPageItem<>(event.getPage(), event.getPageState()));
    refresh();
  }

  private void refresh() {
    content.clear();
    Iterator<BreadCrumbItem> itemIt = items.iterator();
    while (itemIt.hasNext()) {
      BreadCrumbItem item = itemIt.next();
      item.setEnabled(itemIt.hasNext());
      BreadCrumbTransitionAnchor widget = breadCrumbItemAnchorProvider.get();
      widget.setModel(item);
      content.add(widget);
    }
  }
}

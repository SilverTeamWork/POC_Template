package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.ApplicationInstanceSelection;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
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

  public void onSpaceSelection(@Observes SpaceSelection spaceSelection) {
    Space selectedSpace = spaceSelection.getSelectedSpace();
    if (!selectedSpace.isHome()) {
      init();
      items.add(new BreadCrumbSpaceItem(selectedSpace));
    }
  }

  public void onApplicationInstanceSelection(
      @Observes ApplicationInstanceSelection instanceSelection) {
    ApplicationInstance selectedInstance = instanceSelection.getSelectedInstance();
    if (!items.isEmpty()) {
      ListIterator<BreadCrumbItem> listIterator = items.listIterator(items.size());
      while (listIterator.hasPrevious()) {
        BreadCrumbItem breadCrumbItem = listIterator.previous();
        if (!(breadCrumbItem instanceof BreadCrumbSpaceItem)) {
          listIterator.remove();
        }
        if (breadCrumbItem instanceof BreadCrumbAppItem) {
          break;
        }
      }
    }
    items.add(new BreadCrumbAppItem(selectedInstance));
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

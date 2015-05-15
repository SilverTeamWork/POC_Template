package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
      items.add(new BreadCrumbSpaceItem(spaceSelection.getSelectedSpace()));
    }
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
    items.get(items.size() - 1).setEnabled(false);
    for (BreadCrumbItem item: items) {
      BreadCrumbItemWidget widget = new BreadCrumbItemWidget();
      widget.setModel(item);
      content.add(widget);
    }
  }
}

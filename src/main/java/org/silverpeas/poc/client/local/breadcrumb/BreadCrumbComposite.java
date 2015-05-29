package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.databinding.client.BindableListWrapper;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.widget.SilverpeasListChangeHandler;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A widget to render a breadcrumb indicating the navigation level of the user in the current page.
 * @author miguel
 */
@ApplicationScoped
@Templated
public class BreadCrumbComposite extends Composite {

  private BindableListWrapper<BreadCrumbItem> items =
      new BindableListWrapper<>(new ArrayList<BreadCrumbItem>());

  @Inject
  @DataField
  private ListWidget<BreadCrumbItem, BreadCrumbItemWidget> content;

  @Inject
  @DataField("back-container")
  private SilverpeasHtmlPanel backContainer;

  @DataField("back-button")
  private SilverpeasTransitionAnchor backButton = new SilverpeasTransitionAnchor() {
    @Override
    protected void onClickEvent() {
      backContainer.setVisible(items.size() > 3);
    }
  };

  @AfterInitialization
  protected void setup() {
    items.add(new BreadCrumbSpaceItem(HomeSpaceProvider.getHomeSpace()));
    content.setItems(items);
    backContainer.setVisible(false);
    backContainer.add(backButton);
    backButton.setTitle(I18n.format(CommonTranslations.BACK_TO_PREVIOUS_PAGE));
    backButton.setText(backButton.getTitle());
    items.addChangeHandler(new SilverpeasListChangeHandler<BreadCrumbItem>() {

      @Override
      protected void onListChanged(final List<BreadCrumbItem> source) {
        super.onListChanged(source);
        refreshView();
      }
    });
  }

  private void onSpaceSelected(@Observes SelectedSpace event) {
    Space space = event.getResource();
    Log.debug("BreadCrumbWidget - onSpaceSelected - {0} (id={1})", space.getLabel(), space.getId());
    backContainer.setVisible(false);
  }

  private void onSpaceLoaded(@Observes LoadedSpace event) {
    Space space = event.getResource();
    Log.debug("BreadCrumbWidget - onSpaceLoaded - {0} (id={1})", space.getLabel(), space.getId());
    performSpaceContent(space);
  }

  private void onApplicationInstanceLoaded(@Observes LoadedApplicationInstance instanceLoaded) {
    ApplicationInstance instance = instanceLoaded.getResource();
    Log.debug("BreadCrumbWidget - onApplicationInstanceLoaded - {0} (type={1}, id={2})",
        instance.getLabel(), instance.getComponentName(), instance.getId());
    performSpaceContent(instance);
  }

  private void performSpaceContent(SpaceContent spaceContent) {
    if (spaceContent != null) {
      final BreadCrumbItem item;
      if (spaceContent.isApplication()) {
        item = new BreadCrumbAppItem((ApplicationInstance) spaceContent);
      } else {
        item = new BreadCrumbSpaceItem((Space) spaceContent);
      }
      performSpaceContent(spaceContent.getParent());
      push(1, item);
    }
  }

  protected void push(BreadCrumbItem newItem) {
    push(3, newItem);
  }

  private void push(int startIndex, BreadCrumbItem newItem) {
    ListIterator<BreadCrumbItem> itemListIterator = items.listIterator(startIndex);
    boolean positionFound = false;
    while (itemListIterator.hasNext()) {
      BreadCrumbItem item = itemListIterator.next();
      if (item.getTargetPageName().equals(newItem.getTargetPageName())) {
        itemListIterator.set(newItem);
        positionFound = true;
      } else if (positionFound) {
        itemListIterator.remove();
      }
    }
    if (!positionFound) {
      items.add(newItem);
    }
  }

  /**
   * Refreshing the breadcrumb according to the item contents.<br/>
   * The rules are the following ones:
   * <ul>
   * <li>if it exists more that 2 items (application page at minimal) then the back button is
   * displayed</li>
   * <li>the last item is disabled unless it corresponds to the homepage breadcrumb item</li>
   * <li>the item before the last is enabled if any</li>
   * </ul>
   */
  private void refreshView() {
    if (items.size() > 2) {
      backContainer.setVisible(true);
      BreadCrumbItem item = items.get(items.size() - 2);
      backButton.initHref(item.getTargetPageName(), item.getTransitionParameters());
    } else {
      backContainer.setVisible(false);
    }
    int lastIndex = items.size() - 1;
    if (lastIndex > 0) {
      items.get(lastIndex - 1).setEnabled(true);
      items.get(lastIndex).setEnabled(false);
    }
  }
}

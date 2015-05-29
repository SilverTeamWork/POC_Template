package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.databinding.client.BindableListWrapper;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.event.widget.ContentChangeEvent;
import org.silverpeas.poc.api.event.widget.ContentChangeHandler;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
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
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A widget to render a breadcrumb indicating the navigation level of the user in the current page.
 * @author miguel
 */

@ApplicationScoped
@Templated
public class BreadCrumbComposite extends Composite {

  @Inject
  @DataField
  private SilverpeasHtmlPanel content;

  @Inject
  @DataField("back-container")
  private SilverpeasHtmlPanel backContainer;

  @DataField("back-button")
  private SilverpeasTransitionAnchor backButton = new SilverpeasTransitionAnchor() {
    @Override
    protected void onClickEvent() {
      backButtonClickedEvent.fire(new BackButtonClicked());
    }
  };

  @Inject
  private Event<BackButtonClicked> backButtonClickedEvent;

  @Inject
  private Instance<BreadCrumbTransitionAnchor> breadCrumbItemAnchorProvider;

  private BindableListWrapper<BreadCrumbItem> items =
      new BindableListWrapper<>(new ArrayList<BreadCrumbItem>());

  @AfterInitialization
  protected void setup() {
    backContainer.setVisible(false);
    backContainer.add(backButton);
    backButton.setTitle(I18n.format(CommonTranslations.BACK_TO_PREVIOUS_PAGE));
    backButton.setText(backButton.getTitle());
  }

  private void onBackButtonClicked(@Observes BackButtonClicked event) {
    backContainer.setVisible(items.size() > 3);
  }

  private void onSpaceSelection(@Observes SelectedSpace spaceSelection) {
    backContainer.setVisible(false);
    Space selectedSpace = spaceSelection.getResource();
    if (selectedSpace.isHome()) {
      init();
      refresh();
    }
  }

  private void onSpaceLoaded(@Observes LoadedSpace event) {
    init();
    Space space = event.getResource();
    Log.debug("BreadCrumbWidget - onSpaceLoaded - {0} (id={1})", space.getLabel(), space.getId());
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

  protected void init() {
    items.clear();
    items.add(new BreadCrumbSpaceItem(HomeSpaceProvider.getHomeSpace()));
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

  protected void addItem(BreadCrumbItem newItem) {
    ListIterator<BreadCrumbItem> itemListIterator = items.listIterator(3);
    boolean positionFound = false;
    while (itemListIterator.hasNext()) {
      BreadCrumbItem item = itemListIterator.next();
      if (positionFound || item.getTargetPageName().equals(newItem.getTargetPageName())) {
        positionFound = true;
        itemListIterator.remove();
      }
    }
    items.add(newItem);
    refresh();
  }

  private void refresh() {
    content.clear();
    if (items.size() > 2) {
      backContainer.setVisible(true);
      BreadCrumbItem item = items.get(items.size() - 2);
      backButton.initHref(item.getTargetPageName(), item.getTransitionParameters());
    }
    Iterator<BreadCrumbItem> itemIt = items.iterator();
    while (itemIt.hasNext()) {
      final BreadCrumbItem item = itemIt.next();
      item.setEnabled(itemIt.hasNext());
      final BreadCrumbTransitionAnchor anchor = breadCrumbItemAnchorProvider.get();
      item.addResourceContentChangeHandler(new ContentChangeHandler() {
        @Override
        public void onContentChange(final ContentChangeEvent event) {
          anchor.setModel(item);
        }
      });
      anchor.setModel(item);
      content.add(anchor);
    }
  }
}

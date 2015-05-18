package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.ApplicationInstanceSelection;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.enterprise.event.Event;

/**
 * @author miguel
 */
public class BreadCrumbAppItem extends BreadCrumbItem {

  private final ApplicationInstance instance;

  public BreadCrumbAppItem(final ApplicationInstance instance) {
    this.instance = instance;
  }

  @Override
  protected String getTargetPageName() {
    return StringUtil.capitalize(instance.getComponentName()) + "HomePage";
  }

  @Override
  public String getLabel() {
    return instance.getLabel();
  }

  @Override
  public String getStyleClass() {
    return instance.getComponentName() + "-breadcrump";
  }

  @Override
  protected Multimap<String, String> getTransitionParameters() {
    return ImmutableMultimap.of("instanceId", instance.getId());
  }

  @Override
  protected void fireEvent() {
    EventsProvider eventsProvider = BeanManager.getInstanceOf(EventsProvider.class);
    Event<ApplicationInstanceSelection> event =
        eventsProvider.getApplicationInstanceSelectionEvent();
    event.fire(new ApplicationInstanceSelection(instance));
  }

}

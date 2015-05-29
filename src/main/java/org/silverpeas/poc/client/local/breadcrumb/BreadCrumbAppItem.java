package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.SelectedApplicationInstance;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.enterprise.event.Event;

/**
 * @author miguel
 */
public class BreadCrumbAppItem extends BreadCrumbItem<BreadCrumbAppItem> {

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
  protected void fireItemEvent() {
    EventsProvider eventsProvider = BeanManager.getInstanceOf(EventsProvider.class);
    Event<SelectedApplicationInstance> event =
        eventsProvider.getApplicationInstanceSelectionEvent();
    event.fire(new SelectedApplicationInstance(instance));
  }

  @Override
  protected void resolveIfDefined() {
    resolve();
  }
}

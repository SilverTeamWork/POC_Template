package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.navigation.TransitionProvider;
import org.silverpeas.poc.api.util.Deferred;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;

import javax.inject.Inject;

/**
 * An item in a breadcrumb. The item represents a node in the navigation level of the user in the
 * current page. It can be a space, a Silverpeas application or a resource of the application.
 * @author miguel
 */
public abstract class BreadCrumbItem {

  private boolean enabled = true;

  public abstract String getLabel();

  public abstract String getStyleClass();

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Goes the page referred by this breadcrumb item.
   */
  protected final void go() {
    fireEvent();
    getNavigation().goTo(getTargetPage(), getTransitionParameters());
  }

  protected abstract Class<? extends SilverpeasPageComposite> getTargetPage();

  protected abstract Multimap<String, String> getTransitionParameters();

  protected abstract void fireEvent();

  protected Navigation getNavigation() {
    return BeanManager.getInstanceOf(Navigation.class);
  }
}

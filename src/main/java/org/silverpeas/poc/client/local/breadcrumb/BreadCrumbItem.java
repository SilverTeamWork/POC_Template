package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;

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

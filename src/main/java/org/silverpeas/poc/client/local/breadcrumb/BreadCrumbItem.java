package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;

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

  protected abstract String getTargetPageName();

  protected abstract Multimap<String, String> getTransitionParameters();

  protected abstract void fireEvent();
}

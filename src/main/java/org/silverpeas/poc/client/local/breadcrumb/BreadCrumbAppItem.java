package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.TransitionTo;

/**
 * @author miguel
 */
public class BreadCrumbAppItem extends BreadCrumbItem {

  @Override
  protected <P extends IsWidget> TransitionTo<P> getTargetPage() {
    return null;
  }

  @Override
  public String getLabel() {
    return "Not implemented (see BreadCrumbSpaceItem for example)";
  }

  @Override
  public String getStyleClass() {
    return "app-breadcrump";
  }

  @Override
  protected void applyParameters(final Multimap<String, String> parameters) {

  }
}

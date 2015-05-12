package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;

/**
 * @author miguel
 */
public class BreadCrumbAppItem extends BreadCrumbItem {

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

package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
public class BreadCrumbSpaceItem extends BreadCrumbItem {

  private final Space space;

  public BreadCrumbSpaceItem(final Space space) {
    this.space = space;
  }

  @Override
  public String getLabel() {
    return space.getLabel();
  }

  @Override
  public String getStyleClass() {
    return "space-breadcrump";
  }

  @Override
  protected void applyParameters(final Multimap<String, String> parameters) {
    parameters.put("spaceId", space.getId());
  }
}

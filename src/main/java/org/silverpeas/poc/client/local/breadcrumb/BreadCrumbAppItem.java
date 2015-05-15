package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;

/**
 * @author miguel
 */
public class BreadCrumbAppItem extends BreadCrumbItem {

  @Override
  protected Class<? extends SilverpeasPageComposite> getTargetPage() {
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
  protected Multimap<String, String> getTransitionParameters() {
    return ImmutableMultimap.of();
  }

  @Override
  protected void fireEvent() {

  }

}

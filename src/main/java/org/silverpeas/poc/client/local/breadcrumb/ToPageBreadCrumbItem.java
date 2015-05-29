package org.silverpeas.poc.client.local.breadcrumb;

import org.silverpeas.poc.client.local.template.SilverpeasApplicationPageComposite;

/**
 * @author Yohann Chastagnier
 */
public class ToPageBreadCrumbItem extends BreadCrumbItem<ToPageBreadCrumbItem> {

  public static ToPageBreadCrumbItem to(SilverpeasApplicationPageComposite pageInstance) {
    ToPageBreadCrumbItem item = new ToPageBreadCrumbItem().withToPageName(pageInstance.getClass());
    item.withLabel(pageInstance.getHeaderTitlePanel().getElement().getInnerText());
    return item;
  }

  public static ToPageBreadCrumbItem to(String toPageName) {
    return new ToPageBreadCrumbItem().withToPageName(toPageName);
  }

  @Override
  public String getStyleClass() {
    return "page-breadcrump";
  }

  @Override
  protected void fireItemEvent() {
  }
}

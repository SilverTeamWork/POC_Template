package org.silverpeas.poc.client.local.breadcrumb;

/**
 * @author miguel
 */
public class BreadCrumbItem<T> {

  private String label;
  private T page;

  public BreadCrumbItem(String label, T page) {
    this.label = label;
    this.page = page;
  }

  public String getLabel() {
    return label;
  }

  public T getPage() {
    return page;
  }

}

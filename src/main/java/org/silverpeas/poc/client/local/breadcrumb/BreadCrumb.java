package org.silverpeas.poc.client.local.breadcrumb;

import org.silverpeas.poc.api.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The breadcrumb model used by the dedicated widget.
 * @author miguel
 */
public class BreadCrumb {

  private List<BreadCrumbItem> items = new ArrayList<>();

  /**
   * Clears the existing list of breadcrumb items and sets the first one.
   * @param item the first breadcrumb item of the list.
   */
  public void setItem(BreadCrumbItem item) {
    items.clear();
    addItem(item);
  }

  /**
   * Adds a breadcrumb item into the existing list.
   * @param item the breadcrumb item to add.
   */
  public void addItem(BreadCrumbItem item) {
    items.add(item);
  }

  /**
   * Gets the items of the breadcrumb.
   * @return the items of the breadcrumbs (never null).
   */
  List<BreadCrumbItem> getItems() {
    return items;
  }

  /**
   * This method handles the navigation to one of parts of the breadcrumb.
   * @param item the aimed part of the breadcrumb.
   */
  void backTo(BreadCrumbItem item) {

    // Cleaning the breadcrumb
    boolean stop = false;
    for (int i = (items.size() - 1); i >= 0 && !stop; i--) {
      stop = (items.get(i) == item);
      items.remove(i);
    }
    Log.dev(this.getClass().getSimpleName() + " - backTo() - breadcrumb cleaned");

    // Going to the requested page
    item.go();
  }
}

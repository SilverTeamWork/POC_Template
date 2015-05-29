package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.silverpeas.poc.api.ioc.BeanManager;

/**
 * @author Yohann Chastagnier
 */
public class BreadCrumbWidget implements IsWidget {

  @Override
  public Widget asWidget() {
    return getBreadCrumbComposite();
  }

  public static void push(BreadCrumbItem newItem) {
    getBreadCrumbComposite().push(newItem);
  }

  private static BreadCrumbComposite getBreadCrumbComposite() {
    return BeanManager.getInstanceOf(BreadCrumbComposite.class);
  }
}

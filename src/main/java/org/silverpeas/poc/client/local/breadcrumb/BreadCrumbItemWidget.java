package org.silverpeas.poc.client.local.breadcrumb;

import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.event.widget.ContentChangeEvent;
import org.silverpeas.poc.api.event.widget.ContentChangeHandler;

/**
 * This widget represents an element part from the breadcrumb.
 * @author Yohann Chastagnier
 */
public class BreadCrumbItemWidget extends BreadCrumbTransitionAnchor
    implements HasModel<BreadCrumbItem> {

  @Override
  public void setModel(final BreadCrumbItem breadCrumbItem) {
    super.setModel(breadCrumbItem);
    breadCrumbItem.addResourceContentChangeHandler(new ContentChangeHandler() {
      @Override
      public void onContentChange(final ContentChangeEvent event) {
        BreadCrumbItemWidget.super.setModel(breadCrumbItem);
      }
    });
  }
}

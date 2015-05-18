package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.event.dom.client.ClickHandler;
import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;

/**
 * @author miguel
 */
public class BreadCrumbTransitionAnchor extends SilverpeasTransitionAnchor
    implements HasModel<BreadCrumbItem>, ClickHandler {

  private BreadCrumbItem item;

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public BreadCrumbItem getModel() {
    return this.item;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final BreadCrumbItem model) {
    this.item = model;
    setText(this.item.getLabel());
    setStyleName(this.item.getStyleClass());
    setEnabled(this.item.isEnabled());
    initHref(this.item.getTargetPageName(), this.item.getTransitionParameters());
  }

  @Override
  protected void onClickEvent() {
    this.item.fireEvent();
  }
}

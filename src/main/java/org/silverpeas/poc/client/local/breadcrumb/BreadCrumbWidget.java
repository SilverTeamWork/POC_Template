package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author miguel
 */
@Templated
public class BreadCrumbWidget extends Composite implements HasModel<BreadCrumb> {

  @Inject
  @DataField
  private Anchor spaceHome;

  private BreadCrumb breadCrumb;

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public BreadCrumb getModel() {
    return null;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final BreadCrumb model) {
    this.breadCrumb = model;
    if (!breadCrumb.getItems().isEmpty()) {
      spaceHome.setHref("#");
      spaceHome.setText(breadCrumb.getItems().get(0).getLabel());
    }
  }
}

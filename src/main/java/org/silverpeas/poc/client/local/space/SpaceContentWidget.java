package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author miguel
 */
@Templated("../SilverpeasMainPage.html#spaceContents-item")
public class SpaceContentWidget extends Composite implements HasModel<SpaceContent> {
  private SpaceContent content;

  @Inject
  @DataField("spaceContents-item-label")
  private Anchor spaceLabel;

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public SpaceContent getModel() {
    return this.content;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final SpaceContent model) {
    this.content = model;
    this.spaceLabel.setText(this.content.getLabel());
    this.spaceLabel.setHref("#");
  }
}

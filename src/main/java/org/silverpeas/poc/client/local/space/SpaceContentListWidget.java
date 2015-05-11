package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * A widget displaying a list of a space content (subspaces, applications).
 * @author miguel
 */
@Templated("SpaceWidget.html#space-item-content")
public class SpaceContentListWidget extends Composite implements HasModel<Space> {

  private Space space;

  @Inject
  @DataField("space-content")
  @UnOrderedList
  private ListWidget<SpaceContent, SpaceContentWidget> spaceContent;

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public Space getModel() {
    return space;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final Space model) {
    this.space = model;
    this.spaceContent.setItems(this.space.getContent());
  }
}

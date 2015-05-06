package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;
import java.util.List;

/**
 * A widget displaying a list of a space content (subspaces, applications).
 * @author miguel
 */
@Templated
public class SpaceContentListWidget extends ListWidget<SpaceContent, SpaceContentWidget> {

  /**
   * Returns the class object for the item widget type <W> to look up new
   * instances of the widget using the client-side bean manager.
   * @return the item widget type.
   */
  @Override
  protected Class<SpaceContentWidget> getItemWidgetType() {
    return SpaceContentWidget.class;
  }
}

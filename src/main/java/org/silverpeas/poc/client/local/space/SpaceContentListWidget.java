package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.Composite;
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
public class SpaceContentListWidget extends Composite{

  @Inject
  @DataField("space-content")
  @UnOrderedList
  private ListWidget<SpaceContent, SpaceContentWidget> spaceContent;

  public void setItems(List<SpaceContent> spaceContents) {
    this.spaceContent.setItems(spaceContents);
  }
}

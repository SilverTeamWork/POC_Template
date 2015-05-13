package org.silverpeas.poc.client.local.space;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author miguel
 */
@Templated
public class SpaceWidget extends Composite implements HasModel<Space> {

  private Space space;

  @Inject
  @DataField("space-item-label")
  private Anchor spaceLabel;

  @Inject
  @DataField("space-item-content")
  private SpaceContentListWidget spaceContents;

  @Inject
  private Event<SpaceSelection> spaceSelected;

  @Inject
  private SpaceLoader spaceLoader;

  @Inject
  private HistoryTokenFactory urlFactory;

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
    this.spaceLabel.setText(this.space.getLabel());
    if (this.space.isHome()) {
      ListMultimap<String, String> state = ImmutableListMultimap.of();
      this.spaceLabel.setHref("#" + urlFactory.createHistoryToken("HomePage", state).toString());
    } else {
      final ListMultimap<String, String> state =
          ImmutableListMultimap.of("spaceId", this.space.getId());
      this.spaceLabel.setHref(
          "#" + urlFactory.createHistoryToken("SpaceHomePage", state).toString());
    }
    this.spaceLabel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        spaceSelected.fire(new SpaceSelection(getModel()));
      }
    });
    loadSpaceContent(getModel());
  }

  public void unselect(@Observes SpaceSelection selection) {
    if (selection.getSelectedSpace() == this.space) {
      getModel().setAsCurrent();
      addStyleName("selected");
    } else {
      getModel().unsetAsCurrent();
      removeStyleName("selected");
    }
  }

  private void loadSpaceContent(final Space currentSpace) {
    spaceLoader.loadSpaceContent(currentSpace);
  }

  protected void onSpaceContentLoaded(@Observes SpaceContentLoaded spaceContentLoaded) {
    if (spaceContentLoaded.getSpaceWithLoadedContent() == getModel()) {
      spaceContents.setModel(spaceContentLoaded.getSpaceWithLoadedContent());
    }
  }
}

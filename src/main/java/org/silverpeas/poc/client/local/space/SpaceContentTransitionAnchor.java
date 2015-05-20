package org.silverpeas.poc.client.local.space;

import com.google.common.collect.ImmutableMultimap;
import com.google.gwt.event.dom.client.ClickHandler;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.event.SelectedApplicationInstance;
import org.silverpeas.poc.client.local.space.event.SelectedSpace;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.inject.Inject;

/**
 * Handles the transition to a {@link SpaceContent}. A space content can be:
 * <ul>
 * <li>a {@link Space} instance</li>
 * <li>or a {@link ApplicationInstance} instance</li>
 * </ul>
 * @author Yohann Chastagnier
 */
public class SpaceContentTransitionAnchor extends SilverpeasTransitionAnchor
    implements ClickHandler {

  @Inject
  private EventsProvider eventsProvider;

  private SpaceContent spaceContent;

  @Override
  public void onClickEvent() {
    if (spaceContent.isApplication()) {
      eventsProvider.getApplicationInstanceSelectionEvent()
          .fire(new SelectedApplicationInstance((ApplicationInstance) this.spaceContent));
    } else {
      eventsProvider.getSpaceSelectionEvent().fire(new SelectedSpace((Space) this.spaceContent));
    }
  }

  public void setSpaceContent(SpaceContent spaceContent) {
    this.spaceContent = spaceContent;
    setText(spaceContent.getLabel());
    final String toPrefixPageName;
    final String spaceContentIdKey;
    if (spaceContent.isApplication()) {
      toPrefixPageName = ((ApplicationInstance) spaceContent).getComponentName();
      spaceContentIdKey = "instanceId";
    } else {
      toPrefixPageName = spaceContent.getClass().getSimpleName();
      spaceContentIdKey = "spaceId";
    }
    initHref(StringUtil.capitalize(toPrefixPageName + "HomePage"),
        ImmutableMultimap.of(spaceContentIdKey, spaceContent.getId()));
  }
}

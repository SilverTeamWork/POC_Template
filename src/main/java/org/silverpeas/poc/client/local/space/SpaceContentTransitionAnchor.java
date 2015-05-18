package org.silverpeas.poc.client.local.space;

import com.google.common.collect.ImmutableMultimap;
import com.google.gwt.event.dom.client.ClickHandler;
import org.silverpeas.poc.api.navigation.SilverpeasTransitionAnchor;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.ApplicationInstanceSelection;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;
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
    if (spaceContent instanceof ApplicationInstance) {
      eventsProvider.getApplicationInstanceSelectionEvent()
          .fire(new ApplicationInstanceSelection((ApplicationInstance) this.spaceContent));
    } else {
      eventsProvider.getSpaceSelectionEvent().fire(new SpaceSelection((Space) this.spaceContent));
    }
  }

  public void setSpaceContent(SpaceContent spaceContent) {
    this.spaceContent = spaceContent;
    setText(spaceContent.getLabel());
    final String toPrefixPageName;
    final String spaceContentIdKey;
    if (spaceContent instanceof ApplicationInstance) {
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

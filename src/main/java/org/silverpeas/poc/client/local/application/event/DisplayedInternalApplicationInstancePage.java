package org.silverpeas.poc.client.local.application.event;

import org.silverpeas.poc.api.event.PageEvent;
import org.silverpeas.poc.client.local.template.SilverpeasApplicationPageComposite;

/**
 * @author Yohann Chastagnier
 */
public class DisplayedInternalApplicationInstancePage
    extends PageEvent<SilverpeasApplicationPageComposite> {

  public DisplayedInternalApplicationInstancePage(
      final SilverpeasApplicationPageComposite resource) {
    super(resource);
  }
}

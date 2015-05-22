package org.silverpeas.poc.client.local.space;

import org.jboss.errai.common.client.api.annotations.LocalEvent;
import org.silverpeas.poc.api.event.ResourceEvent;

/**
 * @author Yohann Chastagnier
 */
@LocalEvent
public class SpaceContentMenuToggled extends ResourceEvent<Boolean> {
  public SpaceContentMenuToggled(final Boolean toggled) {
    super(toggled);
  }
}

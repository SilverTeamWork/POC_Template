package org.silverpeas.poc.api.navigation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 * All anchors instantiated from Silverpeas should inherit from this class.<br/>
 * By this way, it will be easier to change common behaviours around anchors.
 * @author Yohann Chastagnier
 */
public abstract class SilverpeasAnchor extends Anchor implements ClickHandler {

  public SilverpeasAnchor() {
    addClickHandler(this);
    setHref("#");
  }

  @Override
  public final void onClick(final ClickEvent event) {
    if (isEnabled()) {
      click();
    }

    event.stopPropagation();
    event.preventDefault();
  }

  /**
   * Programmatically click on the anchor.
   */
  public abstract void click();
}

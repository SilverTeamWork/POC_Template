package org.silverpeas.poc.client.local.widget.menu;

import org.silverpeas.poc.api.Callback;

/**
 * This menu action handles a click callback to invoke.
 * @author Yohann Chastagnier
 */
public class ClickAction extends MenuAction<ClickAction> {

  private Callback clickCallback;

  /**
   * @see MenuAction#MenuAction(boolean)
   */
  public ClickAction(final boolean mustBeVerified) {
    super(mustBeVerified);
  }

  /**
   * Sets the callback to invoke when the menu item is clicked.
   * @param clickCallback the callback to invoke.
   * @return the current instance.
   */
  public ClickAction clickCallback(final Callback clickCallback) {
    this.clickCallback = clickCallback;
    return this;
  }

  Callback getClickCallback() {
    return clickCallback;
  }
}

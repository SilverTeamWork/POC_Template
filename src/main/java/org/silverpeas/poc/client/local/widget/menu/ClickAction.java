package org.silverpeas.poc.client.local.widget.menu;

import org.silverpeas.poc.api.Callback;

/**
 * @author Yohann Chastagnier
 */
public class ClickAction extends MenuAction {

  private final Callback clickCallback;

  public static final ClickAction DUMMY = new ClickAction(TYPE.UNKNOWN, new Callback() {
    @Override
    public void invoke(final Object... parameters) {

    }
  });

  public ClickAction(final TYPE type, final Callback clickCallback) {
    super(type);
    this.clickCallback = clickCallback;
  }

  public Callback getClickCallback() {
    return clickCallback;
  }

  @Override
  protected ClickAction getDummy() {
    return DUMMY;
  }
}

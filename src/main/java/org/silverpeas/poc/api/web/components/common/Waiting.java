package org.silverpeas.poc.api.web.components.common;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

import static org.silverpeas.poc.client.local.util.BundleProvider.msg;

/**
 * @author Yohann Chastagnier
 */
public class Waiting {

  public static void show() {
    waitingComponent.waiting.center();
  }

  public static void hide() {
    waitingComponent.waiting.hide();
  }

  /*
  THE WIDGET
   */

  final static Waiting waitingComponent = new Waiting();

  final PopupPanel waiting = new PopupPanel(false, true);

  private Waiting() {
    waiting.add(new Label(msg().waiting()));
    waiting.setGlassEnabled(true);
  }
}

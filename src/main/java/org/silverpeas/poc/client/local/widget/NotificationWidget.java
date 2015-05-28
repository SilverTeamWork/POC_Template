package org.silverpeas.poc.client.local.widget;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author miguel
 */
public class NotificationWidget extends Composite {

  public static native void notifSuccess(String message) /*-{
    $wnd.noty({
      text: message,
      type: 'success',
      layout: 'topCenter',
      timeout: 5000,
      dismissQueue: true,
      animation: {
        open: {height: 'toggle'},
        close: {height: 'toggle'},
        easing: 'swing',
        speed: 500
      }
    });
  }-*/;

  public static native void notifError(String message) /*-{
    $wnd.noty({
      text: message,
      type: 'error',
      layout: 'topCenter',
      timeout: 5000,
      dismissQueue: true,
      animation: {
        open: {height: 'toggle'},
        close: {height: 'toggle'},
        easing: 'swing',
        speed: 500
      }
    });
  }-*/;

  public static native void notifInfo(String message) /*-{
    $wnd.noty({
      text: message,
      type: 'information',
      layout: 'topCenter',
      timeout: 5000,
      dismissQueue: true,
      animation: {
        open: {height: 'toggle'},
        close: {height: 'toggle'},
        easing: 'swing',
        speed: 500
      }
    });
  }-*/;
}

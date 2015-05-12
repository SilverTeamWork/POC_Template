package org.silverpeas.poc.api.util;

import com.google.gwt.core.client.Scheduler;
import org.silverpeas.poc.api.Callback;

/**
 * @author Yohann Chastagnier
 */
public class Deferred {
  /**
   * Invokes the given callback after the browser event loop returns.
   * @param callback the callback to invoke.
   */
  public static void add(final Callback callback) {
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        callback.invoke();
      }
    });
  }
}

package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link ResourceDeleteEvent} events.
 * @param <T> the value about to be changed
 * @author Yohann Chastagnier
 */
public interface ResourceDeleteHandler<T> extends EventHandler {

  /**
   * Called when {@link ResourceDeleteEvent} is fired.
   * @param event the {@link ResourceDeleteEvent} that was fired
   */
  void onResourceDelete(ResourceDeleteEvent<T> event);
}

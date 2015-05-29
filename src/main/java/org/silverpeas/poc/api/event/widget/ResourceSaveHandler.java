package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link ResourceSaveEvent} events.
 * @param <T> the value about to be changed
 * @author Yohann Chastagnier
 */
public interface ResourceSaveHandler<T> extends EventHandler {

  /**
   * Called when {@link ResourceSaveEvent} is fired.
   * @param event the {@link ResourceSaveEvent} that was fired
   */
  void onResourceSave(ResourceSaveEvent<T> event);
}

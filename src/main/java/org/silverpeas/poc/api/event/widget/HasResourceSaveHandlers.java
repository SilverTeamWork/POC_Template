package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link ResourceSaveEvent} events.
 * @param <T> the value about to be changed
 * @author Yohann Chastagnier
 */
public interface HasResourceSaveHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link ResourceSaveEvent} handler.
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addResourceSaveHandler(ResourceSaveHandler<T> handler);
}

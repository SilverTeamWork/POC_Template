package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link ResourceDeleteEvent} events.
 * @param <T> the value about to be changed
 * @author Yohann Chastagnier
 */
public interface HasResourceDeleteHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link ResourceDeleteEvent} handler.
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addDeleteResourceHandler(ResourceDeleteHandler<T> handler);
}

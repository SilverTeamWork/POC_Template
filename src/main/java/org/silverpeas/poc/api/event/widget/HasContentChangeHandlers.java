package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link ContentchangeEvent} events.
 * @author Yohann Chastagnier
 */
public interface HasContentChangeHandlers extends HasHandlers {
  /**
   * Adds a {@link ContentChangeEvent} handler.
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addResourceContentChangeHandler(ContentChangeHandler handler);
}

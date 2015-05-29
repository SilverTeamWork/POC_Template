package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler interface for {@link ContentChangeEvent} events.
 * @author Yohann Chastagnier
 */
public interface ContentChangeHandler extends EventHandler {

  /**
   * Called when {@link ContentChangeEvent} is fired.
   * @param event the {@link ContentChangeEvent} that was fired
   */
  void onContentChange(ContentChangeEvent event);
}

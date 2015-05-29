package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a resource delete event.
 * @author Yohann Chastagnier
 */
public class ContentChangeEvent extends GwtEvent<ContentChangeHandler> {

  /**
   * Handler type.
   */
  private static Type<ContentChangeHandler> TYPE;

  /**
   * Fires a value change event on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   * @param source the source of the handlers
   */
  public static void fire(HasContentChangeHandlers source) {
    if (TYPE != null) {
      ContentChangeEvent event = new ContentChangeEvent();
      source.fireEvent(event);
    }
  }

  /**
   * Fires value change event if the old value is not equal to the new value.
   * Use this call rather than making the decision to short circuit yourself for
   * safe handling of null.
   * @param source the source of the handlers
   */
  public static void fireIfNotEqual(HasContentChangeHandlers source) {
    ContentChangeEvent event = new ContentChangeEvent();
    source.fireEvent(event);
  }

  /**
   * Gets the type associated with this event.
   * @return returns the handler type
   */
  public static Type<ContentChangeHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<>();
    }
    return TYPE;
  }

  /**
   * Deletes a value change event.
   */
  protected ContentChangeEvent() {
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public final Type<ContentChangeHandler> getAssociatedType() {
    return (Type) TYPE;
  }

  @Override
  protected void dispatch(ContentChangeHandler handler) {
    handler.onContentChange(this);
  }
}


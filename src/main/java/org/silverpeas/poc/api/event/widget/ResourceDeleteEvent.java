package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a resource delete event.
 * @param <T> the resource about to be deleted.
 * @author Yohann Chastagnier
 */
public class ResourceDeleteEvent<T> extends GwtEvent<ResourceDeleteHandler<T>> {

  /**
   * Handler type.
   */
  private static Type<ResourceDeleteHandler<?>> TYPE;

  /**
   * Fires a value change event on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   * @param <T> the old value type
   * @param source the source of the handlers
   * @param value the value
   */
  public static <T> void fire(HasResourceDeleteHandlers<T> source, T value) {
    if (TYPE != null) {
      ResourceDeleteEvent<T> event = new ResourceDeleteEvent<>(value);
      source.fireEvent(event);
    }
  }

  /**
   * Fires value change event if the old value is not equal to the new value.
   * Use this call rather than making the decision to short circuit yourself for
   * safe handling of null.
   * @param <T> the old value type
   * @param source the source of the handlers
   * @param oldValue the oldValue, may be null
   * @param newValue the newValue, may be null
   */
  public static <T> void fireIfNotEqual(HasResourceDeleteHandlers<T> source, T oldValue,
      T newValue) {
    if (shouldFire(source, oldValue, newValue)) {
      ResourceDeleteEvent<T> event = new ResourceDeleteEvent<>(newValue);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * @return returns the handler type
   */
  public static Type<ResourceDeleteHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<>();
    }
    return TYPE;
  }

  /**
   * Convenience method to allow subtypes to know when they should fire a value
   * change event in a null-safe manner.
   * @param <T> value type
   * @param source the source
   * @param oldValue the old value
   * @param newValue the new value
   * @return whether the event should be fired
   */
  protected static <T> boolean shouldFire(HasResourceDeleteHandlers<T> source, T oldValue,
      T newValue) {
    return TYPE != null && oldValue != newValue && (oldValue == null || !oldValue.equals(newValue));
  }

  private final T value;

  /**
   * Deletes a value change event.
   * @param value the value
   */
  protected ResourceDeleteEvent(T value) {
    this.value = value;
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public final Type<ResourceDeleteHandler<T>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the value.
   * @return the value
   */
  public T getValue() {
    return value;
  }

  @Override
  public String toDebugString() {
    return super.toDebugString() + getValue();
  }

  @Override
  protected void dispatch(ResourceDeleteHandler<T> handler) {
    handler.onResourceDelete(this);
  }
}


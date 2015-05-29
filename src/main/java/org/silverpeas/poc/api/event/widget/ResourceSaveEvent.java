package org.silverpeas.poc.api.event.widget;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a resource save event.
 * @param <R> the resource about to be saved.
 * @author Yohann Chastagnier
 */
public class ResourceSaveEvent<R> extends GwtEvent<ResourceSaveHandler<R>> {

  /**
   * Handler type.
   */
  private static Type<ResourceSaveHandler<?>> TYPE;

  private boolean persistSave = false;

  /**
   * Fires a value change event on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   * @param <T> the old value type
   * @param source the source of the handlers
   * @param value the resource
   * @param isPersist true if the save concerns a persist operation, false otherwise.
   */
  public static <T> void fire(HasResourceSaveHandlers<T> source, T value, boolean isPersist) {
    if (TYPE != null) {
      ResourceSaveEvent<T> event = new ResourceSaveEvent<>(value, isPersist);
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
   * @param isPersist true if the save concerns a persist operation, false otherwise.
   */
  public static <T> void fireIfNotEqual(HasResourceSaveHandlers<T> source, T oldValue, T newValue,
      boolean isPersist) {
    if (shouldFire(source, oldValue, newValue)) {
      ResourceSaveEvent<T> event = new ResourceSaveEvent<>(newValue, isPersist);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * @return returns the handler type
   */
  public static Type<ResourceSaveHandler<?>> getType() {
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
  protected static <T> boolean shouldFire(HasResourceSaveHandlers<T> source, T oldValue,
      T newValue) {
    return TYPE != null && oldValue != newValue && (oldValue == null || !oldValue.equals(newValue));
  }

  private final R value;

  /**
   * Creates a resource save event.
   * @param value the resource
   * @param persistSave true if the save concerns a persist operation, false otherwise.
   */
  protected ResourceSaveEvent(R value, boolean persistSave) {
    this.value = value;
    this.persistSave = persistSave;
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public final Type<ResourceSaveHandler<R>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the value.
   * @return the value
   */
  public R getValue() {
    return value;
  }

  /**
   * Indicates if save has performed a persist operation.
   * @return true if persist operation, false otherwise.
   */
  public boolean isPersistSave() {
    return persistSave;
  }

  @Override
  public String toDebugString() {
    return super.toDebugString() + getValue();
  }

  @Override
  protected void dispatch(ResourceSaveHandler<R> handler) {
    handler.onResourceSave(this);
  }
}


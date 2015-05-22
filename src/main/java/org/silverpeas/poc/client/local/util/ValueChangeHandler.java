package org.silverpeas.poc.client.local.util;

/**
 * A handler to handle a change of a value.
 * @author miguel
 */
public interface ValueChangeHandler<T> {

  void onChange(T previousValue, T newValue);
}

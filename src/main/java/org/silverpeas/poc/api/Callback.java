package org.silverpeas.poc.api;

/**
 * @author Yohann Chastagnier
 */
public interface Callback {
  void invoke(final Object... parameters);
}

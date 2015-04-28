package org.silverpeas.poc.api.util.comparator;

import java.util.Comparator;

/**
 * @author yohann.chastagnier
 * @param <C>
 */
public abstract class AbstractComparator<C> implements Comparator<C> {

  /**
   * Centralizes bean comparison mechanism
   * @return
   */
  protected static <T> boolean areInstancesComparable(final T comp1, final T comp2) {
    return (comp1 != null && comp2 != null);
  }

  /**
   * Centralizes bean comparison mechanism
   * @return
   */
  protected static <T> int compareInstance(final T comp1, final T comp2) {
    int result = 0;
    if (comp1 == null && comp2 != null) {
      result = -1;
    } else if (comp1 != null && comp2 == null) {
      result = 1;
    }
    return result;
  }

  /**
   * Centralizes bean comparison mechanism
   * @return
   */
  protected static <T> int compare(final Comparable<? super T> comp1, final T comp2) {
    int result = 0;
    if (comp1 == null && comp2 != null) {
      result = -1;
    } else if (comp1 != null && comp2 == null) {
      result = 1;
    } else if (comp1 != null && comp2 != null) {
      result = comp1.compareTo(comp2);
    }
    return result;
  }
}

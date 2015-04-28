package org.silverpeas.poc.api.util.comparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.silverpeas.poc.api.util.StringUtil.isNotDefined;

/**
 * @param <C>
 * @author Yohann Chastagnier
 */
public abstract class AbstractComplexComparator<C> extends AbstractComparator<C> {

  /**
   * Value list to compare
   * @param object
   * @return
   */
  protected abstract ValueBuffer getValuesToCompare(final C object);

  /**
   * @see java.util.Comparator#compare(Object, Object)
   */
  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public int compare(final C o1, final C o2) {

    // Value lists to compare
    final ValueBuffer baseValues = getValuesToCompare(o1);
    final ValueBuffer comparedValues = getValuesToCompare(o2);

    // Tests
    int result = 0;
    final Iterator<Object> it1 = baseValues.getValues().iterator();
    final Iterator<Object> it2 = comparedValues.getValues().iterator();
    final Iterator<Integer> itSens = baseValues.getSens().iterator();
    Object curO1;
    Object curO2;
    Integer sens;
    while (it1.hasNext()) {
      curO1 = it1.next();
      curO2 = it2.next();
      sens = itSens.next();

      // Instance
      result = compareInstance(curO1, curO2);
      if (result != 0) {
        return result * sens.intValue();
      }

      // Value
      if (areInstancesComparable(curO1, curO2)) {
        result = compare((Comparable) curO1, curO2);
        if (result != 0) {
          return result * sens.intValue();
        }
      }
    }

    // The two objects are identical
    return result;
  }

  /**
   * A value
   * @author yohann.chastagnier
   */
  public class ValueBuffer {

    /**
     * Sens
     */
    final private List<Integer> sens = new ArrayList<Integer>();

    /**
     * Valeur
     */
    final private List<Object> values = new ArrayList<Object>();

    /**
     * Default constructor
     */
    public ValueBuffer() {
      // NTD
    }

    /**
     * Adding a value
     * @param object
     * @param isAscending
     * @return
     */
    public ValueBuffer append(final Object object, final boolean isAscending) {
      values.add(object);
      if (isAscending) {
        sens.add(1);
      } else {
        sens.add(-1);
      }
      return this;
    }

    /**
     * Adding a value
     * @param object
     * @return
     */
    public ValueBuffer append(final Object object) {
      return append(object, true);
    }

    /**
     * @return the sens
     */
    public List<Integer> getSens() {
      return sens;
    }

    /**
     * @return the values
     */
    public List<Object> getValues() {
      return values;
    }
  }

  /**
   * Class that permits to put null or empty String value always at the bottom of a list.
   */
  public class StringWrapper implements Comparable<StringWrapper> {
    final String string;
    final boolean sort;
    final boolean emptyAtEnd;

    public StringWrapper(final String string, final boolean sort, final boolean emptyAtEnd) {
      this.string = isNotDefined(string) ? null : string;
      this.sort = sort;
      this.emptyAtEnd = emptyAtEnd;
    }

    @Override
    public int compareTo(final StringWrapper o) {

      // Instance
      int result = compareInstance(string, o.string);
      if (result != 0) {
        if (emptyAtEnd) {
          return result * (sort ? -1 : 1);
        } else {
          return result * (sort ? 1 : -1);
        }
      }

      // Value
      if (areInstancesComparable(string, o.string)) {
        return string.compareTo(o.string);
      }

      // Identical
      return 0;
    }
  }
}

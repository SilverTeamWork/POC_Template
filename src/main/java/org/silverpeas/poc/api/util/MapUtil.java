/*
 * Copyright (C) 2000 - 2013 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/docs/core/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.silverpeas.poc.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yohann Chastagnier
 */
public class MapUtil {

  /**
   * Centralizes the map adding that containing list collections
   */
  public static <K, V> List<V> putAddAllList(Map<K, List<V>> map, final K key,
      final Collection<V> values) {
    List<V> result = null;

    if (values != null && !values.isEmpty()) {
      for (V value : values) {
        result = putAddList(map, key, value);
      }
    } else {
      result = putAddList(map, key, null);
    }

    // map result (never null)
    return result;
  }

  /**
   * Centralizes the map adding that containing list collections
   */
  public static <K, V> List<V> putAddList(Map<K, List<V>> map, final K key, final V value) {

    if (map == null) {
      map = new LinkedHashMap<>();
    }

    // Old value
    List<V> result = map.get(key);
    if (result == null) {
      try {
        result = new ArrayList<>();
      } catch (final Exception myException) {
        throw new IllegalArgumentException(myException);
      }
      map.put(key, result);
    }

    // adding the value
    result.add(value);

    // map result
    return result;
  }

  /**
   * Centralizes the map adding that containing list collections
   */
  public static <K, V> Set<V> putAddAllSet(Map<K, Set<V>> map, final K key,
      final Collection<V> values) {
    Set<V> result = null;

    if (values != null && !values.isEmpty()) {
      for (V value : values) {
        result = putAddSet(map, key, value);
      }
    } else {
      result = putAddSet(map, key, null);
    }

    // map result (never null)
    return result;
  }

  /**
   * Centralizes the map adding that containing set collections
   */
  public static <K, V> Set<V> putAddSet(Map<K, Set<V>> map, final K key, final V value) {

    if (map == null) {
      map = new LinkedHashMap<>();
    }

    // Old value
    Set<V> result = map.get(key);
    if (result == null) {
      try {
        result = new HashSet<>();
      } catch (final Exception myException) {
        throw new IllegalArgumentException(myException);
      }
      map.put(key, result);
    }

    // adding the value
    result.add(value);

    // map result
    return result;
  }
}

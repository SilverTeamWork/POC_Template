package org.silverpeas.poc.api.util;

import org.silverpeas.poc.api.ioc.BeanManager;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple application cache.
 * @author Yohann Chastagnier
 */
@ApplicationScoped
public class ApplicationCache {

  private static ApplicationCache get() {
    return BeanManager.getInstanceOf(ApplicationCache.class);
  }

  private Map<String, Object> cache = new HashMap<>();

  public static void clear() {
    ApplicationCache me = get();
    Log.dev("RequestCache will be cleaned with content: " + me.cache);
    me.cache.clear();
  }

  public static void put(String key, Object value) {
    ApplicationCache me = get();
    me.cache.put(key, value);
  }

  public static Object get(String key) {
    ApplicationCache me = get();
    return me.cache.get(key);
  }

  @SuppressWarnings({"unchecked", "UnusedParameters"})
  public static <T> T get(String key, Class<T> clazz) {
    try {
      return (T) get(key);
    } catch (Exception e) {
      return null;
    }
  }
}

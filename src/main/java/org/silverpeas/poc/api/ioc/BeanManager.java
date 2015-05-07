package org.silverpeas.poc.api.ioc;

import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ioc.client.container.IOC;
import org.jboss.errai.ioc.client.container.IOCBeanDef;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Singleton;

/**
 * This class permits to get from IOC an instance of bean annotated with one of the following
 * annotations:
 * <ul>
 * <li>{@link EntryPoint}, {@link Singleton} or {@link ApplicationScoped}: the bean will be
 * available during all the application life time.</li>
 * <li>{@link Dependent}: a new instance is created each time a bean instance is requested</li>
 * </ul>
 * WARNING : only beans that are explicitly scoped will be made available to the bean manager for
 * lookup. So while it is not necessary for regular injection, you must annotate your dependent
 * scoped beans with @Dependent if you wish to dynamically lookup these beans at runtime.
 * @author Yohann Chastagnier
 */
public class BeanManager {

  /**
   * Gets an instance of {@link T} according to the different bean scope definitions. <br/>
   * Please consult documentation of class {@link BeanManager} to get the list of the managed
   * scopes.
   * @param clazz the aimed bean class.
   * @param <T> the type of the aimed bean.
   * @return an instance of {@link T} according to the different bean scope definitions.
   * @see BeanManager
   */
  public static <T> T getInstanceOf(Class<T> clazz) {
    T beanInstance = null;
    IOCBeanDef<T> beanDef = IOC.getBeanManager().lookupBean(clazz);
    if (beanDef != null) {
      beanInstance = beanDef.getInstance();
    }
    return beanInstance;
  }
}

package org.silverpeas.poc.api.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.PageTransitionProvider;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionAnchorProvider;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.api.ioc.BeanManager;

import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * @author Yohann Chastagnier
 */

/**
 * Provides {@link TransitionTo} and {@link TransitionAnchor} instances by an other way than the
 * {@link Inject} method.
 */
public class TransitionProvider {

  /**
   * Gets a {@link TransitionAnchor} instance from the given page class.
   * @param pageClass the page class to associate with the requested transition.
   * @param <P> the type of the page
   * @return a {@link TransitionAnchor} instance from the given page class.
   */
  @SuppressWarnings("unchecked")
  public static <P extends IsWidget> TransitionAnchor<P> getTransitionAnchor(Class<P> pageClass) {
    return getTransitionAnchorProvider().provide(new Class[]{pageClass}, new Annotation[0]);
  }

  /**
   * Gets a {@link TransitionTo} instance from the given page class.
   * @param pageClass the page class to associate with the requested transition.
   * @param <P> the type of the page
   * @return a {@link TransitionTo} instance from the given page class.
   */
  @SuppressWarnings("unchecked")
  public static <P extends IsWidget> TransitionTo<P> getTransitionTo(Class<P> pageClass) {
    return getPageTransitionProvider().provide(new Class[]{pageClass}, new Annotation[0]);
  }

  private static TransitionAnchorProvider getTransitionAnchorProvider() {
    return BeanManager.getInstanceOf(TransitionAnchorProvider.class);
  }

  private static PageTransitionProvider getPageTransitionProvider() {
    return BeanManager.getInstanceOf(PageTransitionProvider.class);
  }
}

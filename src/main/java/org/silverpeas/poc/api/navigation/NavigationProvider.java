package org.silverpeas.poc.api.navigation;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.nav.client.local.PageTransitionProvider;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionAnchorProvider;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.nav.client.local.spi.NavigationGraph;
import org.silverpeas.poc.api.ioc.BeanManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.annotation.Annotation;

/**
 * Provides {@link TransitionTo} and {@link TransitionAnchor} instances by an other way than the
 * {@link Inject} method.<br/>
 * Provides also methods to perform easily navigation requests.
 * @author Yohann Chastagnier
 */
@ApplicationScoped
public class NavigationProvider {

  public static NavigationProvider get() {
    return BeanManager.getInstanceOf(NavigationProvider.class);
  }

  @Inject
  private Navigation navigation;

  @Inject
  private HistoryTokenFactory htFactory;

  @Inject
  private NavigationGraph navGraph;

  @Inject
  private TransitionAnchorProvider transitionAnchorProvider;

  @Inject
  private PageTransitionProvider pageTransitionProvider;

  /**
   * Looks up the PageNode instance that provides content for the given widget type, sets the state
   * on that page, then
   * makes the widget visible in the content area.
   * @param toPage The content type of the page node to look up and display. Normally, this is a
   * Widget subclass that has
   * been annotated with {@code @Page}.
   */
  public <W extends IsWidget> void goTo(Class<W> toPage) {
    goTo(toPage, ImmutableListMultimap.<String, String>of());
  }

  /**
   * Looks up the PageNode instance that provides content for the given widget type, sets the state
   * on that page, then
   * makes the widget visible in the content area.
   * @param toPage The content type of the page node to look up and display. Normally, this is a
   * Widget subclass that has been annotated with {@code @Page}.
   * @param state The state information to set on the page node before showing it. Normally the map
   * keys correspond with the names of fields annotated with {@code @PageState} in the widget
   * class, but this is not required.
   */
  public <W extends IsWidget> void goTo(Class<W> toPage, Multimap<String, String> state) {
    navigation.goTo(toPage, state);
  }

  /**
   * Same as {@link #goTo(Class, com.google.common.collect.Multimap)} but then with the page name.
   * @param toPage the name of the page node to lookup and display.
   */
  public void goTo(String toPage) {
    goTo(toPage, ImmutableListMultimap.<String, String>of());
  }

  /**
   * Same as {@link #goTo(Class, com.google.common.collect.Multimap)} but then with the page name.
   * @param toPage the name of the page node to lookup and display.
   * @param state The state information to set on the page node before showing it. Normally the map
   * keys correspond with the names of fields annotated with {@code @PageState} in the widget
   * class, but this is not required.
   */
  public void goTo(String toPage, Multimap<String, String> state) {
    goTo(navGraph.getPage(toPage).contentType(), state);
  }

  /**
   * This can be used to create a HistoryToken when navigating by page name.
   * @param pageName The name of the page. Never null.
   * @param state The map of {@link PageState} keys and values. Never null.
   * @return A HistoryToken with the parsed URL matching information.
   */
  public HistoryToken createHistoryToken(String pageName, Multimap<String, String> state) {
    return htFactory.createHistoryToken(pageName, state);
  }

  /**
   * Gets a {@link TransitionAnchor} instance from the given page class.
   * @param pageClass the page class to associate with the requested transition.
   * @param <P> the type of the page
   * @return a {@link TransitionAnchor} instance from the given page class.
   */
  @SuppressWarnings("unchecked")
  public <P extends IsWidget> TransitionAnchor<P> getTransitionAnchor(Class<P> pageClass) {
    return transitionAnchorProvider.provide(new Class[]{pageClass}, new Annotation[0]);
  }

  /**
   * Gets a {@link TransitionTo} instance from the given page class.
   * @param pageClass the page class to associate with the requested transition.
   * @param <P> the type of the page
   * @return a {@link TransitionTo} instance from the given page class.
   */
  @SuppressWarnings("unchecked")
  public <P extends IsWidget> TransitionTo<P> getTransitionTo(Class<P> pageClass) {
    return pageTransitionProvider.provide(new Class[]{pageClass}, new Annotation[0]);
  }
}

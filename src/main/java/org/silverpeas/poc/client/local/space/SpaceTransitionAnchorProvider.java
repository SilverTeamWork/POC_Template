package org.silverpeas.poc.client.local.space;

/**
 * @author Yohann Chastagnier
 */

import org.jboss.errai.ioc.client.api.ContextualTypeProvider;
import org.jboss.errai.ioc.client.api.IOCProvider;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;

/**
 * Provides new instances of the {@link SpaceTransitionAnchor} widget class, which
 * allows them to be injected.
 * @author Yohann Chastagnier
 */
@IOCProvider
@Singleton
public class SpaceTransitionAnchorProvider
    implements ContextualTypeProvider<SpaceTransitionAnchor> {

  @Inject
  Navigation navigation;

  @Inject
  HistoryTokenFactory htFactory;

  @Inject
  Event<SpaceSelection> selectedSpace;

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public SpaceTransitionAnchor provide(Class<?>[] typeargs, Annotation[] qualifiers) {
    return new SpaceTransitionAnchor(navigation, htFactory, selectedSpace);
  }

}

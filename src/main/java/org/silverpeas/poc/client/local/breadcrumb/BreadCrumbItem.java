package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.user.client.ui.IsWidget;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.navigation.TransitionProvider;
import org.silverpeas.poc.api.util.Deferred;
import org.silverpeas.poc.api.util.Log;

/**
 * An item in a breadcrumb. The item represents a node in the navigation level of the user in the
 * current page. It can be a space, a Silverpeas application or a resource of the application.
 * @author miguel
 */
public abstract class BreadCrumbItem {

  protected abstract <P extends IsWidget> TransitionTo<P> getTargetPage();

  public abstract String getLabel();

  public abstract String getStyleClass();

  private TransitionTo getTransitionToPage() {
    if (getTargetPage() != null) {
      return getTargetPage();
    }
    return TransitionProvider.getTransitionTo(getNavigation().getCurrentPage().contentType());
  }

  public final void go() {
    Multimap<String, String> parameters = ArrayListMultimap.create();
    applyParameters(parameters);
    final TransitionTo transition = getTransitionToPage();
    Log.dev(BreadCrumbItem.class.getSimpleName() + " - go() - target: " + transition.toPageType() +
        " with parameters: " + parameters);
    transition.go(parameters);
  }

  protected abstract void applyParameters(Multimap<String, String> parameters);

  protected Navigation getNavigation() {
    return BeanManager.getInstanceOf(Navigation.class);
  }
}

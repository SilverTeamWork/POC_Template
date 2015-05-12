package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.navigation.TransitionProvider;
import org.silverpeas.poc.api.util.Deferred;
import org.silverpeas.poc.api.util.Log;

/**
 * @author miguel
 */
public abstract class BreadCrumbItem {

  private TransitionTo targetPage;

  void setCurrentPage() {
    this.targetPage =
        TransitionProvider.getTransitionTo(getNavigation().getCurrentPage().contentType());
  }

  public abstract String getLabel();

  public abstract String getStyleClass();

  private TransitionTo getTransitionToPage() {
    if (targetPage != null) {
      return targetPage;
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

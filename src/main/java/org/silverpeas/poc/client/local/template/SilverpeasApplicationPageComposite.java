package org.silverpeas.poc.client.local.template;

import io.reinert.gdeferred.DoneCallback;
import io.reinert.gdeferred.FailCallback;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.Dispatcher;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.ApplicationInstanceLoader;

/**
 * Application instance pages must extends this class in order to get a common behaviour.
 * @author Yohann Chastagnier
 */
public abstract class SilverpeasApplicationPageComposite extends SilverpeasPageComposite {

  private SilverpeasApplicationLayout applicationLayout;

  @PageState
  private String instanceId;

  private ApplicationInstance applicationInstance;

  protected abstract String getComponentType();

  @Override
  public final void onPageShowing() {
    ApplicationInstanceLoader.get().loadComponent(instanceId)
        .then(new DoneCallback<ApplicationInstance>() {

          @Override
          public void onDone(final ApplicationInstance loadedInstance) {
            applicationInstance = loadedInstance;
            Log.debug("Application instance '{0}' loaded for display (type={1}, id={2})",
                applicationInstance.getLabel(), applicationInstance.getComponentName(),
                applicationInstance.getId());
            if (getComponentType().equals(applicationInstance.getComponentName())) {
              onApplicationInstanceLoaded(applicationInstance);
            } else {
              loadComponentError();
            }
          }
        }).fail(new FailCallback<Void>() {
      @Override
      public void onFail(final Void result) {
        loadComponentError();
      }
    });
  }

  private void loadComponentError() {
    Message.notifies("The component does not exist...").error(new Callback() {
      @Override
      public void invoke(final Object... parameters) {
        Dispatcher.home();
      }
    });
  }

  public abstract void onApplicationInstanceLoaded(ApplicationInstance instance);

  @Override
  protected SilverpeasComposite getCompositeParent() {
    if (applicationLayout == null) {
      applicationLayout = BeanManager.getInstanceOf(SilverpeasApplicationLayout.class);
    }
    return applicationLayout;
  }

  public ApplicationInstance getApplicationInstance() {
    return applicationInstance;
  }
}

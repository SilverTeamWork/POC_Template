package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Panel;
import io.reinert.gdeferred.DoneCallback;
import io.reinert.gdeferred.FailCallback;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.Dispatcher;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.application.ApplicationInstanceLoader;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.util.EventsProvider;
import org.silverpeas.poc.client.local.widget.menu.MenuWidget;

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
    beforeOnPageShowing();
    applicationInstance = applicationLayout.getCurrentApplicationInstance();
    if (applicationInstance == null || !applicationInstance.getId().equals(instanceId)) {
      // The necessary data about the application instance are not already loaded
      ApplicationInstanceLoader.get().loadComponent(instanceId)
          .then(new DoneCallback<ApplicationInstance>() {

            @Override
            public void onDone(final ApplicationInstance loadedInstance) {
              applicationInstance = loadedInstance;
              Log.debug("Application instance '{0}' loaded for display (type={1}, id={2})",
                  applicationInstance.getLabel(), applicationInstance.getComponentName(),
                  applicationInstance.getId());
              if (getComponentType().equals(applicationInstance.getComponentName())) {
                EventsProvider.get().getApplicationInstanceLoadedEvent()
                    .fire(new LoadedApplicationInstance(applicationInstance));
                loadComponentSuccess();
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
    } else {
      // The layout can already provide all the necessary data, so application instance is reloaded
      EventsProvider.get().getApplicationInstanceLoadedEvent()
          .fire(new LoadedApplicationInstance(applicationInstance));
      loadComponentSuccess();
    }
  }

  protected void beforeOnPageShowing() {

  }

  private void loadComponentError() {
    Message.notifies("The component does not exist...").error(new Callback() {
      @Override
      public void invoke(final Object... parameters) {
        Dispatcher.home();
      }
    });
  }

  private void loadComponentSuccess() {
    onApplicationInstanceLoaded(applicationInstance);

    // Common menu actions
    if (getClass().getSimpleName().endsWith("HomePage")) {
      getMenuWidget().addClickAction().withLabel(I18n.format(CommonTranslations.SUBSCRIBE_LABEL));
      getMenuWidget().addClickAction()
          .withLabel(I18n.format(CommonTranslations.APPLICATION_RESPONSIBLE_LABEL));
    }
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

  public void setPageTitle(String title) {
    applicationLayout.setPageTitle(title);
  }

  public void setPageDescription(String description) {
    applicationLayout.setPageDescription(description);
  }

  public Panel getHeaderTitlePanel() {
    return applicationLayout.getHeaderTitlePanel();
  }

  public Panel getHeaderDescriptionPanel() {
    return applicationLayout.getHeaderDescriptionPanel();
  }

  public Panel getRightPanel() {
    return applicationLayout.getRightPanel();
  }

  public Panel getFooterPanel() {
    return applicationLayout.getFooterPanel();
  }

  public MenuWidget getMenuWidget() {
    return applicationLayout.getMenuWidget();
  }
}
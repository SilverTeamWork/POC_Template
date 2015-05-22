package org.silverpeas.poc.client.local;

import com.google.gwt.user.client.ui.Composite;
import io.reinert.gdeferred.Deferred;
import io.reinert.gdeferred.Promise;
import io.reinert.gdeferred.impl.DeferredObject;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.DefaultNavigationErrorHandler;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.UniquePageRole;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * This class handle the start of the application.<br/>
 * It is the entry point of the application and as such all required resources are then initialized.
 * @author Yohann Chastagnier
 */
@EntryPoint
public class Starter {

  @Inject
  private Navigation navigation;

  private Deferred<Void, Void, Void> silverpeasStarted = new DeferredObject<>();

  private static Starter get() {
    return BeanManager.getInstanceOf(Starter.class);
  }

  public static void triggerSilverpeasIsStarted() {
    get().silverpeasStarted.resolve(null);
  }

  public static Promise<Void, Void, Void> whenSilverpeasIsStarted() {
    return get().silverpeasStarted;
  }

  @AfterInitialization
  private void setup() {
    JsonHttp.setUnauthorizedCallback(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        CurrentUser.markAsDisconnected();
        Message.notifies(I18n.format(Messages.DISCONNECTION_TOKEN_EXPIRED)).error(new Callback() {
          @Override
          public void invoke(final Object... parameters) {
            Dispatcher.home();
          }
        });
      }
    });
    setErrorPageHandler();
    Log.dev("Silverpeas configured successfully !");
  }

  /**
   * Sets the handler that takes in charge page not found exceptions.<br/>
   */
  public void setErrorPageHandler() {
    navigation.setErrorHandler(new DefaultNavigationErrorHandler(navigation) {
      @Override
      public void handleInvalidPageNameError(final Exception exception, final String pageName) {
        Log.dev(exception.getMessage());
        Log.dev("PageName: " + pageName);
        super.handleInvalidPageNameError(exception, pageName);
      }

      @Override
      public void handleError(final Exception exception,
          final Class<? extends UniquePageRole> pageRole) {
        Log.dev(exception.getMessage());
        Log.dev("PageRole: " + pageRole.getSimpleName());
        super.handleError(exception, pageRole);
      }

      @Override
      public void handleInvalidURLError(final Exception exception, final String urlPath) {
        Log.dev(exception.getMessage());
        Log.dev("UrlPath: " + urlPath);
        super.handleInvalidURLError(exception, urlPath);
      }
    });

  }
}

package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.DefaultNavigationErrorHandler;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.nav.client.local.UniquePageRole;
import org.jboss.errai.ui.nav.client.local.pushstate.PushStateUtil;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.util.Messages;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * This class handle the start of the application.<br/>
 * When application is starting it is searching for the default page. So, this application starts
 * from this class because of the definition of annotation {@link Page} with role {@link
 * DefaultPage}.
 * It is annotated by {@link ApplicationScoped} in order to get one instance for all the life of
 * application.
 * @author Yohann Chastagnier
 */
@EntryPoint
@Page(role = DefaultPage.class)
@Bundle(BundleProvider.JSON_TRANSLATIONS)
public class Starter extends Composite {

  @Inject
  private Navigation navigation;

  @Inject
  private TransitionTo<HomePage> homepage;

  @AfterInitialization
  private void setup() {
    PushStateUtil.enablePushState(true);
    Navigation.setAppContext("");
    JsonHttp.setUnauthorizedCallback(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        CurrentUser.markAsDisconnected();
        Message.notifies(I18n.format(Messages.DISCONNECTION_TOKEN_EXPIRED)).info(new Callback() {
          @Override
          public void invoke() {
            Starter.home();
          }
        });
      }
    });
    setErrorPageHandler();
    GWT.log("Silverpeas configured successfully !");
    dispatch();
  }

  /**
   * This method handles the navigation to the first page to display:
   * <ul>
   * <li>The login page if no user exists into the local storage</li>
   * <li>The homepage of Silverpeas application if a user is already authenticated and registered
   * inti local storage</li>
   * </ul>
   * It is called just after the instantiation of the class.
   */
  private void dispatch() {
    if (CurrentUser.exists()) {
      GWT.log(I18n.format(Messages.CURRENT_CONNECTED_USER_LOG, CurrentUser.get().getName()));
      homepage.go();
    } else {
      UrlManager.goToPlainPage("login.html");
    }
  }

  /**
   * Handle the navigation to the home page.
   * @see #dispatch()
   */
  public static void home() {
    BeanManager.getInstanceOf(Starter.class).dispatch();
  }

  /**
   * Sets the handler that takes in charge page not found exceptions.<br/>
   */
  public void setErrorPageHandler() {
    navigation.setErrorHandler(new DefaultNavigationErrorHandler(navigation) {
      @Override
      public void handleInvalidPageNameError(final Exception exception, final String pageName) {
        GWT.log(exception.getMessage());
        GWT.log("PageName: " + pageName);
        super.handleInvalidPageNameError(exception, pageName);
      }

      @Override
      public void handleError(final Exception exception,
          final Class<? extends UniquePageRole> pageRole) {
        GWT.log(exception.getMessage());
        GWT.log("PageRole: " + pageRole.getSimpleName());
        super.handleError(exception, pageRole);
      }

      @Override
      public void handleInvalidURLError(final Exception exception, final String urlPath) {
        GWT.log(exception.getMessage());
        GWT.log("UrlPath: " + urlPath);
        super.handleInvalidURLError(exception, urlPath);
      }
    });

  }
}

package org.silverpeas.poc.client.local;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.client.local.space.SpaceTransitionAnchor;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.util.BundleProvider;
import org.silverpeas.poc.client.local.util.HomeSpaceProvider;
import org.silverpeas.poc.client.local.util.Messages;

import javax.inject.Inject;

/**
 * The default page when no one is found by Errai.
 * It plays the role of dispatcher to page according to the navigation error: the user is not
 * connected, then he's redirected to the login page, otherwise he's redirected to the home page.
 * @author Yohann Chastagnier
 */
@Page(role = DefaultPage.class)
@Templated
@Bundle(BundleProvider.JSON_TRANSLATIONS)
public class Dispatcher extends Composite {

  @Inject
  @DataField
  private SpaceTransitionAnchor homepage;

  @AfterInitialization
  private void setup() {
    homepage.setSpace(HomeSpaceProvider.getHomeSpace());
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
  @PageShowing
  private void dispatch() {
    if (CurrentUser.exists()) {
      Log.dev(I18n.format(Messages.CURRENT_CONNECTED_USER_LOG, CurrentUser.get().getName()));
      homepage.click();
    } else {
      UrlManager.goToPlainPage("login.html");
    }
  }

  /**
   * Handle the navigation to the home page.
   * @see #dispatch()
   */
  public static void home() {
    BeanManager.getInstanceOf(Dispatcher.class).dispatch();
  }
}

package org.silverpeas.poc.client.local;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.silverpeas.poc.client.local.user.CurrentUser;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page(role = DefaultPage.class)
public class Starter extends Composite {

  @Inject
  private TransitionTo<SilverpeasMainPage> homepage;

  @PostConstruct
  public void redirect() {
    if (CurrentUser.exists() ) {
      homepage.go();
    } else {
      CurrentUser.markAsDisconnected();
      Window.Location.assign("/login.html");
    }
  }
}

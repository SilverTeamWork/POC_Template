package org.silverpeas.poc.client.local.test.yocha.ui.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.silverpeas.poc.client.local.SilverpeasMainTemplate;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
public class SilverpeasMainLayout extends Composite {

  public static final String DOCK_LAYOUT =
      "/org/silverpeas/poc/client/local/test/yocha/ui/common/dock.html";

  @Inject
  protected SilverpeasMainTemplate mainPage;

  @PageShowing
  public void pageShowing() {
    GWT.log("pageShowing(): " + getContentContainer());
  }

  @PageShown
  private void pageShown() {
    GWT.log("pageShown(): " + getContentContainer());
    RootPanel.get().add(this);
  }

  /**
   * Gets the element that handle the content of the main page.
   * @return the content element container.
   */
  public HTMLPanel getContentContainer() {
    return null;//mainPage.getContentContainer();
  }
}

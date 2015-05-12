package org.silverpeas.poc.client.local.test.yocha.ui.common;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.template.SilverpeasMainTemplate;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
public class SpaceTemplate extends Composite {

  public static final String DOCK_LAYOUT =
      "/org/silverpeas/poc/client/local/test/yocha/ui/common/dock.html";

  @Inject
  protected SilverpeasMainTemplate mainPage;

  @PageShowing
  public void pageShowing() {
    Log.dev("pageShowing(): " + getContentContainer());
  }

  @PageShown
  private void pageShown() {
    Log.dev("pageShown(): " + getContentContainer());
    RootPanel.get().add(this);
  }

  /**
   * Gets the element that handle the content of the main page.
   * @return the content element container.
   */
  public HTMLPanel getContentContainer() {
    return mainPage.getContentContainer();
  }
}

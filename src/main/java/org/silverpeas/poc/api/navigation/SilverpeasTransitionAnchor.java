package org.silverpeas.poc.api.navigation;

import com.google.common.collect.Multimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * This Class is annotated by {@link Dependent} in order to be managed by {@link BeanManager}
 * provider.
 * @author Yohann Chastagnier
 */
@Dependent
public class SilverpeasTransitionAnchor extends Anchor implements ClickHandler {

  private String toPage;
  private HistoryToken pageToken;

  public SilverpeasTransitionAnchor() {
    addClickHandler(this);
  }

  @Override
  public final void onClick(final ClickEvent event) {
    if (isEnabled()) {
      click();
    }

    event.stopPropagation();
    event.preventDefault();
  }

  protected void onClickEvent() {

  }

  /**
   * Programmatically click on the anchor.
   */
  public final void click() {
    if (this.pageToken != null) {
      onClickEvent();
      NavigationProvider.get().goTo(pageToken.getPageName(), pageToken.getState());
    } else {
      Message.notifies(toPage + " does not (yet) exist...").error();
    }
  }

  /**
   * Initialize the anchor's href attribute.
   * @param toPage The page name this transition goes to. Not null.
   * @param state The page state.  Cannot be null (but can be an empty multimap)
   */
  public void initHref(String toPage, Multimap<String, String> state) {
    this.toPage = toPage;
    try {
      this.pageToken = NavigationProvider.get().createHistoryToken(toPage, state);
    } catch (Exception e) {
      this.pageToken = null;
    }
    String href = "#";
    if (this.pageToken != null) {
      href += this.pageToken.toString();
    } else {
      Log.dev(toPage + " does not (yet) exist");
    }
    setHref(href);
  }

  /**
   * Initialize the anchor's href attribute.
   * @param toPage The page type this transition goes to. Not null.
   * @param state The page state.  Cannot be null (but can be an empty multimap)
   */
  public void initHref(Class toPage, Multimap<String, String> state) {
    initHref(toPage.getSimpleName(), state);
  }
}

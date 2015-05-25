package org.silverpeas.poc.api.event;

import com.google.common.collect.Multimap;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;

/**
 * @author Yohann Chastagnier
 */
public abstract class PageEvent<P extends SilverpeasPageComposite> {

  private P page;
  private final HistoryToken historyToken;

  public PageEvent(final P page) {
    this.page = page;
    historyToken = NavigationProvider.get().createCurrentHistoryToken();
  }

  public P getPage() {
    return page;
  }

  public String getPageName() {
    return historyToken.getPageName();
  }

  public Multimap<String, String> getPageState() {
    return historyToken.getState();
  }
}

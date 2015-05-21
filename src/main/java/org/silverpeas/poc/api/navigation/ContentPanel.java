package org.silverpeas.poc.api.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ui.nav.client.local.NavigatingContainer;
import org.jboss.errai.ui.nav.client.local.NavigationPanel;

/**
 * @author Yohann Chastagnier
 */
public class ContentPanel implements NavigatingContainer {
  private final SimplePanel panel = new NavigationPanel();

  public ContentPanel() {
  }

  public Widget getWidget() {
    return this.panel.getWidget();
  }

  public void setWidget(IsWidget w) {
    this.panel.setWidget(w);
  }

  public void setWidget(Widget w) {
    this.panel.setWidget(w);
  }

  public void clear() {
    this.panel.clear();
  }

  public Widget asWidget() {
    return this.panel.asWidget();
  }
}

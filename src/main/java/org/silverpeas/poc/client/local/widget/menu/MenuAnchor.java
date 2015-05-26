package org.silverpeas.poc.client.local.widget.menu;

import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.api.navigation.SilverpeasAnchor;
import org.silverpeas.poc.api.util.Log;

/**
 * @author Yohann Chastagnier
 */
public class MenuAnchor extends SilverpeasAnchor implements HasModel<MenuAction> {

  private MenuAction menuAction;

  @Override
  public void click() {
    if (menuAction instanceof ToPageAction) {
      ToPageAction toPageAction = (ToPageAction) menuAction;
      NavigationProvider.get().goTo(toPageAction.getPageName(), toPageAction.getParameters());
    } else if (menuAction instanceof ClickAction) {
      ClickAction clickAction = (ClickAction) menuAction;
      clickAction.getClickCallback().invoke();
    } else {
      throw new IllegalArgumentException(
          "MenuAnchor - click - type '" + menuAction.getClass().getName() + "' is noy yet handled");
    }
  }

  @Override
  public MenuAction getModel() {
    return menuAction;
  }

  @Override
  public void setModel(final MenuAction menuAction) {
    this.menuAction = menuAction;
    this.menuAction.setMenuAnchor(this);
    setText(this.menuAction.getLabel());
  }
}

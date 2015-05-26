package org.silverpeas.poc.client.local.widget.menu;

import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.api.navigation.SilverpeasAnchor;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.api.web.components.common.Message;

import static org.silverpeas.poc.api.util.StringUtil.format;

/**
 * This class handles menu anchors.
 * @author Yohann Chastagnier
 */
public class MenuAnchor extends SilverpeasAnchor implements HasModel<MenuAction> {

  private MenuAction menuAction;

  @Override
  public void click() {
    if (menuAction instanceof ToPageAction) {
      ToPageAction toPageAction = (ToPageAction) menuAction;
      if (StringUtil.isDefined(toPageAction.getPageName())) {
        NavigationProvider.get().goTo(toPageAction.getPageName(), toPageAction.getParameters());
      } else {
        Message.notifies(format("Action '{0}' is not yet handled", toPageAction.getLabel()))
            .warning();
      }
    } else if (menuAction instanceof ClickAction) {
      ClickAction clickAction = (ClickAction) menuAction;
      if (clickAction.getClickCallback() != null) {
        clickAction.getClickCallback().invoke();
      } else {
        Message.notifies(format("Action '{0}' is not yet handled", clickAction.getLabel()))
            .warning();
      }
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

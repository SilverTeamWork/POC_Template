package org.silverpeas.poc.client.local.widget.menu;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.client.local.util.AccessController;
import org.silverpeas.poc.client.local.util.Contribution;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * This widget handles a menu.
 * @author Yohann Chastagnier
 */
@Templated
public class MenuWidget extends Composite {

  @Inject
  @DataField("menu-container")
  @UnOrderedList
  private ListWidget<MenuAction, MenuItemWidget> menuActionList;

  @AfterInitialization
  private void init() {
    menuActionList.setItems(new ArrayList<MenuAction>());
  }

  /**
   * Adds an action which the click event is handled by the caller.
   * @param clickCallback the callback to call on click event.
   * @return the initialized {@link ClickAction} instance. {@link ClickAction#DUMMY} instance if
   * user has no privilege for the action.
   */
  public ClickAction addClickAction(final Callback clickCallback) {
    return addItem(new ClickAction[]{new ClickAction(MenuAction.TYPE.FREE, clickCallback)},
        ClickAction.DUMMY);
  }

  /**
   * Adds an action which the click action brings the user to another page.
   * @param toPageName the page name aimed.
   * @return the initialized {@link ToPageAction} instance. {@link ToPageAction#DUMMY} instance if
   * user has no privilege for the action.
   */
  public ToPageAction addToPageAction(final String toPageName) {
    return addItem(new ToPageAction[]{new ToPageAction(MenuAction.TYPE.FREE, toPageName)},
        ToPageAction.DUMMY);
  }

  /**
   * Adds an action to a contribution which the click event is handled by the caller.<br/>
   * The privileged on the resource are verified. If the user has not the privilege to perform the
   * action, the menu is not added.
   * @param contribution the contribution aimed by the action.
   * @param type the primary type of the action.
   * @param clickCallback the callback to call on click event.
   * @return the initialized {@link ClickAction} instance. {@link ClickAction#DUMMY} instance if
   * user has no privilege for the action.
   */
  public ClickAction addClickAction(Contribution contribution, final MenuAction.TYPE type,
      final Callback clickCallback) {
    final ClickAction[] clickAction = new ClickAction[1];
    if (type == MenuAction.TYPE.FREE) {
      clickAction[0] = new ClickAction(type, clickCallback);
    } else {
      AccessController.on(contribution).doOnlyIfPrivileged(new AccessController.Action() {
        @Override
        public void run() {
          clickAction[0] = new ClickAction(type, clickCallback);
        }
      });
    }
    return addItem(clickAction, ClickAction.DUMMY);
  }

  /**
   * Adds an action to a contribution which the click action brings the user to another page.<br/>
   * The privileged on the resource are verified. If the user has not the privilege to perform the
   * action, the menu is not added.
   * @param contribution the contribution aimed by the action.
   * @param type the primary type of the action.
   * @param toPageName the page name aimed.
   * @return the initialized {@link ToPageAction} instance. {@link ToPageAction#DUMMY} instance if
   * user has no privilege for the action.
   */
  public ToPageAction addToPageAction(Contribution contribution, final MenuAction.TYPE type,
      final String toPageName) {
    final ToPageAction[] toPageAction = new ToPageAction[1];
    if (type == MenuAction.TYPE.FREE) {
      toPageAction[0] = new ToPageAction(type, toPageName);
    } else {
      AccessController.on(contribution).doOnlyIfPrivileged(new AccessController.Action() {
        @Override
        public void run() {
          toPageAction[0] = new ToPageAction(type, toPageName);
        }
      });
    }
    return addItem(toPageAction, ToPageAction.DUMMY);
  }

  private <MENU_ACTION extends MenuAction> MENU_ACTION addItem(MENU_ACTION[] menuActions,
      MENU_ACTION dummy) {
    if (menuActions.length > 0 && menuActions[0] != null) {
      MENU_ACTION menuAction = menuActions[0];
      menuActionList.getValue().add(menuAction);
      setVisible(true);
      return menuAction;
    }
    return dummy;
  }

  /**
   * Clears all action items from the menu.
   */
  public void clear() {
    menuActionList.getValue().clear();
    setVisible(false);
  }
}

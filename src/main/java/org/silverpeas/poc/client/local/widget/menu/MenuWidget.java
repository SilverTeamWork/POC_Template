package org.silverpeas.poc.client.local.widget.menu;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.util.Contribution;
import org.silverpeas.poc.client.local.util.ContributionList;

import javax.inject.Inject;

/**
 * This widget handles a menu.<br/>
 * In order to get the control of the position of menu items, the principle of this mechanism is to
 * prepare all menu instances with minimal data by using {@link #addClickAction()}, {@link
 * #addPrivilegedClickAction()}, {@link #addToPageAction()} or  {@link
 * #addPrivilegedToPageAction()}.<br/>
 * An entry in the menu is added to each time one of the methods mentioned above is called.<br/>
 * Once the instance of a menu item is added, it may be subsequently completed during synchronous
 * or  asynchronous loading of information to take into account the specific resource data.<br/>
 * When a menu item must be displayed only when the user has the right privileges, {@link
 * #addPrivilegedClickAction()} or {@link #addPrivilegedToPageAction()} must be called first to add
 * the entry, and the menu item should be checked later against the privileges specified for a
 * resource by using {@link MenuAction#verify(Contribution)} or {@link
 * MenuAction#verify(ContributionList)} methods.<br/>
 * If there is only menu items that have to be verified according to privileges, the entire menu is
 * shown after that there is no more menu entry for which the verification has not been
 * performed.<br/>
 * When there is a menu item that does not require verification against the privileges, the entire
 * menu is displayed and menu entries that ask to be verify according to the privileges are
 * displayed once the verification is performed successfully.
 * @author Yohann Chastagnier
 */
@Templated
public class MenuWidget extends Composite {

  private MenuActionListWrapper menuActions;

  @Inject
  @DataField("menu-container")
  @UnOrderedList
  private ListWidget<MenuAction, MenuItemWidget> menuActionList;

  @AfterInitialization
  private void init() {
    menuActions = new MenuActionListWrapper(this);
    menuActionList.setItems(menuActions);
    clear();
  }

  /**
   * Adds an action which the click event is handled by the caller.
   * @return the initialized {@link ClickAction} instance.
   */
  public ClickAction addClickAction() {
    return addItem(new ClickAction(false));
  }

  /**
   * Adds an action which the click event is handled by the caller.<br/>
   * This action must be verified via {@link ClickAction#verify(ContributionList)} or {@link
   * ClickAction#verify(Contribution)} in order to get a visible menu container.
   * @return the initialized {@link ClickAction} instance.
   */
  public ClickAction addPrivilegedClickAction() {
    return addItem(new ClickAction(true));
  }

  /**
   * Adds an action which the click action brings the user to another page.
   * @return the initialized {@link ToPageAction} instance.
   */
  public ToPageAction addToPageAction() {
    return addItem(new ToPageAction(false));
  }

  /**
   * Adds an action which the click action brings the user to another page.<br/>
   * This action must be verified via {@link ClickAction#verify(ContributionList)} or {@link
   * ClickAction#verify(Contribution)} in order to get a visible menu container.
   * @return the initialized {@link ToPageAction} instance.
   */
  public ToPageAction addPrivilegedToPageAction() {
    return addItem(new ToPageAction(true));
  }

  private <MENU_ACTION extends MenuAction> MENU_ACTION addItem(MENU_ACTION menuAction) {
    menuAction.setMenuActions(menuActions);
    menuActions.add(menuAction);
    return menuAction;
  }

  /**
   * Clears all action items from the menu.
   */
  public void clear() {
    menuActions.clear();
  }
}

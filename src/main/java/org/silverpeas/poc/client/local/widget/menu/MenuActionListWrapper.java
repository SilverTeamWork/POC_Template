package org.silverpeas.poc.client.local.widget.menu;

import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.databinding.client.BindableListWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * This list wrapper specific to the {@link MenuWidget} permits to manage the content of the menu
 * container.<br/>
 * The visibility of the {@link MenuWidget} is handled here.
 * @author Yohann Chastagnier
 */
public class MenuActionListWrapper extends BindableListWrapper<MenuAction> {

  private final Widget menuWidget;
  private List<MenuAction> toVerify = new ArrayList<>();

  public MenuActionListWrapper(final Widget menuWidget) {
    super(new ArrayList<MenuAction>());
    this.menuWidget = menuWidget;
  }

  @Override
  public boolean add(final MenuAction menuAction) {
    if (menuAction.hasToBeVerified()) {
      toVerify.add(menuAction);
    } else {
      menuWidget.setVisible(true);
    }
    return super.add(menuAction);
  }

  /**
   * Marks that the given menu has been verified (successfully or not).
   * @param menuAction the verified menu action.
   */
  void verificationPerformedOn(MenuAction menuAction) {
    toVerify.remove(menuAction);

    if (menuAction.hasToBeRemoved()) {
      remove(menuAction);
    }

    if (toVerify.isEmpty()) {
      menuWidget.setVisible(!isEmpty());
    }
  }

  @Override
  public void clear() {
    menuWidget.setVisible(false);
    toVerify.clear();
    super.clear();
  }
}

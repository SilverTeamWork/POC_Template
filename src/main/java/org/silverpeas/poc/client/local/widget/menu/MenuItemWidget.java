package org.silverpeas.poc.client.local.widget.menu;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated("MenuWidget.html#menu-item")
public class MenuItemWidget extends Composite implements HasModel<MenuAction> {

  private MenuAction menuAction;

  @Inject
  @DataField("menu-item-action")
  private MenuAnchor actionAnchor;

  @Override
  public MenuAction getModel() {
    return menuAction;
  }

  @Override
  public void setModel(final MenuAction menuAction) {
    this.menuAction = menuAction;
    this.menuAction.setMenuItemWidget(this);
    this.actionAnchor.setModel(menuAction);
    setVisible(!menuAction.hasToBeVerified());
  }
}

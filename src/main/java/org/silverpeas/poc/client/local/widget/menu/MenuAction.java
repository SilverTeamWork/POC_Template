package org.silverpeas.poc.client.local.widget.menu;

import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.util.AccessController;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.util.Contribution;
import org.silverpeas.poc.client.local.util.ContributionList;

/**
 * This class contains the common behaviour of a menu action.<br/>
 * Each entry of {@link MenuWidget} must be linked with a {@link MenuAction} instance.
 * @author Yohann Chastagnier
 */
public abstract class MenuAction<MENU_ACTION_TYPE extends MenuAction> {

  private boolean mustBeVerified;
  private boolean mustBeRemoved = false;
  private MenuActionListWrapper menuActions;
  private MenuItemWidget menuItemWidget;
  private MenuAnchor menuAnchor;

  public enum TYPE {
    UNKNOWN(""),
    CREATION(CommonTranslations.ACTION_CREATION),
    MODIFICATION(CommonTranslations.ACTION_MODIFICATION),
    DELETION(CommonTranslations.ACTION_DELETION);

    private final String bundleKey;

    TYPE(final String bundleKey) {
      this.bundleKey = bundleKey;
    }
  }

  private TYPE type = TYPE.UNKNOWN;
  private String label = "";

  /**
   * Mandatory constructor.
   * @param mustBeVerified indicates if a verification must be done by {@link
   * #verify(Contribution)}
   * or {@link #verify(ContributionList)}.
   */
  public MenuAction(boolean mustBeVerified) {
    this.mustBeVerified = mustBeVerified;
  }

  private MenuAction() {
  }

  String getLabel() {
    return label;
  }

  /**
   * Sets the {@link MenuAction.TYPE} of the action. If the type is known, a default label is set.
   * @param type the type of the action.
   * @param labelAddon the default label can include a additional label represented bu this
   * parameter.
   * @return the current instance.
   */
  @SuppressWarnings("unchecked")
  public MENU_ACTION_TYPE ofType(TYPE type, String... labelAddon) {
    this.type = type;
    if (StringUtil.isDefined(type.bundleKey)) {
      withLabel(I18n.format(type.bundleKey, labelAddon));
    }
    return (MENU_ACTION_TYPE) this;
  }

  /**
   * Sets the label of the {@link MenuAction}. This method will drop default labels set by {@link
   * #ofType(TYPE, String...)} if it is called after.
   * @param label the label to set.
   * @return the current instance.
   */
  @SuppressWarnings("unchecked")
  public MENU_ACTION_TYPE withLabel(String label) {
    this.label = label;
    if (menuAnchor != null) {
      menuAnchor.setText(label);
    }
    return (MENU_ACTION_TYPE) this;
  }

  /**
   * Verifies if the user has the privilege to perform the menu action according to the given
   * {@link ContributionList}.<br/>
   * If not, the menu entry is removed from the menu container.
   * @param contributionListToVerifyOn the contribution list that contains the definition of user
   * privileges.
   */
  public void verify(final ContributionList contributionListToVerifyOn) {
    if (mustBeVerified) {
      mustBeVerified = false;
      AccessController.on(contributionListToVerifyOn)
          .doOnlyIfUnprivileged(new AccessController.Action() {
            @Override
            public void run() {
              mustBeRemoved = true;
            }
          });
    }
    menuItemWidget.setVisible(!mustBeRemoved);
    menuActions.verificationPerformedOn(this);
  }

  /**
   * Verifies if the user has the privilege to perform the menu action according to the given
   * {@link Contribution}.<br/>
   * If not, the menu entry is removed from the menu container.
   * @param contributionToVerifyOn the contribution that contains the definition of user
   * privileges.
   */
  public void verify(final Contribution contributionToVerifyOn) {
    if (mustBeVerified) {
      mustBeVerified = false;
      AccessController.on(contributionToVerifyOn)
          .doOnlyIfUnprivileged(new AccessController.Action() {
            @Override
            public void run() {
              mustBeRemoved = true;
            }
          });
    }
    menuItemWidget.setVisible(!mustBeRemoved);
    menuActions.verificationPerformedOn(this);
  }

  /**
   * Indicates if the menu action privileges has to be verified.
   * @return true if it has to be verified, false otherwise.
   */
  public boolean hasToBeVerified() {
    return mustBeVerified;
  }

  /**
   * Indicates that the menu action has to be removed from the menu container.
   * @return true if it has to be removed, false otherwise.
   */
  public boolean hasToBeRemoved() {
    return mustBeRemoved;
  }

  void setMenuItemWidget(final MenuItemWidget menuItemWidget) {
    this.menuItemWidget = menuItemWidget;
  }

  void setMenuAnchor(final MenuAnchor menuAnchor) {
    this.menuAnchor = menuAnchor;
  }

  void setMenuActions(final MenuActionListWrapper menuActions) {
    this.menuActions = menuActions;
  }
}

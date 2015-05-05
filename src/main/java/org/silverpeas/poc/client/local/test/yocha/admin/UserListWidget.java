package org.silverpeas.poc.client.local.test.yocha.admin;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.user.User;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated("../App.html#user-list-container")
public class UserListWidget extends Composite {

  @Inject
  @DataField
  private ListWidget<User, UserItemWidget> users;

  public ListWidget<User, UserItemWidget> getUsers() {
    return users;
  }
}

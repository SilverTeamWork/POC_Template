package org.silverpeas.poc.client.local.test.yocha.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.user.User;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated("../App.html#user")
public class UserItemWidget extends Composite implements HasModel<User> {

  private User user;

  @Inject
  @DataField
  private InlineLabel name;

  @Inject
  @DataField
  private InlineLabel accessLevel;

  @Override
  public User getModel() {
    return user;
  }

  @Override
  public void setModel(final User user) {
    this.user = user;
    this.name.setText(user.getName());
    this.accessLevel.setText(user.getAccessLevel());
  }
}

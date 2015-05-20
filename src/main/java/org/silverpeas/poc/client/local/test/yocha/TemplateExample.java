package org.silverpeas.poc.client.local.test.yocha;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.Dispatcher;
import org.silverpeas.poc.client.local.test.yocha.admin.UserListWidget;
import org.silverpeas.poc.client.local.test.yocha.ui.YochaHomePage;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.user.User;
import org.silverpeas.poc.client.local.user.UserCriteria;
import org.silverpeas.poc.client.local.util.Messages;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Templated("App.html#root")
public class TemplateExample extends Composite {

  @Inject
  @DataField
  private TextBox myField;

  @Inject
  @DataField
  private Button myButton;

  @Inject
  @DataField
  private TransitionAnchor<YochaHomePage> reload;

  @Inject
  @DataField("content")
  private UserListWidget userList;

  @EventHandler("myButton")
  void onClickMyButton(ClickEvent event) {
    Message.notifies(I18n.format(Messages.DISCONNECTION_CONFIRM, CurrentUser.get().getName()))
        .confirm(new Callback() {
          @Override
          public void invoke(final Object... parameters) {
            CurrentUser.markAsDisconnected();
            Dispatcher.home();
          }
        });
  }

  @AfterInitialization
  private void initialize() {
    myField.setText(CurrentUser.get().getName() + " à " +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE_SECOND)
            .format(new Date()));

    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        List<User> users = response.parseJsonEntities(new HttpResponse.JsonArrayLine<User>() {
          @Override
          public void perform(final int index, final User entity) {
            entity.setTags("Tag_" + index);
          }
        });
        userList.getUsers().setItems(users);
        if (((new Date().getTime()) % 2) == 0) {
          userList.setWidth("500px");
        } else {
          userList.setWidth("200px");
        }
      }
    }).get(UserCriteria.init());
  }
}

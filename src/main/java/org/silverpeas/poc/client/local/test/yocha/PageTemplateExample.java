package org.silverpeas.poc.client.local.test.yocha;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.Starter;
import org.silverpeas.poc.client.local.test.yocha.admin.UserListWidget;
import org.silverpeas.poc.client.local.test.yocha.layout.Header;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.user.User;
import org.silverpeas.poc.client.local.user.UserCriteria;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.client.local.util.Messages;
import org.turbogwt.core.collections.JsArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;

@Page(path = "example/{exampleId}")
@Templated("App.html#root")
public class PageTemplateExample extends Composite {

  @Inject
  @DataField
  private TextBox myField;

  @Inject
  @DataField
  private Button myButton;

  @Inject
  @DataField
  private Anchor myHome;

  @Inject
  @DataField
  private Header header;

  @Inject
  @DataField("content")
  private UserListWidget userList;

  @PageState
  private String exampleId;

  @EventHandler("myButton")
  void onClickMyButton(ClickEvent event) {
    Message.notifies(
        I18n.format(Messages.DISCONNECTION_CONFIRM, CurrentUser.get().getName()))
        .confirm(new Callback() {
          @Override
          public void invoke() {
            CurrentUser.markAsDisconnected();
            Starter.home();
          }
        });
  }

  @EventHandler("myHome")
  void onClickMyHome(ClickEvent event) {
    // Prevents from technical GWT error... (Because of @ApplicationScoped on Header class)
    header.removeFromParent();
    Starter.home();
  }

  @PageShown
  private void initialize() {
    myField.setText(CurrentUser.get().getName() + " à " +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE_SECOND)
            .format(new Date()));

    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        JsArray<User> users = JsonUtils.safeEval(response.getText());
        userList.getUsers().setItems(new JsArrayList<User>(users));
        if ((header.i % 2) == 0) {
          userList.setWidth("500px");
        } else {
          userList.setWidth("200px");
        }
      }
    }).get(UserCriteria.init());

    GWT.log("@ApplicationScoped - Header i=" + (header.i++));
  }
}

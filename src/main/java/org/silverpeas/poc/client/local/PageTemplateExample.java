package org.silverpeas.poc.client.local;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.layout.Header;
import org.silverpeas.poc.client.local.session.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Date;

@Page(path = "example")
@Dependent
@Templated("App.html#root")
@Bundle("messages.json")
public class PageTemplateExample extends Composite {

  @Inject
  @DataField
  private TextBox myField;

  @Inject
  @DataField
  private Button myButton;

  @Inject
  @DataField
  private Hyperlink myHome;

  @Inject
  private Header header;

  @Inject
  private TranslationService translationService;

  @EventHandler("myButton")
  void onClickMyButton(ClickEvent event) {
    Message.notifies(translationService.format(Messages.DISCONNECTION_CONFIRM))
        .confirm(new Callback() {
          @Override
          public void invoke() {
            CurrentUser.markAsDisconnected();
            Starter.go();
          }
        });
  }

  @EventHandler("myHome")
  void onClickMyHome(ClickEvent event) {
    Window.Location.assign("/home.html");
  }

  @PostConstruct
  private void initialize() {
    RootPanel.get().add(header);
    myField.setText(CurrentUser.get().getName() + " à " +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE_SECOND).format(new Date()));
  }
}

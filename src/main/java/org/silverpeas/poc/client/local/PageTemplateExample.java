package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hyperlink;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.session.CurrentUser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Formatter;

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
    myField.setText(CurrentUser.get().getName() + " à " +
        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE_SECOND).format(new Date()));
  }
}

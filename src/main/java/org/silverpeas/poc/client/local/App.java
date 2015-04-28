package org.silverpeas.poc.client.local;

import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Composite;

import javax.inject.Inject;

@EntryPoint
@Templated("#root")
@Bundle("messages.json")
public class App extends Composite {

  @Inject
  @DataField
  private TextBox myField;

  @Inject
  @DataField
  private Button myButton;

  @EventHandler
  void onClickMyButton(ClickEvent event) {
    // do something when clicked.
  }
}

package org.silverpeas.poc.client.local.widget.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

/**
 * A popup asking confirmation before performing an action. The action itself is delegated through
 * a handler.
 * @author miguel
 */
public class ConfirmationPopup extends PopupPanel {

  private String message;
  private ClickHandler handler;
  private SilverpeasHtmlPanel panel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.DIV);

  /**
   * Creates the confirmation popup panel.
   */
  public ConfirmationPopup() {
    super(true, true);
    setWidget(panel);
    setGlassEnabled(true);
    Label question = new Label();
    FlowPanel buttonBar = new FlowPanel();
    Button ok = new Button(I18n.format(CommonTranslations.ACTION_OK));
    ok.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (getOkHandler() != null) {
          getOkHandler().onClick(event);
        }
        hide();
      }
    });
    Button cancel = new Button(I18n.format(CommonTranslations.ACTION_CANCEL));
    cancel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    buttonBar.add(cancel);
    buttonBar.add(ok);

    panel.add(question);
    panel.add(buttonBar);
  }

  /**
   * Creates the confirmation popup panel with a confirmation message.
   * @param msg the confirmation message to display.
   */
  public ConfirmationPopup(String msg) {
    this();
    this.message = msg;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public String getMessage() {
    return (message != null ? message : "");
  }

  public void setOkHandler(ClickHandler handler) {
    this.handler = handler;
  }

  private ClickHandler getOkHandler() {
    return this.handler;
  }

  @Override
  protected void doAttachChildren() {
    super.doAttachChildren();
    ((Label) panel.getWidget(0)).setText(getMessage());
    FlowPanel buttonBar = (FlowPanel) panel.getWidget(1);
    ((Button) buttonBar.getWidget(0)).setFocus(true);
  }

}

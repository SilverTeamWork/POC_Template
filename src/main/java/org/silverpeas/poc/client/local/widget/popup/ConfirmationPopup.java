package org.silverpeas.poc.client.local.widget.popup;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
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
    panel.setStyleName(
        "ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons " +
            "ui-draggable ui-resizable");
    panel.setWidth("320px");
    panel.setHeight("auto");
    Label question = new Label();
    FlowPanel buttonBar = new FlowPanel();
    buttonBar.setStyleName("ui-dialog-buttonpane ui-widget-content ui-helper-clearfix");
    FlowPanel buttonSet = new FlowPanel();
    buttonSet.setStyleName("ui-dialog-buttonset");
    final Button ok = new Button();
    ok.setStyleName("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
    InlineLabel label = new InlineLabel(I18n.format(CommonTranslations.ACTION_OK));
    label.setStyleName("ui-button-text");
    ok.setHTML(label.toString());
    ok.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        ok.addStyleName("ui-state-hover");
      }
    });
    ok.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        ok.removeStyleName("ui-state-hover");
      }
    });
    ok.addMouseOverHandler(new MouseOverHandler() {
      @Override
      public void onMouseOver(final MouseOverEvent event) {
        ok.addStyleName("ui-state-hover");
        ok.setFocus(true);
      }
    });
    ok.addMouseOutHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(final MouseOutEvent event) {
        ok.removeStyleName("ui-state-hover");
      }
    });
    ok.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (getOkHandler() != null) {
          getOkHandler().onClick(event);
        }
        hide();
      }
    });
    final Button cancel = new Button();
    cancel.setStyleName("ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
    label = new InlineLabel(I18n.format(CommonTranslations.ACTION_CANCEL));
    label.setStyleName("ui-button-text");
    cancel.setHTML(label.toString());
    cancel.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        cancel.addStyleName("ui-state-hover");
      }
    });
    cancel.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        cancel.removeStyleName("ui-state-hover");
      }
    });
    cancel.addMouseOverHandler(new MouseOverHandler() {
      @Override
      public void onMouseOver(final MouseOverEvent event) {
        cancel.addStyleName("ui-state-hover");
        cancel.setFocus(true);
      }
    });
    cancel.addMouseOutHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(final MouseOutEvent event) {
        cancel.removeStyleName("ui-state-hover");
      }
    });
    cancel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    buttonSet.add(cancel);
    buttonSet.add(ok);
    buttonBar.add(buttonSet);

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
    FlowPanel buttonSet = (FlowPanel) buttonBar.getWidget(0);
    Button cancel = (Button) buttonSet.getWidget(0);
    cancel.setFocus(true);
  }

}

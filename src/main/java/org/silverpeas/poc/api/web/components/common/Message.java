package org.silverpeas.poc.api.web.components.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.silverpeas.poc.api.Callback;

import static org.silverpeas.poc.api.util.StringUtil.isDefined;
import static org.silverpeas.poc.client.local.util.BundleProvider.msg;

/**
 * @author Yohann Chastagnier
 */
public class Message {
  private final static Label emptyLabel = new Label("");

  static {
    emptyLabel.setSize("auto", "25px");
  }

  private static enum TYPE {
    info, confirmation, warning, error
  }

  private final IsWidget content;
  private String title = "";
  private Callback callback;

  public static Message notifies(IsWidget content) {
    return new Message(content);
  }

  public static Message notifies(String content) {
    return new Message(new InlineHTML(content));
  }

  private Message(IsWidget content) {
    this.content = content;
  }

  public Message withTitle(String title) {
    this.title = title;
    return this;
  }

  public DialogBox info() {
    return display(TYPE.info);
  }

  public DialogBox error() {
    return display(TYPE.error);
  }


  public DialogBox confirm(Callback callback) {
    this.callback = callback;
    return display(TYPE.confirmation);
  }

  private DialogBox display(TYPE type) {
    final DialogBox box = new DialogBox() {
      @Override
      protected void onPreviewNativeEvent(final Event.NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        if (event.getTypeInt() == Event.ONKEYUP &&
            event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
          hide();
        }
      }
    };
//    box.addStyleName("dialog-notify");
    box.setGlassEnabled(true);

    final VerticalPanel panel = new VerticalPanel();

    // Title
    if (isDefined(title)) {
      box.setText(title);
    }

    // Content
    panel.add(content);

    switch (type) {
      case error:
      case info:
        panel.add(emptyLabel);
        panel.add(emptyLabel);
        final Button okButton = new Button(msg().ok(), new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            box.hide();
          }
        });
        panel.add(okButton);
        panel.setCellHorizontalAlignment(okButton, HasAlignment.ALIGN_RIGHT);
        break;
      case confirmation:
        panel.add(emptyLabel);
        panel.add(emptyLabel);
        final Button yesButton = new Button(msg().yes(), new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            callback.invoke();
            box.hide();
          }
        });
        final Button noButton = new Button(msg().no(), new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            box.hide();
          }
        });
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        panel.add(buttonPanel);
        panel.setCellHorizontalAlignment(buttonPanel, HasAlignment.ALIGN_RIGHT);
        break;
      case warning:
        break;
    }

    box.setWidget(panel);
    box.center();

    return box;
  }
}
package org.silverpeas.poc.api.web.components.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import static org.silverpeas.poc.api.util.StringUtil.isDefined;
import static org.silverpeas.poc.client.local.BundleProvider.msg;

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

  private final String content;
  private String title = "";

  public static Message notifies(String content) {
    return new Message(content);
  }

  private Message(String content) {
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

  private DialogBox display(TYPE type) {
    final DialogBox box = new DialogBox();
    box.addStyleName("dialog-notify");
    box.setGlassEnabled(true);

    final VerticalPanel panel = new VerticalPanel();

    // Title
    if (isDefined(title)) {
      box.setText(title);
    }

    // Content
    if (isDefined(content)) {
      panel.add(new InlineHTML(content));
      panel.add(emptyLabel);
      panel.add(emptyLabel);
    }

    switch (type) {
      case error:
      case info:
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
        break;
      case warning:
        break;
    }

    box.setWidget(panel);
    box.center();

    return box;
  }
}

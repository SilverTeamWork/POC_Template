package org.silverpeas.poc.client.local.widget.title;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.client.local.util.AccessController;
import org.silverpeas.poc.client.local.util.Publication;
import org.silverpeas.poc.client.local.util.ValueChangeHandler;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

/**
 * A widget to render the title of a publication and that is editable for a privileged user.
 * If the user isn't privileged, then the title is clickable and can be
 * @author miguel
 */
public class EditableTitleWidget extends Composite
    implements HasModel<Publication>, MouseOverHandler, MouseOutHandler {

  private InlineLabel title;
  private TextBox textBox = new TextBox();
  private Publication publication;
  private SilverpeasHtmlPanel panel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.A);
  private Anchor transition;
  private ValueChangeHandler<String> handler = null;

  public EditableTitleWidget() {
    initWidget(panel);
    panel.getElement().setAttribute("href", "#");
    title = new InlineLabel();
    title.setStyleName("text");
  }

  /**
   * The title will be wrapped by an anchor. The anchor will be taken into account only if the user
   * doesn't have the privilege to change the title.
   * @param anchor the anchor to use to wrap the title.
   * @return itself.
   */
  public EditableTitleWidget withAnchor(final Anchor anchor) {
    this.transition = anchor;
    return this;
  }

  public void setValueChangeHandler(final ValueChangeHandler<String> handler) {
    this.handler = handler;
  }

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public Publication getModel() {
    return this.publication;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final Publication model) {
    this.publication = model;
    title.setText(this.publication.getTitle());
    AccessController.on(publication).doOnlyIfPrivileged(new AccessController.Action() {
      @Override
      public void run() {
        addDomHandler(EditableTitleWidget.this, MouseOverEvent.getType());
        addDomHandler(EditableTitleWidget.this, MouseOutEvent.getType());
      }
    });
  }

  public String getText() {
    return this.title.getText();
  }

  /**
   * If a widget contains one or more child widgets that are not in the logical
   * widget hierarchy (the child is physically connected only on the DOM level),
   * it must override this method and call {@link #onAttach()} for each of its
   * child widgets.
   * @see #onAttach()
   */
  @Override
  protected void doAttachChildren() {
    AccessController.on(publication).doOnlyIfPrivileged(new AccessController.Action() {
      @Override
      public void run() {
        textBox.setStyleName("input text");
        textBox.setWidth("80%");
        textBox.addKeyPressHandler(new KeyPressHandler() {
          @Override
          public void onKeyPress(final KeyPressEvent event) {
            if (event.getCharCode() == KeyCodes.KEY_ENTER) {
              if (handler != null) {
                String newTitle = textBox.getText();
                if (!newTitle.equals(publication.getTitle())) {
                  handler.onChange(publication.getTitle(), textBox.getText());
                }
              }
              unedit();
            }
            event.stopPropagation();
          }
        });
        panel.add(title);
      }
    }).doOnlyIfUnprivileged(new AccessController.Action() {
      @Override
      public void run() {
        if (transition != null) {
          transition.getElement().appendChild(title.getElement());
          panel.add(transition);
        } else {
          panel.add(title);
        }
      }
    });
  }

  /**
   * Called when MouseOutEvent is fired.
   * @param event the {@link MouseOutEvent} that was fired
   */
  @Override
  public void onMouseOut(final MouseOutEvent event) {
    unedit();
  }

  /**
   * Called when MouseOverEvent is fired.
   * @param event the {@link MouseOverEvent} that was fired
   */
  @Override
  public void onMouseOver(final MouseOverEvent event) {
    AccessController.on(publication).doOnlyIfPrivileged(new AccessController.Action() {
      @Override
      public void run() {
        textBox.setText(publication.getTitle());
        panel.clear();
        panel.add(textBox);
        textBox.setFocus(true);
      }
    });
  }

  /**
   * Indicates if the cursor is over the widget and if the user can modify the content.
   * @return true if the cursor is over and if the content is editable.
   */
  public boolean isEditModeFocused() {
    return textBox.isAttached();
  }

  private void unedit() {
    AccessController.on(publication).doOnlyIfPrivileged(new AccessController.Action() {
      @Override
      public void run() {
        panel.clear();
        panel.add(title);
      }
    });
  }
}

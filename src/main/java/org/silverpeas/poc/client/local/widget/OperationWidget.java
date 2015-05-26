package org.silverpeas.poc.client.local.widget;

import com.google.common.collect.Multimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.silverpeas.poc.api.navigation.NavigationProvider;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.widget.popup.ConfirmationPopup;

/**
 * A panel that provides two functions on a contribution: the modification and the deletion.
 * The modification invokes the transition to the edition page of the contribution whereas the
 * deletion triggers the handler passed by the caller. The modification and the deletion are thus
 * delegated to the caller.
 * @author miguel
 */
public class OperationWidget extends Composite {

  private SilverpeasHtmlPanel panel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.DIV);
  private TransitionAnchor modify = null;
  private ConfirmationPopup popup = new ConfirmationPopup();
  private ClickHandler handler;

  public OperationWidget() {
    initWidget(panel);
    popup.setMessage(I18n.format(CommonTranslations.MESSAGE_CONFIRMATION_DELETION));
    popup.setOkHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (handler != null) {
          handler.onClick(event);
        }
      }
    });
  }

  public void setEditionPage(Class<? extends SilverpeasPageComposite> page,
      Multimap<String, String> state) {
    this.modify = NavigationProvider.get().getTransitionAnchor(page, state);
  }

  public void setDeletionHandler(ClickHandler handler) {
    this.handler = handler;
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
    panel.setStyleName("operation");
    if (modify != null) {
      modify.setHref("#");
      modify.setTitle(I18n.format(CommonTranslations.ACTION_MODIFICATION));
      Image image = new Image();
      image.setAltText(I18n.format(CommonTranslations.ACTION_MODIFICATION));
      image.setTitle(I18n.format(CommonTranslations.ACTION_MODIFICATION));
      image.setUrl("/img/update.gif");
      modify.setHTML(image.toString());
      panel.add(modify);
    }

    Anchor delete = new Anchor();
    delete.setTitle(I18n.format(CommonTranslations.ACTION_DELETION));
    Image image = new Image();
    image.setAltText(I18n.format(CommonTranslations.ACTION_DELETION));
    image.setTitle(I18n.format(CommonTranslations.ACTION_DELETION));
    image.setUrl("/img/delete.gif");
    delete.setHTML(image.toString());
    delete.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        event.stopPropagation();
        popup.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop());
        popup.show();
      }
    });
    panel.add(delete);
  }
}

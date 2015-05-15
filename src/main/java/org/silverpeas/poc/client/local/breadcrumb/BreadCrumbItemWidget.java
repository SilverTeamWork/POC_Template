package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Anchor;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

/**
 * @author miguel
 */
public class BreadCrumbItemWidget extends Anchor implements HasModel<BreadCrumbItem>, ClickHandler {
  private BreadCrumbItem item;

  public BreadCrumbItemWidget() {
    addClickHandler(this);
  }

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  @Override
  public BreadCrumbItem getModel() {
    return this.item;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  @Override
  public void setModel(final BreadCrumbItem model) {
    this.item = model;
    setText(this.item.getLabel());
    setStyleName(this.item.getStyleClass());
    setEnabled(this.item.isEnabled());
    HistoryTokenFactory htFactory = BeanManager.getInstanceOf(HistoryTokenFactory.class);
    HistoryToken token = htFactory.createHistoryToken(this.item.getTargetPage().getSimpleName(),
        this.item.getTransitionParameters());
    setHref("#" + token.toString());
  }

  /**
   * Called when a native click event is fired.
   * @param event the {@link ClickEvent} that was fired
   */
  @Override
  public void onClick(final ClickEvent event) {
    if (isEnabled()) {
      getModel().go();
    }

    event.stopPropagation();
    event.preventDefault();
  }
}

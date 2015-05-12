package org.silverpeas.poc.client.local.breadcrumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * A widget to render a breadcrumb indicating the navigation level of the user in the current page.
 * @author miguel
 */
@ApplicationScoped
@Templated
public class BreadCrumbWidget extends Composite {

  @Inject
  @DataField
  private TransitionAnchor<HomePage> silverpeasHome;

  @Inject
  @DataField
  private SilverpeasHtmlPanel content;

  @Inject
  private Navigation navigation;

  private BreadCrumb breadCrumb;

  @PostConstruct
  private void init() {
    setModel(new BreadCrumb());
  }

  /**
   * Returns the model instance associated with this widget.
   * @return the model instance, or null if no instance is associated with this widget.
   */
  public BreadCrumb getModel() {
    return breadCrumb;
  }

  /**
   * Associate the model instance with this widget.
   * @param model the model instance.
   */
  private void setModel(final BreadCrumb model) {
    this.breadCrumb = model;
  }

  /**
   * Refreshes the display of the breadcrumb.
   */
  public void refresh() {
    content.clear();
    content.add(silverpeasHome);
    for (final BreadCrumbItem item : breadCrumb.getItems()) {
      Anchor anchor = new Anchor(item.getLabel());
      anchor.setStyleName(item.getStyleClass());
      anchor.setHref("#");
      anchor.addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          breadCrumb.backTo(item);
        }
      });
      content.add(anchor);
    }
  }
}

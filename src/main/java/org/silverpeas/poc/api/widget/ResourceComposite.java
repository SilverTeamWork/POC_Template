package org.silverpeas.poc.api.widget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.event.widget.HasResourceDeleteHandlers;
import org.silverpeas.poc.api.event.widget.HasResourceSaveHandlers;
import org.silverpeas.poc.api.event.widget.ResourceDeleteEvent;
import org.silverpeas.poc.api.event.widget.ResourceDeleteHandler;
import org.silverpeas.poc.api.event.widget.ResourceSaveEvent;
import org.silverpeas.poc.api.event.widget.ResourceSaveHandler;

/**
 * @author Yohann Chastagnier
 */
public class ResourceComposite<R> extends Composite
    implements HasModel<R>, HasResourceSaveHandlers<R>, HasResourceDeleteHandlers<R> {

  private R resource;

  @Override
  public R getModel() {
    return resource;
  }

  @Override
  public void setModel(final R resource) {
    this.resource = resource;
  }

  @Override
  public HandlerRegistration addResourceSaveHandler(final ResourceSaveHandler<R> handler) {
    return addHandler(handler, ResourceSaveEvent.getType());
  }

  @Override
  public HandlerRegistration addDeleteResourceHandler(final ResourceDeleteHandler<R> handler) {
    return addHandler(handler, ResourceDeleteEvent.getType());
  }
}

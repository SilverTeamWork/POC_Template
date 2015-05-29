package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import io.reinert.gdeferred.Deferred;
import io.reinert.gdeferred.DoneCallback;
import io.reinert.gdeferred.impl.DeferredObject;
import org.silverpeas.poc.api.event.widget.ContentChangeEvent;
import org.silverpeas.poc.api.event.widget.ContentChangeHandler;
import org.silverpeas.poc.api.event.widget.HasContentChangeHandlers;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.StringUtil;

/**
 * An item in a breadcrumb. The item represents a node in the navigation level of the user in the
 * current page. It can be a space, a Silverpeas application or a resource of the application.
 * @author miguel
 */
public abstract class BreadCrumbItem<BCI> implements HasContentChangeHandlers {
  private HandlerManager handlerManager = new HandlerManager(this);

  private String label = "";
  private String toPageName;
  private Multimap<String, String> parameters = null;
  private Deferred<Void, Void, Void> defined = new DeferredObject<>();

  private boolean enabled = true;

  public String getLabel() {
    return label;
  }

  public abstract String getStyleClass();

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  protected String getTargetPageName() {
    return toPageName;
  }

  protected Multimap<String, String> getTransitionParameters() {
    if (parameters == null) {
      return ImmutableMultimap.of();
    }
    return parameters;
  }

  protected abstract void fireItemEvent();

  @SuppressWarnings("unchecked")
  public BCI withLabel(String label) {
    this.label = label;
    resolveIfDefined();
    return (BCI) this;
  }

  @SuppressWarnings("unchecked")
  public BCI withToPageName(final String toPageName) {
    this.toPageName = toPageName;
    resolveIfDefined();
    return (BCI) this;
  }

  public BCI withToPageName(final Class toPageClass) {
    return withToPageName(toPageClass.getSimpleName());
  }

  @SuppressWarnings("unchecked")
  public BCI withParameters(final Multimap<String, String> parameters) {
    this.parameters = parameters;
    resolveIfDefined();
    return (BCI) this;
  }

  protected void resolveIfDefined() {
    if (StringUtil.isDefined(label) && parameters != null &&
        StringUtil.isDefined(toPageName)) {
      if (defined.isPending()) {
        defined.resolve(null);
      }
      ContentChangeEvent.fire(this);
    }
  }

  @SuppressWarnings("unchecked")
  public BCI add() {
    final BreadCrumbItem me = this;
    defined.then(new DoneCallback<Void>() {
      @Override
      public void onDone(final Void result) {
        Log.debug("Adding breadcrumb item: with label " + getLabel() + " for page " +
            getTargetPageName() + " with parameters " + getTransitionParameters().toString());
        BreadCrumbWidget.addItem(me);
      }
    });
    return (BCI) this;
  }

  @Override
  public HandlerRegistration addResourceContentChangeHandler(final ContentChangeHandler handler) {
    return handlerManager.addHandler(ContentChangeEvent.getType(), handler);
  }

  @Override
  public final void fireEvent(final GwtEvent<?> event) {
    handlerManager.fireEvent(event);
  }
}

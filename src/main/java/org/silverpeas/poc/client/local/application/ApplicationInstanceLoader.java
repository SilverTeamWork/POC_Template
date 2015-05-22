package org.silverpeas.poc.client.local.application;

import io.reinert.gdeferred.Deferred;
import io.reinert.gdeferred.Promise;
import io.reinert.gdeferred.impl.DeferredObject;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.application.event.LoadedApplicationInstance;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;
import org.silverpeas.poc.client.local.space.SpaceCriteria;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.util.EventsProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A loader of data about the application instances from a Silverpeas backend. The loading is
 * performed asynchronously and then an event is fired once it is successfully done.
 * @author miguel
 */
@Singleton
public class ApplicationInstanceLoader {

  @Inject
  private SpaceLoader spaceLoader;

  public static ApplicationInstanceLoader get() {
    return BeanManager.getInstanceOf(ApplicationInstanceLoader.class);
  }

  /**
   * Loads the root spaces and fires an event once they are loaded.
   */
  public Promise<ApplicationInstance, Void, Void> loadComponent(final String instanceId) {
    final Deferred<ApplicationInstance, Void, Void> deferred = new DeferredObject<>();

    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        ApplicationInstance instance = response.parseJsonEntity();
        loadParentSpace(deferred, instance, instance);
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.dev("Error while getting an application instance: " + response.getStatusText());
        deferred.reject(null);
      }
    }).get(ApplicationInstanceCriteria.fromId(instanceId));

    return deferred.promise();
  }

  private void loadParentSpace(final Deferred<ApplicationInstance, Void, Void> deferred,
      final ApplicationInstance initialInstance, final SpaceContent<Space> child) {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Space parentSpace = response.parseJsonEntity();
        child.setParent(parentSpace);
        if (StringUtil.isDefined(parentSpace.getParentUri())) {
          loadParentSpace(deferred, initialInstance, parentSpace);
        } else {
          Log.debug("Send ApplicationInstanceLoaded event for {0} (type={1}, id={2})",
              initialInstance.getLabel(), initialInstance.getComponentName(),
              initialInstance.getId());

          // Notifying
          deferred.resolve(initialInstance);
        }
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.dev("Error while getting parent space: " + response.getStatusText());
        deferred.reject(null);
      }
    }).get(SpaceCriteria.fromUrl(child.getParentUri()));
  }
}

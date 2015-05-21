package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import io.reinert.gdeferred.Deferred;
import io.reinert.gdeferred.DoneCallback;
import io.reinert.gdeferred.Promise;
import io.reinert.gdeferred.impl.DeferredObject;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.space.event.LoadedRootSpaces;
import org.silverpeas.poc.client.local.space.event.LoadedSpace;
import org.silverpeas.poc.client.local.space.event.LoadedSpaceContent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static org.silverpeas.poc.client.local.Starter.triggerSilverpeasIsStarted;
import static org.silverpeas.poc.client.local.Starter.whenSilverpeasIsStarted;

/**
 * A loader of data about the spaces from a Silverpeas backend. The loading is performed
 * asynchronously and then an event is fired once it is successfully done.
 * @author miguel
 */
@Singleton
public class SpaceLoader {

  @Inject
  private Event<LoadedRootSpaces> spaceLoadedEvent;
  @Inject
  private Event<LoadedSpace> loadedSpaceEvent;
  @Inject
  private Event<LoadedSpaceContent> spaceContentLoadedEvent;

  public static SpaceLoader get() {
    return BeanManager.getInstanceOf(SpaceLoader.class);
  }

  public Promise<Space, Void, Void> loadSpace(final String spaceId) {
    final Deferred<Space, Void, Void> deferred = new DeferredObject<>();
    whenSilverpeasIsStarted().then(new DoneCallback<Void>() {
      @Override
      public void onDone(final Void result) {
        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            Space space = response.parseJsonEntity();
            // Notifying
            deferred.resolve(space);
            loadedSpaceEvent.fire(new LoadedSpace(space));
          }
        }).onError(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            Log.dev("Error while getting space: " + response.getStatusText());
            deferred.reject(null);
          }
        }).get(SpaceCriteria.fromId(spaceId));
      }
    });
    return deferred.promise();
  }

  /**
   * Loads the root spaces and fires an event once they are loaded.
   */
  public Promise<List<Space>, Void, Void> loadRootSpaces() {
    final Deferred<List<Space>, Void, Void> deferred = new DeferredObject<>();
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        JsArray<Space> jsRootSpaces = JsonUtils.safeEval(response.getText());
        List<Space> rootSpaces = new ArrayList<>(jsRootSpaces.length());
        for (int i = 0; i < jsRootSpaces.length(); i++) {
          Space space = jsRootSpaces.get(i);
          if (space.isRoot()) {
            rootSpaces.add(space.getRank(), space);
          }
        }
        // Notifying
        deferred.resolve(rootSpaces);
        spaceLoadedEvent.fire(new LoadedRootSpaces(rootSpaces));
        triggerSilverpeasIsStarted();
        Log.debug("Root spaces loaded: {0}", rootSpaces.size());
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.dev("Error while getting root spaces: " + response.getStatusText());
        deferred.reject(null);
      }
    }).get(new SpaceCriteria());
    return deferred.promise();
  }

  /**
   * Loads the content of the specified space and fires an event once the content is loaded.
   * @param rootSpace the space for which its content has to be loaded.
   */
  public Promise<Space, Void, Void> loadSpaceContent(final Space rootSpace) {
    final Deferred<Space, Void, Void> deferred = new DeferredObject<>();

    whenSilverpeasIsStarted().then(new DoneCallback<Void>() {
      @Override
      public void onDone(final Void result) {
        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            List<Space> spaces = response.parseJsonEntities();
            final List<SpaceContent> spaceContent = new ArrayList<>(spaces.size());
            for (Space space : spaces) {
              space.setParent(rootSpace);
              spaceContent.add(space.getRank(), space);
            }

            JsonHttp.onSuccess(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                response.parseJsonEntities(new HttpResponse.JsonArrayLine<ApplicationInstance>() {
                  @Override
                  public void perform(final int index, final ApplicationInstance instance) {
                    instance.setParent(rootSpace);
                    spaceContent.add(instance);
                  }
                });
                rootSpace.setContent(spaceContent);
                // Notifying
                deferred.resolve(rootSpace);
                spaceContentLoadedEvent.fire(new LoadedSpaceContent(rootSpace));
              }
            }).onError(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                Log.dev("Error while getting root spaces: " + response.getStatusText());
                deferred.reject(null);
              }
            }).get(SpaceCriteria.fromUrl(rootSpace.getComponentsUri()));

          }
        }).onError(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            Log.dev("Error while getting space content: " + response.getStatusText());
            deferred.reject(null);
          }
        }).get(SpaceCriteria.fromUrl(rootSpace.getSpacesUri()));
      }
    });

    return deferred.promise();
  }
}

package org.silverpeas.poc.client.local.space;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.space.event.SpaceContentLoaded;
import org.silverpeas.poc.client.local.space.event.SpaceLoaded;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * A loader of data about the spaces from a Silverpeas backend. The loading is performed
 * asynchronously and then an event is fired once it is successfully done.
 * @author miguel
 */
@Singleton
public class SpaceLoader {

  @Inject
  private Event<SpaceLoaded> spaceLoaded;
  @Inject
  private Event<SpaceContentLoaded> spaceContentLoaded;

  /**
   * Loads the root spaces and fires an event once they are loaded.
   */
  public void loadRootSpaces() {
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
        spaceLoaded.fire(new SpaceLoaded(rootSpaces));
      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.dev("Error while getting root spaces: " + response.getStatusText());
      }
    }).get(new SpaceCriteria());
  }

  /**
   * Loads the content of the specified space and fires an event once the content is loaded.
   * @param space the space for which its content has to be loaded.
   */
  public void loadSpaceContent(final Space space) {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        List<Space> spaces = response.parseJsonEntities();
        final List<SpaceContent> spaceContent = new ArrayList<>(spaces.size());
        for (Space space : spaces) {
          spaceContent.add(space.getRank(), space);
        }

        JsonHttp.onSuccess(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            List<ApplicationInstance> instances =
                response.parseJsonEntities(new HttpResponse.JsonArrayLine<ApplicationInstance>() {
                  @Override
                  public void perform(final int index, final ApplicationInstance instance) {
                    spaceContent.add(instance);
                  }
                });
            space.setContent(spaceContent);
            spaceContentLoaded.fire(new SpaceContentLoaded(space));
          }
        }).onError(new JsonResponse() {
          @Override
          public void process(final HttpResponse response) {
            Log.dev("Error while getting root spaces: " + response.getStatusText());
          }
        }).get(SpaceCriteria.fromUrl(space.getComponentsUri()));

      }
    }).onError(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Log.dev("Error while getting space content: " + response.getStatusText());
      }
    }).get(SpaceCriteria.fromUrl(space.getSpacesUri()));
  }
}

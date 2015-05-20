package org.silverpeas.poc.api.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import org.silverpeas.poc.api.web.components.common.Waiting;
import org.silverpeas.poc.client.local.user.CurrentUser;

import java.util.Map;

/**
 * @author Yohann Chastagnier
 */
public class JsonHttp {

  private static final String JSON_DATA_SERVER = "/silverpeas/services/";

  private static JsonResponse unauthorizedCallback;

  private final JsonResponse successCallback;
  private JsonResponse errorCallback;
  private String dataToSend = null;
  private boolean showWaiting = false;

  public static void setUnauthorizedCallback(JsonResponse unauthorizedCallback) {
    JsonHttp.unauthorizedCallback = unauthorizedCallback;
  }

  public static JsonHttp onSuccess(JsonResponse callback) {
    return new JsonHttp(callback);
  }

  private JsonHttp(JsonResponse callback) {
    this.successCallback = callback;
  }

  public JsonHttp onError(JsonResponse callback) {
    this.errorCallback = callback;
    return this;
  }

  public JsonHttp withData(String dataToSend) {
    this.dataToSend = dataToSend;
    return this;
  }

  public JsonHttp waitingMessage() {
    this.showWaiting = true;
    return this;
  }

  public HttpRequest get(JsonGetCriteria criteria) {
    JsonHttpConfig jsonHttpConfig = criteria.configureJsonGetHttp();
    return performRequest(
        new RequestBuilder(RequestBuilder.GET, normalizeUri(jsonHttpConfig.getUrl())),
        jsonHttpConfig);
  }

  public HttpRequest post(JsonPostCriteria criteria) {
    JsonHttpConfig jsonHttpConfig = criteria.configureJsonPostHttp();
    return performRequest(
        new RequestBuilder(RequestBuilder.POST, normalizeUri(jsonHttpConfig.getUrl())),
        jsonHttpConfig);
  }

  private void hideWaiting() {
    if (showWaiting) {
      Waiting.hide();
    }
  }

  private HttpRequest performRequest(RequestBuilder builder, JsonHttpConfig jsonHttpConfig) {
    if (showWaiting) {
      Waiting.show();
    }
    try {
      if (CurrentUser.exists()) {
        builder.setHeader("X-Silverpeas-Session", CurrentUser.token());
      }
      for (Map.Entry<String, String> header : jsonHttpConfig.getHeaders().entrySet()) {
        builder.setHeader(header.getKey(), header.getValue());
      }
      return new HttpRequest(builder.sendRequest(dataToSend, new RequestCallback() {

        @Override
        public void onResponseReceived(final Request request, final Response response) {
          try {
            HttpResponse httpResponse = new HttpResponse(response);
            if (httpResponse.getStatusCode() == Response.SC_OK) {
              successCallback.process(httpResponse);
            } else {
              try {
                if (errorCallback != null) {
                  errorCallback.process(httpResponse);
                }
              } finally {
                if (httpResponse.getStatusCode() == Response.SC_UNAUTHORIZED &&
                    unauthorizedCallback != null) {
                  unauthorizedCallback.process(httpResponse);
                }
              }
            }
          } finally {
            hideWaiting();
          }
        }

        @Override
        public void onError(final Request request, final Throwable exception) {
          hideWaiting();
        }
      }));
    } catch (RequestException e) {
      hideWaiting();
      return null;
    }
  }

  private String normalizeUri(String uri) {
    if (!uri.toLowerCase().startsWith("http")) {
      uri = Window.Location.createUrlBuilder().setPort(8000).setPath(JSON_DATA_SERVER + uri)
          .buildString();
    }
    return uri;
  }
}

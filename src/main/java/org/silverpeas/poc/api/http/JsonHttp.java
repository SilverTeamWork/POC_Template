package org.silverpeas.poc.api.http;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import org.silverpeas.poc.api.web.components.common.Waiting;
import org.silverpeas.poc.client.local.session.CurrentUser;

import java.util.Map;

/**
 * @author Yohann Chastagnier
 */
public class JsonHttp {

  private static final String JSON_DATA_SERVER = "http://localhost:8000/silverpeas/services/";

  private final JsonResponse successCallback;
  private JsonResponse errorCallback;
  private String dataToSend = null;
  private boolean showWaiting = true;

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

  public JsonHttp noWaitingMessage() {
    this.showWaiting = false;
    return this;
  }

  public HttpRequest get(JsonGetCriteria criteria) {
    JsonHttpConfig jsonHttpConfig = criteria.configureJsonGetHttp();
    return performRequest(
        new RequestBuilder(RequestBuilder.GET, JSON_DATA_SERVER + jsonHttpConfig.getUrl()),
        jsonHttpConfig);
  }

  public HttpRequest post(JsonPostCriteria criteria) {
    JsonHttpConfig jsonHttpConfig = criteria.configureJsonPostHttp();
    return performRequest(
        new RequestBuilder(RequestBuilder.POST, JSON_DATA_SERVER + jsonHttpConfig.getUrl()),
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
            } else if (errorCallback != null) {
              errorCallback.process(httpResponse);
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
}
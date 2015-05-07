package org.silverpeas.poc.api.http;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;
import org.turbogwt.core.collections.JsArrayList;

import java.util.List;

/**
 * @author Yohann Chastagnier
 */
public class HttpResponse extends Response {

  private final Response response;

  HttpResponse(final Response response) {
    this.response = response;
  }

  @Override
  public String getHeader(final String header) {
    return response.getHeader(header);
  }

  @Override
  public Header[] getHeaders() {
    return response.getHeaders();
  }

  @Override
  public String getHeadersAsString() {
    return response.getHeadersAsString();
  }

  @Override
  public int getStatusCode() {
    return response.getStatusCode();
  }

  @Override
  public String getStatusText() {
    return response.getStatusText();
  }

  @Override
  public String getText() {
    return response.getText();
  }

  /**
   * Parses the JSON from the body of the response in order to return a JSNI entity.
   * @param <T> the JSNI entity type.
   * @return the JSNI entity instance.
   */
  public <T extends JavaScriptObject> T parseJsonEntity() {
    return JsonUtils.safeEval(response.getText());
  }

  /**
   * Parses the JSON from the body of the response in order to return a list of JSNI entity.
   * @param <T> the JSNI type of one entity of the list.
   * @return the list of JSNI entity instances.
   */
  public <T extends JavaScriptObject> List<T> parseJsonEntities() {
    JsArray<T> entities = JsonUtils.safeEval(response.getText());
    return new JsArrayList<>(entities);
  }

  /**
   * Parses the JSON from the body of the response in order to return a list of JSNI entity.
   * @param line an instance of {@link JsonArrayLine} which permits to perform a treatment on each
   * entity of the list.
   * @param <T> the JSNI type of one entity of the list.
   * @return the list of JSNI entity instances.
   */
  public <T extends JavaScriptObject> List<T> parseJsonEntities(JsonArrayLine<T> line) {
    List<T> entities = parseJsonEntities();
    int i = 0;
    for (T entity : entities) {
      line.perform(i++, entity);
    }
    return entities;
  }

  public interface JsonArrayLine<T> {
    void perform(int index, T entity);
  }
}

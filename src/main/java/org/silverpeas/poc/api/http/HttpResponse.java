package org.silverpeas.poc.api.http;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Response;

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
}

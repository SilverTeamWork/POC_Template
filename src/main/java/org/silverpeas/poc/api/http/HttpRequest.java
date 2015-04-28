package org.silverpeas.poc.api.http;

import com.google.gwt.http.client.Request;

/**
 * @author Yohann Chastagnier
 */
public class HttpRequest {

  private final Request clientRequest;

  HttpRequest(final Request clientRequest) {
    this.clientRequest = clientRequest;
  }

  public void cancel() {
    clientRequest.cancel();
  }

  public boolean isPending() {
    return clientRequest.isPending();
  }
}

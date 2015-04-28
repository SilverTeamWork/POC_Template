package org.silverpeas.poc.api.http;

/**
 * @author Yohann Chastagnier
 */
public interface JsonResponse {
  void process(HttpResponse response);
}

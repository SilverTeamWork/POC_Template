package org.silverpeas.poc.api.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yohann Chastagnier
 */
public class JsonHttpConfig {

  private final StringBuilder url;
  private final Map<String, String> headers = new HashMap<>();

  public static JsonHttpConfig fromUrl(String url) {
    return fromUrl(new StringBuilder(url));
  }

  public static JsonHttpConfig fromUrl(StringBuilder url) {
    return new JsonHttpConfig(url);
  }

  private JsonHttpConfig(final StringBuilder url) {
    this.url = url;
  }

  public JsonHttpConfig addHeader(String key, String value) {
    headers.put(key, value);
    return this;
  }

  public JsonHttpConfig addParameter(String key, String value) {
    if (url.indexOf("?") > 0) {
      url.append("&");
    } else {
      url.append("?");
    }
    url.append(key).append("=").append(value);
    return this;
  }

  public JsonHttpConfig setPagination(int page, int count) {
    if (page > 0 && count > 0) {
      addParameter("page", page + ";" + count);
    }
    return this;
  }

  public String getUrl() {
    return url.toString();
  }

  public Map<String, String> getHeaders() {
    return headers;
  }
}

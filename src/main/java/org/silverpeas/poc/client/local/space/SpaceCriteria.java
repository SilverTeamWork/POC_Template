package org.silverpeas.poc.client.local.space;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;

/**
 * @author miguel
 */
public class SpaceCriteria implements JsonGetCriteria {

  private String url = "spaces";

  public SpaceCriteria forUrl(String url) {
    if (!url.contains("spaces")) {
      new IllegalArgumentException("Bad URL: " + url + ". The URL must match a space");
    }
    this.url = url;
    return this;
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder targetedUrl = new StringBuilder(this.url);
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(targetedUrl);
    return jsonHttpConfig;
  }
}

package org.silverpeas.poc.client.local.space;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;

/**
 * @author miguel
 */
public class SpaceCriteria implements JsonGetCriteria {
  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder url = new StringBuilder("spaces");
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(url);
    return jsonHttpConfig;
  }
}

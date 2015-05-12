package org.silverpeas.poc.client.local.space;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;
import org.silverpeas.poc.api.util.StringUtil;

/**
 * @author miguel
 */
public class SpaceCriteria implements JsonGetCriteria {

  private String url = "spaces";
  private String spaceId;

  public static SpaceCriteria fromId(String id) {
    SpaceCriteria spaceCriteria = new SpaceCriteria();
    spaceCriteria.spaceId = id;
    return spaceCriteria;
  }

  public static SpaceCriteria fromUrl(String url) {
    if (!url.contains("spaces")) {
      throw new IllegalArgumentException("Bad URL: " + url + ". The URL must match a space");
    }
    SpaceCriteria spaceCriteria = new SpaceCriteria();
    spaceCriteria.url = url;
    return spaceCriteria;
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder targetedUrl = new StringBuilder(this.url);
    if (StringUtil.isDefined(spaceId)) {
      targetedUrl.append("/").append(spaceId);
    }

    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(targetedUrl);
    return jsonHttpConfig;
  }
}

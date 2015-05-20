package org.silverpeas.poc.client.local.application;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;
import org.silverpeas.poc.api.util.StringUtil;

/**
 * @author miguel
 */
public class ApplicationInstanceCriteria implements JsonGetCriteria {

  private String url = "components";
  private String instanceId;

  public static ApplicationInstanceCriteria fromId(String id) {
    ApplicationInstanceCriteria applicationInstanceCriteria = new ApplicationInstanceCriteria();
    applicationInstanceCriteria.instanceId = id;
    return applicationInstanceCriteria;
  }

  public static ApplicationInstanceCriteria fromUrl(String url) {
    if (!url.contains("components")) {
      throw new IllegalArgumentException(
          "Bad URL: " + url + ". The URL must match an application instance");
    }
    ApplicationInstanceCriteria applicationInstanceCriteria = new ApplicationInstanceCriteria();
    applicationInstanceCriteria.url = url;
    return applicationInstanceCriteria;
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder targetedUrl = new StringBuilder(this.url);
    if (StringUtil.isDefined(instanceId)) {
      targetedUrl.append("/").append(instanceId);
    }

    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(targetedUrl);
    return jsonHttpConfig;
  }
}

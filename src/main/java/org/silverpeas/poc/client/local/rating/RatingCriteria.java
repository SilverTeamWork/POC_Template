package org.silverpeas.poc.client.local.rating;

import org.jboss.errai.common.client.logging.util.StringFormat;
import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;
import org.silverpeas.poc.api.http.JsonPostCriteria;

/**
 * @author miguel
 */
public class RatingCriteria implements JsonGetCriteria, JsonPostCriteria {
  private static final String RATING_URI = "rating/%s/%s/%s";
  private String instanceId;
  private String type;
  private String contribId;

  public static RatingCriteria forPublication(String appInstanceId, String pubId) {
    RatingCriteria criteria = new RatingCriteria();
    criteria.instanceId = appInstanceId;
    criteria.type = "Publication";
    criteria.contribId = pubId;
    return criteria;
  }

  private RatingCriteria() {
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    String uri = StringFormat.format(RATING_URI, this.instanceId, this.type, this.contribId);
    return JsonHttpConfig.fromUrl(uri);
  }

  @Override
  public JsonHttpConfig configureJsonPostHttp() {
    return configureJsonGetHttp();
  }
}

package org.silverpeas.poc.client.local.comment;

import org.jboss.errai.common.client.logging.util.StringFormat;
import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;

/**
 * @author miguel
 */
public class CommentCriteria implements JsonGetCriteria {

  private static final String COMMENTS_URI = "comments/%s/%s/%s";
  private String instanceId;
  private String type;
  private String contribId;

  public static CommentCriteria forPublication(String appInstanceId, String pubId) {
    CommentCriteria criteria = new CommentCriteria();
    criteria.instanceId = appInstanceId;
    criteria.type = "Publication";
    criteria.contribId = pubId;
    return criteria;
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    String uri = StringFormat.format(COMMENTS_URI, this.instanceId, this.type, this.contribId);
    return JsonHttpConfig.fromUrl(uri);
  }

  private CommentCriteria() {

  }
}

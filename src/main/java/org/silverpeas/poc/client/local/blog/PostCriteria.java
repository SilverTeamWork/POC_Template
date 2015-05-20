package org.silverpeas.poc.client.local.blog;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;

/**
 * @author ebonnet
 */
public class PostCriteria implements JsonGetCriteria {

  private String blogId;
  private String postId;

  public static PostCriteria fromIds(String blogId, String postId) {
    PostCriteria postCriteria = new PostCriteria();
    postCriteria.blogId = blogId;
    postCriteria.postId = postId;
    return postCriteria;
  }

  /**
   * hidden constructor
   */
  protected PostCriteria() {
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder url = new StringBuilder("blogs/blog" + blogId + "/posts/" + postId);
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(url);
    return jsonHttpConfig;
  }

}

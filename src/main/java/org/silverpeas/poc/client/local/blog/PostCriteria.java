package org.silverpeas.poc.client.local.blog;

import org.silverpeas.poc.api.http.DeleteCriteria;
import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;
import org.silverpeas.poc.api.http.JsonPutCriteria;

/**
 * @author ebonnet
 */
public class PostCriteria implements JsonGetCriteria, JsonPutCriteria, DeleteCriteria {

  private String blogId;
  private String postId;
  private int page = 0;
  private int count = 0;

  public static PostCriteria fromIds(String blogId, String postId) {
    PostCriteria postCriteria = new PostCriteria();
    postCriteria.blogId = blogId;
    postCriteria.postId = postId;
    return postCriteria;
  }

  public static PostCriteria fromBlog(String blogId) {
    PostCriteria postCriteria = new PostCriteria();
    postCriteria.blogId = blogId;
    return postCriteria;
  }

  public static PostCriteria fromPost(Post post) {
    PostCriteria postCriteria = new PostCriteria();
    postCriteria.blogId = post.getAppInstanceId();
    postCriteria.postId = post.getId();
    return postCriteria;
  }

  public PostCriteria paginatedBy(int page, int count) {
    this.page = page;
    this.count = count;
    return this;
  }

  /**
   * hidden constructor
   */
  protected PostCriteria() {
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    return buildJsonHttpConfig();
  }

  @Override
  public JsonHttpConfig configureJsonPutHttp() {
    return buildJsonHttpConfig();
  }


  @Override
  public JsonHttpConfig configureDeleteHttp() {
    return buildJsonHttpConfig();
  }

  private JsonHttpConfig buildJsonHttpConfig() {
    StringBuilder url = new StringBuilder("blogs/");
    if (!blogId.startsWith("blog")) {
      url.append("blog");
    }
    url.append(blogId).append("/posts");
    if (postId != null) {
      url.append("/").append(postId);
    }
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(url);
    if (page > 0 && count > 0) {
      jsonHttpConfig.setPagination(page, count);
    }
    return jsonHttpConfig;
  }

}

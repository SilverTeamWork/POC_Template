package org.silverpeas.poc.client.local.blog;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;

/**
 * @author ebonnet
 */
public class PostCriteria implements JsonGetCriteria {

  private String blogId;
  private String postId;
  private String pagination;
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
    StringBuilder url = new StringBuilder("blogs/blog").append(blogId).append("/posts");
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

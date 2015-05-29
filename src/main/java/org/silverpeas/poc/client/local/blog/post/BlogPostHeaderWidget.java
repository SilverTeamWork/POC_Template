package org.silverpeas.poc.client.local.blog.post;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.widget.rating.RatingWidget;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated
public class BlogPostHeaderWidget extends Composite implements HasModel<Post> {

  private Post post;
  private boolean ratingLoadDone = false;

  @Inject
  @DataField("comment-count")
  private InlineLabel postNbComment;

  @Inject
  @DataField("post-rating")
  private RatingWidget ratingView;

  @Override
  public Post getModel() {
    return post;
  }

  @Override
  public void setModel(final Post post) {
    this.post = post;
    loadRating();
    this.postNbComment.setText(post.getNbComments());
  }

  private void loadRating() {
    if (!ratingLoadDone) {
      ratingLoadDone = true;
      // TODO load notation from rating services
      // URL is something like /services/rating/{blogId}/Publication/{postId}
      JsonHttp.onSuccess(new JsonResponse() {
        @Override
        public void process(final HttpResponse response) {
          Rating rating = response.parseJsonEntity();
          ratingView.setModel(rating);
        }
      }).get(RatingCriteria.forPublication(this.post.getAppInstanceId(), this.post.getId()));
    }
  }
}

package org.silverpeas.poc.client.local.blog.widget;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.application.ApplicationInstance;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.util.ContributionList;
import org.silverpeas.poc.client.local.widget.calendar.DatePickerWidget;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The blog date picket is an extension of {@link DatePickerWidget} and permits to centralize some
 * repeatable code.
 * @author Yohann Chastagnier
 */
@ApplicationScoped
public class BlogDatePickerWidget implements IsWidget {

  @Inject
  private DatePickerWidget datePickerWidget;

  private boolean alreadyLoaded = false;

  public void forceLoadFor(ApplicationInstance instance) {
    loadFor(instance, true);
  }


  public void loadFor(ApplicationInstance instance) {
    loadFor(instance, false);
  }

  private void loadFor(ApplicationInstance instance, boolean forceLoad) {
    if (!forceLoad && alreadyLoaded) {
      return;
    }
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        alreadyLoaded = true;
        final Set<Date> postDates = new HashSet<>();
        for (Post post : response.<ContributionList<Post>>parseJsonEntity().getEntities()) {
          postDates.add(post.getDateEvent());
        }
        datePickerWidget.setDatesToHighlight(postDates);
      }
    }).get(PostCriteria.fromBlog(instance.getId()));
  }

  public void displayOnlyCurrentPost(Post curPost) {
    final Set<Date> postDates = new HashSet<>();
    postDates.add(curPost.getDateEvent());
    datePickerWidget.setDatesToHighlight(postDates);
  }

  @Override
  public Widget asWidget() {
    return datePickerWidget;
  }
}

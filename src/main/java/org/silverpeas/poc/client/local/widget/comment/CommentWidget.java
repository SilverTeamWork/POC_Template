package org.silverpeas.poc.client.local.widget.comment;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.comment.Comment;

/**
 * @author miguel
 */
@Templated
public class CommentWidget extends Composite implements HasModel<Comment> {
  private Comment comment;

  @DataField
  private Element year = DOM.createSpan();
  @DataField
  private Element month = DOM.createSpan();
  @DataField
  private Element number = DOM.createSpan();
  @DataField
  private Element day = DOM.createSpan();

  @Override
  public Comment getModel() {
    return this.comment;
  }

  @Override
  public void setModel(Comment comment) {
    this.comment = comment;
  }

}

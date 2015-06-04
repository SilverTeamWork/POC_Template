package org.silverpeas.poc.client.local.widget.comment;

import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.comment.Comment;

/**
 * @author miguel
 */
@Templated
public class CommentWidget extends Composite implements HasModel<Comment> {
  private Comment comment;


  @Override
  public Comment getModel() {
    return this.comment;
  }

  @Override
  public void setModel(final Comment comment) {
    this.comment = comment;
  }

  public CommentWidget() {
    // Necessary for injection
  }

  /**
   * Create a CommentWidget with the specified ID.  The ID is required
   * because the CommentWidget needs a specific ID to connect to.
   * @param id - id of the element to create
   * @param options - JSONObject of any possible option, can be null for defaults
   */
  public CommentWidget(String id, JSONObject options) {
    super();
    Element divEle = DOM.createDiv();
    setElement(divEle);
    divEle.setId(id);

/*
    m_defaultOptions = options;
    if (m_defaultOptions == null) {
      m_defaultOptions = getOptions(0, 100, new int[]{0});
    }
*/
  }


  public static final native void initCommentWidget(String blogAppId, String postId,
      String authorId, String avatarUrl, boolean canBeUpdated, String userToken) /*-{
    $wnd.$.ajaxSetup({
      beforeSend : function(xhr) {
        xhr.setRequestHeader("X-Silverpeas-Session", userToken);
      }
    });
    $wnd.jQuery('#commentaires').comment({
      serverUrl : @org.silverpeas.poc.api.util.UrlManager::getSilverpeasRootUrl(*)(""),
      uri : @org.silverpeas.poc.api.util.UrlManager::getSilverpeasServiceUrl(*)("comments/" +
          blogAppId + "/Publication/" + postId),
      author : {
        avatar : avatarUrl,
        id : "'" + authorId + "'",
        anonymous : false
      },
      update : {
        activated : function(comment) {
          if (canBeUpdated || (comment.author.id === "'" + authorId + "'")) {
            return true;
          } else return false;
        }, icon : 'img/update.gif', altText : 'update'
      },
      deletion : {
        activated : function(comment) {
          if (canBeUpdated || (comment.author.id === "'" + authorId + "'")) {
            return true;
          } else return false;
        }, confirmation : @org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::COMMENT_DELETE_CONFIRMATION, ""), icon : 'img/delete.gif', altText : 'delete'
      },
      updateBox : {title : @org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::COMMENT_COMMENT, "")},
      editionBox : {
        title : @org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::COMMENT_ADD_LABEL, ""),
        ok : @org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::GML_VALIDATE, "")},
      validate : function(text) {
        if (text == null || $wnd.$.trim(text).length == 0) {
          alert(@org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::COMMENT_CHECK_FIELD_MANDATORY, ""));
        } else if (!$wnd.isValidTextArea(text)) {
          alert(@org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::COMMENT_CHECK_FIELD_LENGTH, ""));
        } else {
          return true;
        }
        return false;
      },
      mandatory : 'img/mandatoryField.gif',
      mandatoryText : @org.silverpeas.poc.api.util.I18n::format(*)(@org.silverpeas.poc.client.local.util.Messages::GML_REQUIRED_FIELD, "")
    });
    $wnd.jQuery('#commentaires').comment('edition', function() {
      return {
        author : {id : authorId},
        componentId : blogAppId,
        resourceId : postId,
        resourceType : 'Publication',
        indexed : true
      }
    });
    $wnd.jQuery('#commentaires').comment('list');
  }-*/;

}

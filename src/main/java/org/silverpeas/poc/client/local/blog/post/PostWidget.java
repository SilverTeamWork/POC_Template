package org.silverpeas.poc.client.local.blog.post;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.event.widget.ResourceSaveEvent;
import org.silverpeas.poc.api.event.widget.ResourceSaveHandler;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.api.widget.ResourceComposite;
import org.silverpeas.poc.client.local.blog.Post;
import org.silverpeas.poc.client.local.blog.PostCriteria;
import org.silverpeas.poc.client.local.user.CurrentUser;
import org.silverpeas.poc.client.local.util.CommonTranslations;
import org.silverpeas.poc.client.local.widget.NotificationWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.WysiwygEditor;
import org.silverpeas.poc.client.local.widget.comment.CommentWidget;
import org.silverpeas.poc.client.local.widget.contribution.PublicationFooterWidget;

import javax.inject.Inject;

/**
 * @author ebonnet
 */
@Templated("Post.html#blogPost")
public class PostWidget extends ResourceComposite<Post> {

  @Inject
  @DataField("header")
  private BlogPostHeaderWidget header;

  @DataField("post-content")
  private SilverpeasHtmlPanel content = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.DIV);

  @Inject
  @DataField
  private CommentWidget commentWidget;
  private boolean commentLoadDone = false;

  @Inject
  @DataField("footer")
  private PublicationFooterWidget footer;

  private TextBox titleInput = new TextBox();

  private boolean edition = false;
  private WysiwygEditor editor = null;

  public void initDefaultDisplay() {
    header.setVisible(false);
    footer.setVisible(false);
  }

  public void setEditionMode() {
    edition = true;
  }

  public boolean isInEdition() {
    return edition;
  }

  public PostWidget() {
    // Registering self handlers on the managed resource
    addResourceSaveHandler(new ResourceSaveHandler<Post>() {
      @Override
      public void onResourceSave(final ResourceSaveEvent<Post> event) {
        applyModel(event.getValue());
        final String successMessageKey;
        if (event.isPersistSave()) {
          successMessageKey = CommonTranslations.MESSAGE_CREATE_SUCCESS;
        } else {
          successMessageKey = CommonTranslations.MESSAGE_UPDATE_SUCCESS;
        }
        NotificationWidget
            .notifSuccess(I18n.format(successMessageKey, I18n.format(CommonTranslations.POST)));
      }
    });
    titleInput.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        if (isNewPost() && titleInput.getText().equals(getModel().getTitle())) {
          titleInput.setText("");
        }
      }
    });
    titleInput.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        if (isNewPost() && StringUtil.isNotDefined(titleInput.getText())) {
          titleInput.setText(getModel().getTitle());
        }
      }
    });
  }

  @Override
  public void setModel(final Post post) {
    super.setModel(post);
    switchEditionMode(isInEdition());
  }

  private boolean isNewPost() {
    return getModel() != null && StringUtil.isNotDefined(getModel().getId());
  }

  private void applyModel(final Post post) {
    super.setModel(post);
    composeViewFromModel();
  }

  private void composeViewFromModel() {
    if (!isNewPost()) {
      loadComments();
      header.setModel(getModel());
      footer.setModel(getModel());
      header.setVisible(true);
      footer.setVisible(true);
    } else {
      header.setVisible(false);
      footer.setVisible(false);
    }
  }

  public void switchEditionMode(boolean edition) {
    composeViewFromModel();
    this.edition = edition;
    content.clear();
    if (isInEdition()) {
      // Adding a title
      SilverpeasHtmlPanel titlePanel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.DIV);
      titlePanel.setStyleName("edition-form-line");
      SilverpeasHtmlPanel titleLabel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.SPAN);
      titleLabel.getElement().setInnerHTML(I18n.format(CommonTranslations.TITLE_LABEL));
      titlePanel.add(titleLabel);
      titleInput.setText(getModel().getTitle());
      titlePanel.add(titleInput);
      content.add(titlePanel);
      // Loading WYSIWYG editor
      loadWysiwygEditor();
    } else {
      if (editor != null) {
        editor.removeFromParent();
        editor = null;
      }
      HTML text = new HTML(getModel().getContent());
      content.add(text);
    }
  }

  private WysiwygEditor loadWysiwygEditor() {
    if (editor == null) {
      editor = new WysiwygEditor(getModel().getContent());
      editor.setTextSaveHandler(new WysiwygEditor.TextSaveHandler() {
        @Override
        public void onTextSave(final String text) {
          if (!getModel().getContent().equals(text) ||
              !getModel().getTitle().equals(titleInput.getText())) {
            getModel().setTitle(titleInput.getText());
            getModel().setContent(text);
            final boolean isCreation = isNewPost();
            JsonHttp jsonHttp = JsonHttp.onSuccess(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                Post savedPost = response.parseJsonEntity();
                ResourceSaveEvent.fire(PostWidget.this, savedPost, isCreation);
              }
            }).onError(new JsonResponse() {
              @Override
              public void process(final HttpResponse response) {
                NotificationWidget.notifError(I18n.format(CommonTranslations.MESSAGE_UPDATE_ERROR,
                    I18n.format(CommonTranslations.POST)));
              }
            }).withData(getModel());

            if (StringUtil.isNotDefined(getModel().getId())) {
              jsonHttp.post(PostCriteria.fromPost(getModel()));
            } else {
              jsonHttp.put(PostCriteria.fromPost(getModel()));
            }
          }
        }
      });
      content.add(editor);
    }
    return editor;
  }

  private void loadComments() {
    if (!commentLoadDone) {
      commentLoadDone = true;
      CommentWidget.initCommentWidget(getModel().getAppInstanceId(), getModel().getId(),
          CurrentUser.get().getId(), UrlManager.getSilverpeasUrl(CurrentUser.get().getAvatar()),
          true, CurrentUser.token());
    }
  }
}

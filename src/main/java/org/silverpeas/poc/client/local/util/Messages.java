package org.silverpeas.poc.client.local.util;

import com.google.gwt.i18n.client.LocalizableResource;
import org.jboss.errai.ui.shared.api.annotations.TranslationKey;

@LocalizableResource.DefaultLocale("fr")
public interface Messages extends com.google.gwt.i18n.client.Messages {

  /**
   * ERRAI i18n framework
   */

  @TranslationKey(defaultValue = "Current connected user: ")
  String CURRENT_CONNECTED_USER_LOG = "user.current.connected.log";

  @TranslationKey(defaultValue = "Your session has expired. <br> You will be redirected to the " +
      "login page.")
  String DISCONNECTION_TOKEN_EXPIRED = "disconnection.token.expired";

  @TranslationKey(defaultValue = "Are you sure about disconnecting yourself ?")
  String DISCONNECTION_CONFIRM = "disconnection.confirm";

  @TranslationKey(defaultValue = "Published")
  String PUBLISH_DATE_LABEL = "publish.date";

  @TranslationKey(defaultValue = "by")
  String COMMON_BY = "common.by";

  @TranslationKey(defaultValue = "Updated")
  String UPDATE_DATE_LABEL = "update.date";

  @TranslationKey(defaultValue = "MM/dd/yyyy")
  String DATE_FORMAT = "date.format";

  @TranslationKey(defaultValue = "MM/dd/yyyy hh:mm")
  String DATETIME_FORMAT = "datetime.format";

  @TranslationKey(defaultValue = "Homepage")
  String SPACE_HOMEPAGE_LABEL = "space.homepage.label";


  @TranslationKey(defaultValue = "Comment")
  String COMMENT_COMMENT = "comment.comment";

  @TranslationKey(defaultValue = "Are you sure you want to delete this comment?")
  String COMMENT_DELETE_CONFIRMATION = "comment.delete.confirmation";

  @TranslationKey(defaultValue = "Add comment")
  String COMMENT_ADD_LABEL = "comment.add.label";

  @TranslationKey(defaultValue = "You must filled all mandatory fields.")
  String COMMENT_CHECK_FIELD_MANDATORY = "comment.check.field.mandatory";

  @TranslationKey(defaultValue = "A comment is limited to 2000 characters.")
  String COMMENT_CHECK_FIELD_LENGTH = "comment.check.fieldlength";

  @TranslationKey(defaultValue = "Required")
  String GML_REQUIRED_FIELD = "GML.required.field";

  @TranslationKey(defaultValue = "Validate")
  String GML_VALIDATE = "GML.validate";


  /**
   * GWT i18n framework
   */

  @DefaultMessage("Please waiting...")
  String waiting();

  @DefaultMessage("Ok")
  String ok();

  @DefaultMessage("Yes")
  String yes();

  @DefaultMessage("No")
  String no();

  @DefaultMessage("Error")
  String error();

  @DefaultMessage("Warning")
  String warning();
}

package org.silverpeas.poc.client.local.util;

import org.jboss.errai.ui.shared.api.annotations.TranslationKey;

/**
 * Common translations shareable by all widgets.
 * @author miguel
 */
public interface CommonTranslations {

  @TranslationKey(defaultValue = "")
  String PUBLICATION_DATE = "publication.publishedIn";
  @TranslationKey(defaultValue = "")
  String PUBLICATION_AUTHOR = "publication.publishedBy";
  @TranslationKey(defaultValue = "")
  String MODIFICATION_DATE = "publication.modifiedIn";
  @TranslationKey(defaultValue = "")
  String MODIFICATION_AUTHOR = "publication.modifiedBy";
  @TranslationKey(defaultValue = "")
  String BACK_TO_PREVIOUS_PAGE = "page.previous.back";
  @TranslationKey(defaultValue = "")
  String SUBSCRIBE_LABEL = "subscribe.label";
  @TranslationKey(defaultValue = "")
  String APPLICATION_RESPONSIBLE_LABEL = "application.responsible.label";
  @TranslationKey(defaultValue = "")
  String ACTION_CREATION = "action.create";
  @TranslationKey(defaultValue = "")
  String ACTION_MODIFICATION = "action.modify";
  @TranslationKey(defaultValue = "")
  String ACTION_DELETION = "action.delete";
  @TranslationKey(defaultValue = "")
  String ACTION_OK = "action.ok";
  @TranslationKey(defaultValue = "")
  String ACTION_CANCEL = "action.cancel";
  @TranslationKey(defaultValue = "")
  String MESSAGE_CONFIRMATION_DELETION = "message.confirmation.deletion";
  @TranslationKey(defaultValue = "")
  String MESSAGE_CREATE_SUCCESS = "message.contribution.create.success";
  @TranslationKey(defaultValue = "")
  String MESSAGE_UPDATE_SUCCESS = "message.contribution.update.success";
  @TranslationKey(defaultValue = "")
  String MESSAGE_UPDATE_ERROR = "message.contribution.update.error";
  @TranslationKey(defaultValue = "")
  String TITLE_LABEL = "title.label";
  @TranslationKey(defaultValue = "")
  String POST = "post";
  @TranslationKey(defaultValue = "")
  String POST_DATE_EVENT = "post.date.event";

}

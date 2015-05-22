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
  String ACTION_MODIFICATION = "action.modify";
  @TranslationKey(defaultValue = "")
  String ACTION_DELETION = "action.delete";
}

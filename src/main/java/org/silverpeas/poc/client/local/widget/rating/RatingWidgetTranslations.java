package org.silverpeas.poc.client.local.widget.rating;

import org.jboss.errai.ui.shared.api.annotations.TranslationKey;

/**
 * @author miguel
 */
public interface RatingWidgetTranslations {
  @TranslationKey(defaultValue = "")
  String LABELS = "RatingWidget.labels";
  @TranslationKey(defaultValue = "")
  String VOTES = "RatingWidget.votes";
  @TranslationKey(defaultValue = "")
  String VOTE_DELETION = "RatingWidget.vote.delete";
  @TranslationKey(defaultValue = "")
  String VOTE_DONE = "RatingWidget.vote.ok";
  @TranslationKey(defaultValue = "")
  String VOTE_DELETION_DONE = "RatingWidget.vote.delete.ok";
  @TranslationKey(defaultValue = "")
  String YOUR_VOTE = "RatingWidget.vote.yours";
  @TranslationKey(defaultValue = "")
  String NO_VOTE = "RatingWidget.vote.yours.none";
}

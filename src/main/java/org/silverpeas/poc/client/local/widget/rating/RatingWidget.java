package org.silverpeas.poc.client.local.widget.rating;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.widget.rating.event.RatingChange;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author miguel
 */
public class RatingWidget extends Composite
    implements HasModel<Rating>, ClickHandler, MouseOverHandler, MouseOutHandler {

  private static final int NOTE_COUNT = 5;
  private static final String RATING_STYLE = "silverpeas-rating";
  private static final String DEFAULT_RATING_NOTE_STYLE = "silverpeas-rating-note";
  private static final String UNSELECTED_RATING_NOTE_STYLE = "silverpeas-rating-note-unselected";
  private static final String SELECTED_RATING_NOTE_STYLE = "silverpeas-rating-note-selected";
  private static final String HALF_SELECTED_RATING_NOTE_STYLE = "silverpeas-rating-note-half";
  private static final String HOVER_RATING_NOTE_STYLE = "silverpeas-rating-note-hover";

  private Rating rating = null;
  private boolean readonly = false;
  private int hoverRatingNote = -1;
  private RatingWidgetResources resources = GWT.create(RatingWidgetResources.class);

  @Inject
  private Event<RatingChange> ratingChangeEvent;

  @Inject
  private TranslationService translation;

  private FlowPanel panel = new FlowPanel();
  private Image clearImage = new Image();

  public RatingWidget readonly() {
    this.readonly = true;
    panel.remove(clearImage);
    return this;
  }

  @Override
  public Rating getModel() {
    return this.rating;
  }

  @Override
  public void setModel(final Rating rating) {
    this.rating = rating;
    updateRatingNoteImages();
  }

  @AfterInitialization
  private void create() {
    initWidget(panel);
    addDomHandler(this, MouseOutEvent.getType());
    panel.setStyleName(RATING_STYLE);

    String[] labels = translation.format(RatingWidgetTranslations.LABELS).split(",");
    for (int note = 0; note < NOTE_COUNT; note++) {
      Image image = new Image();
      image.setTitle(labels[note].trim());
      image.setAltText(String.valueOf(note));
      image.addClickHandler(this);
      image.addMouseOverHandler(this);
      image.setResource(resources.noteUnselected());
      image.setStyleName(DEFAULT_RATING_NOTE_STYLE + " " + UNSELECTED_RATING_NOTE_STYLE);
      panel.add(image);
    }

    if (!readonly) {
      panel.getElement().getStyle().setCursor(Style.Cursor.POINTER);
      clearImage.setResource(resources.clearRating());
      clearImage.setTitle(translation.format(RatingWidgetTranslations.VOTE_DELETION));
      clearImage.addClickHandler(this);
      clearImage.addMouseOverHandler(this);
      panel.add(clearImage);
    }
  }

  private void updateRatingNoteImages() {
    for (int note = 0; note < NOTE_COUNT; note++) {
      Image image = (Image) panel.getWidget(note);
      ImageResource resource;
      String style = DEFAULT_RATING_NOTE_STYLE + " ";
      if (rating != null) {
        double ceil = Math.ceil(rating.getAverageValue());
        if (hoverRatingNote < 0) {
          if (note <= rating.getAverageValue()) {
            if (ceil != rating.getAverageValue() && note == (ceil - 1)) {
              resource = resources.noteHalfSelected();
              style += HALF_SELECTED_RATING_NOTE_STYLE;
            } else {
              resource = resources.noteSelected();
              style += SELECTED_RATING_NOTE_STYLE;
            }
          } else {
            resource = resources.noteUnselected();
            style += UNSELECTED_RATING_NOTE_STYLE;
          }
        } else {
          if (note <= hoverRatingNote) {
            resource = resources.noteHover();
            style += HOVER_RATING_NOTE_STYLE;
          } else {
            resource = resources.noteUnselected();
            style += UNSELECTED_RATING_NOTE_STYLE;
          }
        }
      } else {
        resource = resources.noteUnselected();
        style += UNSELECTED_RATING_NOTE_STYLE;
      }
      image.setResource(resource);
      image.setStyleName(style);
    }
  }

  /**
   * Called when a native click event is fired.
   * @param event the {@link ClickEvent} that was fired
   */
  @Override
  public void onClick(final ClickEvent event) {
    if (!readonly) {
      Image image = (Image) event.getSource();
      unsetHoverRatingNote();
      if (image.equals(clearImage)) {
        setRatingNote(0);
      } else {
        setRatingNote(Integer.parseInt(image.getAltText()));
      }
    }
  }

  private void setRatingNote(final int note) {
    this.rating.setUserRating(note);
    ratingChangeEvent.fire(new RatingChange(rating));
  }

  /**
   * Called when MouseOutEvent is fired.
   * @param event the {@link MouseOutEvent} that was fired
   */
  @Override
  public void onMouseOut(final MouseOutEvent event) {
    unsetHoverRatingNote();
    updateRatingNoteImages();
  }

  /**
   * Called when MouseOverEvent is fired.
   * @param event the {@link MouseOverEvent} that was fired
   */
  @Override
  public void onMouseOver(final MouseOverEvent event) {
    if (!readonly) {
      Image image = (Image) event.getSource();
      hoverRatingNote = Integer.parseInt(image.getAltText());
      updateRatingNoteImages();
    }
  }

  private void unsetHoverRatingNote() {
    hoverRatingNote = -1;
  }
}

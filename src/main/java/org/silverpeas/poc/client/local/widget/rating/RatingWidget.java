package org.silverpeas.poc.client.local.widget.rating;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.HasModel;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.client.local.rating.Rating;
import org.silverpeas.poc.client.local.rating.RatingCriteria;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.rating.event.RatingChange;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.math.BigDecimal;

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
  private int hoverRatingNote = 0;
  private RatingWidgetResources resources = GWT.create(RatingWidgetResources.class);

  @Inject
  private Event<RatingChange> ratingChangeEvent;

  private SilverpeasHtmlPanel panel = new SilverpeasHtmlPanel(SilverpeasHtmlPanel.TYPE.SPAN);
  private Image clearImage = new Image();
  private HandlerRegistration handlerRegistration;

  public RatingWidget readonly() {
    this.readonly = true;
    panel.remove(clearImage);
    handlerRegistration.removeHandler();
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
    handlerRegistration = addDomHandler(this, MouseOutEvent.getType());
    panel.setStyleName(RATING_STYLE);

    String[] labels = I18n.format(RatingWidgetTranslations.LABELS).split(",");
    for (int note = 1; note <= NOTE_COUNT; note++) {
      Image image = new Image();
      image.setTitle(labels[note - 1].trim());
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
      clearImage.setTitle(I18n.format(RatingWidgetTranslations.VOTE_DELETION));
      clearImage.addClickHandler(this);
      clearImage.addMouseOverHandler(this);
      panel.add(clearImage);
    }
  }

  private void updateRatingNoteImages() {
    double ratingNote = readonly ? rating.getAverageValue() : (double) rating.getUserRating();
    if (Math.ceil(ratingNote) != ratingNote) {
      ratingNote += 1;
    }
    double ceil = Math.ceil(ratingNote);
    for (int note = 1; note <= NOTE_COUNT; note++) {
      Image image = (Image) panel.getWidget(note - 1);
      ImageResource resource;
      String style = DEFAULT_RATING_NOTE_STYLE + " ";
      if (rating != null) {
        if (hoverRatingNote == 0) {
          if (note <= ratingNote) {
            if (ceil != ratingNote && note == (ceil - 1)) {
              double rounded =
                  new BigDecimal(String.valueOf(ratingNote)).setScale(0, BigDecimal.ROUND_DOWN)
                      .doubleValue();
              if ((ratingNote - rounded) > 0.25) {
                resource = resources.noteHalfSelected();
                style += HALF_SELECTED_RATING_NOTE_STYLE;
              } else {
                resource = resources.noteUnselected();
                style += UNSELECTED_RATING_NOTE_STYLE;
              }
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
    event.stopPropagation();
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
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        Rating newRating = response.parseJsonEntity();
        setModel(newRating);
        ratingChangeEvent.fire(new RatingChange(rating));
      }
    }).withValue(note).post(
        RatingCriteria.forPublication(rating.getAppInstanceId(), rating.getNotedContributionId()));
  }

  /**
   * Called when MouseOutEvent is fired.
   * @param event the {@link MouseOutEvent} that was fired
   */
  @Override
  public void onMouseOut(final MouseOutEvent event) {
    event.stopPropagation();
    if (!readonly) {
      unsetHoverRatingNote();
      updateRatingNoteImages();
    }
  }

  /**
   * Called when MouseOverEvent is fired.
   * @param event the {@link MouseOverEvent} that was fired
   */
  @Override
  public void onMouseOver(final MouseOverEvent event) {
    event.stopPropagation();
    if (!readonly) {
      Image image = (Image) event.getSource();
      hoverRatingNote = Integer.parseInt(image.getAltText());
      updateRatingNoteImages();
    }
  }

  private void unsetHoverRatingNote() {
    hoverRatingNote = 0;
  }
}
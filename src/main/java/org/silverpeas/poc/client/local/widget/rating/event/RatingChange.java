package org.silverpeas.poc.client.local.widget.rating.event;

import org.silverpeas.poc.client.local.rating.Rating;

/**
 * @author miguel
 */
public class RatingChange {

  public RatingChange(final Rating newRating) {
    this.value = newRating;
  }

  private Rating value;

}

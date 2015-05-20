package org.silverpeas.poc.client.local.rating;

import org.silverpeas.poc.client.local.util.Contribution;

/**
 * @author miguel
 */
public class Rating extends Contribution {
  protected Rating() {
  }

  public final native int getRatingCount() /*-{
    return this.numberOfRaterRatings;
  }-*/;

  public final native double getAverageValue() /*-{
    return this.ratingAverage;
  }-*/;

  public final native int getUserRating() /*-{
    return this.raterRatingValue;
  }-*/;

  public final native boolean isRatingDone() /*-{
    return this.isRatingDone;
  }-*/;
}

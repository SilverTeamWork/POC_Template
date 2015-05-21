package org.silverpeas.poc.client.local.rating;

import org.silverpeas.poc.client.local.util.Contribution;

/**
 * @author miguel
 */
public class Rating extends Contribution {
  protected Rating() {
  }

  public final native String getNotedContributionId() /*-{
    return this.contributionId;
  }-*/;

  public final native String getNotedContributionType() /*-{
    return this.contributionType;
  }-*/;

  public final native int getRatingCount() /*-{
    return this.numberOfRaterRatings;
  }-*/;

  public final native double getAverageValue() /*-{
    return this.ratingAverage;
  }-*/;

  public final native int getUserRating() /*-{
    return this.raterRatingValue;
  }-*/;

  public final native boolean isUserRatingDone() /*-{
    return this.isRatingDone;
  }-*/;

  public final native void setUserRating(int note) /*-{
    this.userRatingValue = note;
  }-*/;
}

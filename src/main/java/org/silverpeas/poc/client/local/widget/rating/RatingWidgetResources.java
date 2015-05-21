package org.silverpeas.poc.client.local.widget.rating;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author miguel
 */
public interface RatingWidgetResources extends ClientBundle {

  @Source("star.png")
  ImageResource noteSelected();

  @Source("star_half.png")
  ImageResource noteHalfSelected();

  @Source("star_empty.png")
  ImageResource noteUnselected();

  @Source("red_star.png")
  ImageResource noteHover();

  @Source("star_delete2.png")
  ImageResource clearRating();
}

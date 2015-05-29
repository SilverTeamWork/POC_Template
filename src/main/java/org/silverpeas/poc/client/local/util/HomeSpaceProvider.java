package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JsonUtils;
import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.UrlManager;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceContent;
import org.turbogwt.core.collections.JsArrayList;

import static org.silverpeas.poc.client.local.util.Messages.SPACE_HOMEPAGE_LABEL;

/**
 * @author miguel
 */
public class HomeSpaceProvider {

  private static Space homeSpace = JsonUtils.safeEval("{\"label\":\"Toto sweet home\"," +
      "\"spacesURI\":\"" + UrlManager.getSilverpeasServiceUrl("spaces/1/spaces") + "\"," +
      "\"componentsURI\":\"" + UrlManager.getSilverpeasServiceUrl("spaces/1/spaces") + "\"," +
      "\"level\":0," +
      "\"rank\": 0," +
      "\"id\":\"-1\"," +
      "\"home\": true}");

  static {
    homeSpace.setLabel(I18n.format(SPACE_HOMEPAGE_LABEL));
    homeSpace.setParent(null);
    homeSpace.setContent(new JsArrayList<SpaceContent>());
  }

  public static Space getHomeSpace() {
    return homeSpace;
  }
}

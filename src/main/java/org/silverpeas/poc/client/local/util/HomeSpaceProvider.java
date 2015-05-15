package org.silverpeas.poc.client.local.util;

import com.google.gwt.core.client.JsonUtils;
import org.silverpeas.poc.client.local.space.Space;

/**
 * @author miguel
 */
public class HomeSpaceProvider {

  private static Space homeSpace = JsonUtils.safeEval("{\"label\":\"Toto sweet home\"," +
      "\"spacesURI\":\"http://localhost:8000/silverpeas/services/spaces/1/spaces\"," +
      "\"componentsURI\":\"http://localhost:8000/silverpeas/services/spaces/1/spaces\"," +
      "\"level\":0," +
      "\"rank\": 0," +
      "\"id\":\"-1\"," +
      "\"home\": true," +
      " \"content\":[]}");

  public static Space getHomeSpace() {
    return homeSpace;
  }
}

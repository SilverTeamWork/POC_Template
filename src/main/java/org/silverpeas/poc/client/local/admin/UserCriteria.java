package org.silverpeas.poc.client.local.admin;

import org.silverpeas.poc.api.http.JsonGetCriteria;
import org.silverpeas.poc.api.http.JsonHttpConfig;
import org.silverpeas.poc.api.http.JsonPostCriteria;
import org.silverpeas.poc.api.util.Base64Util;

import static org.silverpeas.poc.api.util.StringUtil.isDefined;

/**
 * @author Yohann Chastagnier
 */
public class UserCriteria implements JsonGetCriteria, JsonPostCriteria {

  private String credentials = null;

  public static UserCriteria fromCredentials(String login, String password, String domainId) {
    UserCriteria userCriteria = new UserCriteria();
    userCriteria.credentials = login + "@domain" + domainId + ":" + password;
    return userCriteria;
  }

  public static UserCriteria init() {
    return new UserCriteria();
  }

  /**
   * Hidden constructor.
   */
  private UserCriteria() {
  }

  @Override
  public JsonHttpConfig configureJsonGetHttp() {
    StringBuilder url = new StringBuilder("profile/users");
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(url);
    return jsonHttpConfig;
  }

  @Override
  public JsonHttpConfig configureJsonPostHttp() {
    StringBuilder url = new StringBuilder();
    JsonHttpConfig jsonHttpConfig = JsonHttpConfig.fromUrl(url);
    if (isDefined(credentials)) {
      url.append("authentication");
      jsonHttpConfig.addHeader("Authorization", "Basic " + Base64Util.encode(credentials));
    }
    return jsonHttpConfig;
  }
}

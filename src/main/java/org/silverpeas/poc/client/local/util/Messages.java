package org.silverpeas.poc.client.local.util;

import com.google.gwt.i18n.client.LocalizableResource;
import org.jboss.errai.ui.shared.api.annotations.TranslationKey;

@LocalizableResource.DefaultLocale("fr")
public interface Messages extends com.google.gwt.i18n.client.Messages {

  /**
   * ERRAI i18n framework
   */

  @TranslationKey(defaultValue = "Current connected user: ")
  String CURRENT_CONNECTED_USER_LOG = "user.current.connected.log";

  @TranslationKey(defaultValue = "Your session has expired. <br> You will be redirected to the " +
      "login page.")
  String DISCONNECTION_TOKEN_EXPIRED = "disconnection.token.expired";

  @TranslationKey(defaultValue = "Are you sure about disconnecting yourself ?")
  String DISCONNECTION_CONFIRM = "disconnection.confirm";

  @TranslationKey(defaultValue = "Published")
  String PUBLISH_DATE_LABEL = "publish.date";

  @TranslationKey(defaultValue = "by")
  String COMMON_BY = "common.by";

  @TranslationKey(defaultValue = "Updated")
  String UPDATE_DATE_LABEL = "update.date";

  @TranslationKey(defaultValue = "MM/dd/yyyy")
  String DATE_FORMAT = "date.format";

  @TranslationKey(defaultValue = "MM/dd/yyyy hh:mm")
  String DATETIME_FORMAT = "datetime.format";

  @TranslationKey(defaultValue = "Subscribe")
  public static final String SUBSCRIBE_LABEL = "subscribe.label";

  @TranslationKey(defaultValue = "Application responsibles")
  public static final String APPLICATION_RESPONSIBLE_LABEL = "application.responsible.label";

  /**
   * GWT i18n framework
   */

  @DefaultMessage("Please waiting...")
  String waiting();

  @DefaultMessage("Ok")
  String ok();

  @DefaultMessage("Yes")
  String yes();

  @DefaultMessage("No")
  String no();
}

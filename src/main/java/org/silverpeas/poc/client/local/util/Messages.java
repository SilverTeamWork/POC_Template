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

  @TranslationKey(defaultValue = "Are you sure about disconnecting yourself ?")
  String DISCONNECTION_CONFIRM = "disconnection.confirm";

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

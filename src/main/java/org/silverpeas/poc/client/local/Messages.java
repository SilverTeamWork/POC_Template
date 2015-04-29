package org.silverpeas.poc.client.local;

import com.google.gwt.i18n.client.LocalizableResource;
import org.jboss.errai.ui.shared.api.annotations.TranslationKey;

@LocalizableResource.DefaultLocale("fr")
public interface Messages extends com.google.gwt.i18n.client.Messages {

  @TranslationKey(defaultValue = "Are you sure about disconnecting yourself ?")
  String DISCONNECTION_CONFIRM = "disconnection.confirm";

  @DefaultMessage("Please waiting...")
  String waiting();

  @DefaultMessage("Ok")
  String ok();

  @DefaultMessage("Yes")
  String yes();

  @DefaultMessage("No")
  String no();

}

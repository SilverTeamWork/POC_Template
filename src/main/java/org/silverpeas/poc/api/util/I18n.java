package org.silverpeas.poc.api.util;

import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.silverpeas.poc.api.ioc.BeanManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class permits to use easily the ERRAI mechanism provided for i18n.
 * @author Yohann Chastagnier
 */
@Singleton
public class I18n {

  @Inject
  private TranslationService translationService;

  private static I18n get() {
    return BeanManager.getInstanceOf(I18n.class);
  }

  /**
   * Look up a message in the i18n resource message bundle by key, then format the message with the
   * given arguments and return the result.
   */
  public static String format(String key, Object... args) {
    return get().translationService.format(key, args);
  }
}

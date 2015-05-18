package org.silverpeas.poc.client.local.application;

import org.jboss.errai.common.client.api.annotations.LocalEvent;

/**
 * @author miguel
 */
@LocalEvent
public class ApplicationInstanceSelection {

  private ApplicationInstance instance;

  public ApplicationInstanceSelection(final ApplicationInstance selectedInstance) {
    this.instance = selectedInstance;
  }

  public ApplicationInstance getSelectedInstance() {
    return instance;
  }
}

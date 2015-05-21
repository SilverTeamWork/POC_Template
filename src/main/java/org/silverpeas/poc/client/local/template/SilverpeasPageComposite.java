package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Panel;

/**
 * All pages must extend this class.
 * @author Yohann Chastagnier
 */
public abstract class SilverpeasPageComposite extends SilverpeasComposite {

  @Override
  public Panel getContentPanel() {
    return getCompositeParent().getContentPanel();
  }
}

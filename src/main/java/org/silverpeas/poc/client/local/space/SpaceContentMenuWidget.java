package org.silverpeas.poc.client.local.space;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.silverpeas.poc.api.ioc.BeanManager;

/**
 * @author Yohann Chastagnier
 */
public class SpaceContentMenuWidget implements IsWidget {

  @Override
  public Widget asWidget() {
    return getSpaceContentMenuComposite();
  }

  private static SpaceContentMenuComposite getSpaceContentMenuComposite() {
    return BeanManager.getInstanceOf(SpaceContentMenuComposite.class);
  }
}

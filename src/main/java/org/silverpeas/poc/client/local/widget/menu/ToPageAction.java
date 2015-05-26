package org.silverpeas.poc.client.local.widget.menu;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * This menu handles a navigation to an other page.
 * @author Yohann Chastagnier
 */
public class ToPageAction extends MenuAction<ToPageAction> {

  private String pageName;
  private Multimap<String, String> parameters = ArrayListMultimap.create();

  /**
   * @see MenuAction#MenuAction(boolean)
   */
  public ToPageAction(final boolean mustBeVerified) {
    super(mustBeVerified);
  }

  /**
   * The page name to go to when the menu item is clicked.
   * @param pageName the page name to go to.
   * @return the current instance.
   */
  public ToPageAction toPageName(final String pageName) {
    this.pageName = pageName;
    return this;
  }

  /**
   * The page class to go to when the menu item is clicked.
   * @param pageClass the page class to go to.
   * @return the current instance.
   */
  public ToPageAction toPage(final Class pageClass) {
    return toPageName(pageClass.getSimpleName());
  }

  String getPageName() {
    return pageName;
  }

  /**
   * Gets the parameters in order to add some.
   * @return the parameters.
   */
  public Multimap<String, String> getParameters() {
    return parameters;
  }
}

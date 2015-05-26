package org.silverpeas.poc.client.local.widget.menu;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Yohann Chastagnier
 */
public class ToPageAction extends MenuAction {

  private final String pageName;
  private Multimap<String, String> parameters = ArrayListMultimap.create();

  public static final ToPageAction DUMMY = new ToPageAction(TYPE.UNKNOWN, "");

  public ToPageAction(final TYPE type, final String pageName) {
    super(type);
    this.pageName = pageName;
  }

  public String getPageName() {
    return pageName;
  }

  public Multimap<String, String> getParameters() {
    return parameters;
  }

  @Override
  protected ToPageAction getDummy() {
    return DUMMY;
  }
}

package org.silverpeas.poc.client.local.breadcrumb;

import com.google.common.collect.Multimap;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.template.SilverpeasApplicationPageComposite;

/**
 * @author miguel
 */
public class BreadCrumbInternalAppPageItem<P extends SilverpeasApplicationPageComposite>
    extends BreadCrumbItem {

  private final P page;
  private final Multimap<String, String> pageState;

  public BreadCrumbInternalAppPageItem(final P page, Multimap<String, String> pageState) {
    this.page = page;
    this.pageState = pageState;
  }

  @Override
  protected String getTargetPageName() {
    return StringUtil.capitalize(page.getClass().getSimpleName());
  }

  @Override
  public String getLabel() {
    return page.getTitle();
  }

  @Override
  public String getStyleClass() {
    return "page-item-breadcrumb";
  }

  @Override
  protected Multimap<String, String> getTransitionParameters() {
    return pageState;
  }

  @Override
  protected void fireEvent() {
  }
}

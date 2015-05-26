package org.silverpeas.poc.client.local.widget.menu;

import org.silverpeas.poc.api.util.I18n;
import org.silverpeas.poc.api.util.StringUtil;
import org.silverpeas.poc.client.local.util.CommonTranslations;

/**
 * @author Yohann Chastagnier
 */
public abstract class MenuAction {

  private MenuAnchor menuAnchor;

  public enum TYPE {
    UNKNOWN(""),
    FREE(""),
    CREATE(CommonTranslations.ACTION_CREATION),
    MODIFY(CommonTranslations.ACTION_MODIFICATION),
    DELETE(CommonTranslations.ACTION_DELETION);

    private final String bundleKey;

    TYPE(final String bundleKey) {
      this.bundleKey = bundleKey;
    }
  }

  private final TYPE type;
  private String label;

  public MenuAction(final TYPE type) {
    this.type = type;
    this.label = StringUtil.isDefined(type.bundleKey) ? I18n.format(type.bundleKey) : "";
  }

  public String getLabel() {
    return label;
  }

  public MenuAction setLabel(String label) {
    this.label = label;
    if (menuAnchor != null) {
      menuAnchor.setText(label);
    }
    return this;
  }

  public void setMenuAnchor(final MenuAnchor menuAnchor) {
    this.menuAnchor = menuAnchor;
  }

  public boolean isDummy() {
    return this != getDummy();
  }

  protected abstract MenuAction getDummy();
}

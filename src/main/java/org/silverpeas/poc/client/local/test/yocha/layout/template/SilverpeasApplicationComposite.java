package org.silverpeas.poc.client.local.test.yocha.layout.template;

import com.google.gwt.user.client.ui.Panel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated("ApplicationTemplate.html")
public class SilverpeasApplicationComposite extends SilverpeasComposite {

  @Inject
  private SilverpeasSpaceComposite spaceTemplateComposite;

  @Inject
  @DataField
  private SilverpeasHtmlPanel appContent;

  @Override
  public void onPageShowing() {
  }

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return spaceTemplateComposite;
  }

  @Override
  public Panel getContentPanel() {
    return appContent;
  }
}

package org.silverpeas.poc.client.local.test.yocha.layout.hierarchy;

import com.google.gwt.user.client.ui.Label;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Templated("template.html")
public class Middle extends Parent {

  @Inject
  @DataField("parent")
  private Label parentLabel;

  @PageShowing
  private void setup() {
    parentLabel.setText("Parent label performed");
  }
}

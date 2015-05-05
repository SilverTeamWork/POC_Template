package org.silverpeas.poc.client.local.test.yocha.layout;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.inject.Inject;

@Templated("layout-master.html#layout-master")
public class LayoutMaster extends Composite {

  @Inject
  @DataField("header-part")
  private Header header;
}

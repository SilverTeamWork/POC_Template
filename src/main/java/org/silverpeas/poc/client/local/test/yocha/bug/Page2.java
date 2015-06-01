package org.silverpeas.poc.client.local.test.yocha.bug;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
@Page
@Templated
//@ApplicationScoped
public class Page2 extends Composite {

  @Inject
  @DataField
  private Child child;
}

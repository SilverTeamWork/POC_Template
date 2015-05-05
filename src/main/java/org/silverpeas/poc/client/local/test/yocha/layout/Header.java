package org.silverpeas.poc.client.local.test.yocha.layout;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;

@ApplicationScoped
@Templated("home.html#header-container")
public class Header extends Composite {

  public int i = 0;
}

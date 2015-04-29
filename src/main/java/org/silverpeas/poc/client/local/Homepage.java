package org.silverpeas.poc.client.local;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.layout.LayoutMaster;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.silverpeas.poc.client.local.layout.LayoutUtil.LAYOUT_MASTER;

@Page
@Templated(LAYOUT_MASTER)
public class Homepage extends LayoutMaster {

//  @PageShown
//  private void init() {
//    RootPanel.get().add(this);
//  }
}

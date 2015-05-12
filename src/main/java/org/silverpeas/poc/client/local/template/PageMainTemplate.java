package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.header.HeaderWidget;

import javax.inject.Inject;

import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate.MAIN_HTML_TEMPLATE;
import static org.silverpeas.poc.client.local.template.SilverpeasMainTemplate
    .MAIN_HTML_TEMPLATE_CONTENT_CONTAINER;

/**
 * This template defines the layout
 * @author miguel
 */
@Templated(MAIN_HTML_TEMPLATE)
public class PageMainTemplate extends Composite {

  @Inject
  @DataField("header-container")
  private HeaderWidget header;

  @Inject
  @DataField(MAIN_HTML_TEMPLATE_CONTENT_CONTAINER)
  private SpaceMainPanel spaceMainPanel;

}

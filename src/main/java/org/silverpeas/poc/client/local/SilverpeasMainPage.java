package org.silverpeas.poc.client.local;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.Composite;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.http.HttpResponse;
import org.silverpeas.poc.api.http.JsonHttp;
import org.silverpeas.poc.api.http.JsonResponse;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceCriteria;
import org.silverpeas.poc.client.local.space.SpaceWidget;
import org.turbogwt.core.collections.JsArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author miguel
 */
@Page(path = "home")
@Templated("SilverpeasMainPage.html")
@Bundle("messages.json")
public class SilverpeasMainPage extends Composite {

  @Inject
  @DataField
  @UnOrderedList
  private ListWidget<Space, SpaceWidget> spaces;

  @PostConstruct
  public void loadRootSpaces() {
    JsonHttp.onSuccess(new JsonResponse() {
      @Override
      public void process(final HttpResponse response) {
        JsArray<Space> jsRootSpaces = JsonUtils.safeEval(response.getText());
        spaces.setItems(new JsArrayList<Space>(jsRootSpaces));
      }
    }).get(new SpaceCriteria());
  }
}

package org.silverpeas.poc.client.local;

import io.reinert.gdeferred.DoneCallback;
import io.reinert.gdeferred.FailCallback;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageState;
import org.jboss.errai.ui.shared.api.annotations.Bundle;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.api.Callback;
import org.silverpeas.poc.api.util.Log;
import org.silverpeas.poc.api.web.components.common.Message;
import org.silverpeas.poc.client.local.space.Space;
import org.silverpeas.poc.client.local.space.SpaceLoader;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.template.SilverpeasSpaceLayout;
import org.silverpeas.poc.client.local.util.BundleProvider;

import javax.inject.Inject;

/**
 * @author miguel
 */
@Page(path = "space/{spaceId}")
@Templated
@Bundle(BundleProvider.JSON_MESSAGES)
public class SpaceHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasSpaceLayout spaceLayout;

  @PageState
  private String spaceId;

  private Space currentSpace;

  @Override
  protected SilverpeasComposite getCompositeParent() {
    return spaceLayout;
  }

  @Override
  public void onPageShowing() {
    SpaceLoader.get().loadSpace(spaceId).then(new DoneCallback<Space>() {

      @Override
      public void onDone(final Space space) {
        currentSpace = space;
        Log.debug("SpaceHomePage - onPageShowing - Space '{0}' loaded for display (id={1})",
            space.getLabel(), space.getId());
      }
    }).fail(new FailCallback<Void>() {
      @Override
      public void onFail(final Void result) {
        Message.notifies("The space does not exist...").error(new Callback() {
          @Override
          public void invoke(final Object... parameters) {
            Dispatcher.home();
          }
        });
      }
    });
  }
}

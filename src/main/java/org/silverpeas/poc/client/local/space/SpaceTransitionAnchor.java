package org.silverpeas.poc.client.local.space;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import org.jboss.errai.ui.nav.client.local.HistoryToken;
import org.jboss.errai.ui.nav.client.local.HistoryTokenFactory;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.nav.client.local.spi.PageNode;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.SpaceHomePage;
import org.silverpeas.poc.client.local.space.event.SpaceSelection;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author Yohann Chastagnier
 */
public class SpaceTransitionAnchor extends Anchor implements ClickHandler {

  private Navigation navigation;
  private HistoryTokenFactory htFactory;
  private Event<SpaceSelection> spaceSelected;

  private Space space;
  private Class toPage;
  private Multimap<String, String> state;

  public SpaceTransitionAnchor(final Navigation navigation, final HistoryTokenFactory htFactory,
      final Event<SpaceSelection> event) {
    this.navigation = navigation;
    this.htFactory = htFactory;
    this.spaceSelected = event;
    addClickHandler(this);
  }

  @Override
  public void onClick(final ClickEvent event) {
    if (isEnabled()) {
      spaceSelected.fire(new SpaceSelection(this.space));
      navigation.goTo(toPage, this.state);
    }

    event.stopPropagation();
    event.preventDefault();
  }

  /**
   * Initialize the anchor's href attribute.
   * @param toPage The page type this transition goes to. Not null.
   * @param state The page state.  Cannot be null (but can be an empty multimap)
   */
  private void initHref(Class toPage, Multimap<String, String> state) {
    this.toPage = toPage;
    this.state = state;
    HistoryToken token = htFactory.createHistoryToken(toPage.getSimpleName(), state);
    String href = "#" + token.toString();
    setHref(href);
  }

  public void setSpace(Space space) {
    this.space = space;
    setText(space.getLabel());
    if (space.isHome()) {
      initHref(HomePage.class, ImmutableMultimap.<String, String>of());
    } else {
      initHref(SpaceHomePage.class, ImmutableMultimap.of("spaceId", space.getId()));
    }
  }
}

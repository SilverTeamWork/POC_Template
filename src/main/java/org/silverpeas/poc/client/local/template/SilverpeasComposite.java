package org.silverpeas.poc.client.local.template;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ui.nav.client.local.Navigation;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageHidden;
import org.jboss.errai.ui.nav.client.local.PageShowing;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.silverpeas.poc.api.ioc.BeanManager;
import org.silverpeas.poc.api.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Each part of the layout must extend this class.<br/>
 * This class take in charge the mechanism that compose all templates to get the final layout
 * display of the page.
 * @author Yohann Chastagnier
 */
public abstract class SilverpeasComposite extends Composite {

  /**
   * Gets the content container.
   * @return the content container.
   */
  public abstract Panel getContentPanel();

  /**
   * Gets the {@link SilverpeasComposite} instance that represents the parent of the current one.
   * @return a {@link SilverpeasComposite} instance.
   */
  protected abstract SilverpeasComposite getCompositeParent();

  /**
   * Contains treatments that must be performed during the page showing lifecycle.
   */
  public abstract void onPageShowing();

  private List<SilverpeasComposite> composites;

  protected Widget getTemplateCompositeWidget() {
    return this;
  }

  /**
   * This method is invoked when a composite annotated with {@link Page} is displaying.
   * Between all classes that composes the hierarchy of the {@link Page} class, only one method
   * must be annotated with {@link PageShowing}.<br/>
   * As the mechanism of this class uses this annotation, child classes must implement {@link
   * #onPageShowing()} in order to perform treatments on this part of display lifecycle.
   */
  @PageShowing
  private void initOnPageShowing() {

    // Caches for the current display lifecycle the composite hierarchy
    composites = getTemplateHierarchy();

    // Composing the entire layout
    composeComposites();

    // Calling the onPageShowing() method of each composite
    for (SilverpeasComposite silverpeasComposite : composites) {
      silverpeasComposite.onPageShowing();
    }
  }

  /**
   * This method is invoked when a composite annotated with {@link Page} is displaying.
   * Between all classes that composes the hierarchy of the {@link Page} class, only one method
   * must be annotated with {@link PageShowing}.<br/>
   * As the mechanism of this class uses this annotation, child classes must implement {@link
   * #onPageShown()} in order to perform treatments on this part of display lifecycle.
   */
  @PageShown
  private void initOnPageShown() {
    for (SilverpeasComposite silverpeasComposite : composites) {
      silverpeasComposite.onPageShown();
    }
  }

  /**
   * Contains treatments that must be performed during the page shown lifecycle.
   */
  public void onPageShown() {
  }

  /**
   * This method is invoked when a composite annotated with {@link Page} is displaying.
   * Between all classes that composes the hierarchy of the {@link Page} class, only one method
   * must be annotated with {@link PageShowing}.<br/>
   * As the mechanism of this class uses this annotation, child classes must implement {@link
   * #onPageHidden()} in order to perform treatments on this part of display lifecycle.
   */
  @PageHidden
  private void initOnPageHidden() {
    composites.get(0).getContentPanel().clear();
    for (SilverpeasComposite silverpeasComposite : composites) {
      silverpeasComposite.onPageHidden();
    }
  }

  /**
   * Contains treatments that must be performed during the page shown lifecycle.
   */
  public void onPageHidden() {
  }

  /**
   * Gets the complete template composite hierarchy from parent to child.
   * @return the complete template composite hierarchy from parent to child.
   */
  private List<SilverpeasComposite> getTemplateHierarchy() {
    List<SilverpeasComposite> hierarchy = new ArrayList<>();
    SilverpeasComposite composite = this;
    while (composite != null) {
      hierarchy.add(composite);
      composite = composite.getCompositeParent();
    }
    Collections.reverse(hierarchy);
    Log.debug(this.getClass().getName() + " - hierarchy of " + hierarchy.size() + " templates");
    return hierarchy;
  }

  /**
   * Packages the hierarchy of template composites.
   */
  private void composeComposites() {
    Iterator<SilverpeasComposite> templateCompositeIterator = composites.iterator();
    while (templateCompositeIterator.hasNext()) {
      SilverpeasComposite silverpeasComposite = templateCompositeIterator.next();
      SilverpeasComposite parent = silverpeasComposite.getCompositeParent();
      if (parent == null) {
        RootPanel.get().clear();
        RootPanel.get().add(silverpeasComposite.getTemplateCompositeWidget());
      } else if (templateCompositeIterator.hasNext()) {
        parent.getContentPanel().add(silverpeasComposite.getTemplateCompositeWidget());
      } else {
        parent.getContentPanel().add(getNavigation().getContentPanel());
      }
    }
  }

  /**
   * Gets the {@link Navigation} instance managed by ERRAI framework.
   * @return a {@link Navigation} instance.
   */
  protected Navigation getNavigation() {
    return BeanManager.getInstanceOf(Navigation.class);
  }
}

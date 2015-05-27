package org.silverpeas.poc.client.local.test.yocha.ui;

import com.google.common.collect.ImmutableMultimap;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.nav.client.local.TransitionAnchorFactory;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.silverpeas.poc.client.local.HomePage;
import org.silverpeas.poc.client.local.blog.home.BlogHomePage;
import org.silverpeas.poc.client.local.blog.post.BlogPostPage;
import org.silverpeas.poc.client.local.template.SilverpeasComposite;
import org.silverpeas.poc.client.local.template.SilverpeasPageComposite;
import org.silverpeas.poc.client.local.template.SilverpeasSpaceLayout;
import org.silverpeas.poc.client.local.widget.OperationWidget;
import org.silverpeas.poc.client.local.widget.SilverpeasHtmlPanel;
import org.silverpeas.poc.client.local.widget.WysiwygEditor;

import javax.inject.Inject;

/**
 * @author miguel
 */
@Page(path= "miguel.html")
@Templated
public class MiguelHomePage extends SilverpeasPageComposite {

  @Inject
  private SilverpeasSpaceLayout spaceLayout;

  @Inject
  private TransitionAnchorFactory<BlogHomePage> transitionAnchorFactory;

  @Inject
  @DataField("home-content")
  private WysiwygEditor content;

  /**
   * Gets the {@link SilverpeasComposite} instance that represents the parent of the current one.
   * @return a {@link SilverpeasComposite} instance.
   */
  @Override
  protected SilverpeasComposite getCompositeParent() {
    return spaceLayout;
  }

  /**
   * Contains treatments that must be performed during the page showing lifecycle.
   */
  @Override
  public void onPageShowing() {
    /*TransitionAnchor<BlogHomePage> transitionAnchor = transitionAnchorFactory.get("blogId", "blog1");
    transitionAnchor.setText("Go to blog 1");
    content.add(transitionAnchor);*/
  }
}
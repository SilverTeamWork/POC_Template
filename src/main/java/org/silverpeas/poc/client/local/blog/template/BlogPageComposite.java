package org.silverpeas.poc.client.local.blog.template;

import org.silverpeas.poc.client.local.template.SilverpeasApplicationPageComposite;

/**
 * @author Yohann Chastagnier
 */
public abstract class BlogPageComposite extends SilverpeasApplicationPageComposite {

  @Override
  protected String getComponentType() {
    return "blog";
  }
}

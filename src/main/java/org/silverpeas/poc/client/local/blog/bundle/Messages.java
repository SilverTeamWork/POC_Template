package org.silverpeas.poc.client.local.blog.bundle;

/**
 * @author Yohann Chastagnier
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {

  @DefaultMessage("post")
  @AlternateMessage({"a", "a post", "s", "posts", "the", "the post"})
  String post(@Select String key);
}

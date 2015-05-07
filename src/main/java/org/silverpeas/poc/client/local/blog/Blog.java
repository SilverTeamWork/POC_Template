package org.silverpeas.poc.client.local.blog;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.view.client.ProvidesKey;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.net.URI;
import java.util.List;

/**
 * @author ebonnet
 */
public class Blog extends JavaScriptObject {


  /**
   * The key provider that provides the unique ID of a blog.
   */
  public static final ProvidesKey<Blog> PROVIDES_KEY = new ProvidesKey<Blog>() {
    @Override
    public Object getKey(Blog blog) {
      return blog == null ? null : blog.getId();
    }
  };

  // Overlay types always have protected, zero argument constructors.
  protected Blog() {
  }

  public final native String getId() /*-{
    return this.id;
  }-*/;

  public final native String getName() /*-{
    return this.name;
  }-*/;

  public final native String getUri() /*-{
    return this.uri;
  }-*/;

}

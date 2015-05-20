package org.silverpeas.poc.api.event;

/**
 * @author miguel
 */
public abstract class ResourceEvent<R> {

  private R resource;

  public ResourceEvent(final R resource) {
    this.resource = resource;
  }

  public R getResource() {
    return resource;
  }

}

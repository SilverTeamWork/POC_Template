package org.silverpeas.poc.client.local.util;

import java.util.Arrays;
import java.util.List;

/**
 * A controller of access to modification operations. If the user has no right to perform the
 * privileged action, then nothing is done and the access control returns silently.
 * @author miguel
 */
public class AccessController {

  private static final List<String> PRIVILEGED_HTTP_METHODS =
      Arrays.asList("PUT", "POST", "DELETE", "PATCH");

  private final WebLink[] links;

  protected AccessController(WebLink[] links) {
    this.links = links;
  }

  public static AccessController on(Contribution contribution) {
    return new AccessController(contribution.getLinks());
  }


  public static AccessController on(ContributionList contributionList) {
    return new AccessController(contributionList.getLinks());
  }

  /**
   * Performs the specific action only if the user has the privilege to modify the contribution on
   * which the action is.
   * @param action a privileged action.
   */
  public AccessController doOnlyIfPrivileged(Action action) {
    if (hasPrivilege()) {
      action.run();
    }
    return this;
  }

  /**
   * Performs the specific action only if the user hasn't the privilege to modify the contribution
   * on which the action is.
   * @param action an unprivileged action.
   */
  public AccessController doOnlyIfUnprivileged(Action action) {
    if (!hasPrivilege()) {
      action.run();
    }
    return this;
  }

  public interface Action {
    void run();
  }

  private boolean hasPrivilege() {
    if (links != null && links.length > 0) {
      for (WebLink link : links) {
        if (PRIVILEGED_HTTP_METHODS.contains(link.getHttpMethod())) {
          return true;
        }
      }
    }
    return false;
  }
}

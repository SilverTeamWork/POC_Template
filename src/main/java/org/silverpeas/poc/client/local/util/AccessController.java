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

  private final Contribution contribution;

  protected AccessController(Contribution contribution) {
    this.contribution = contribution;
  }

  public static AccessController on(Contribution contribution) {
    return new AccessController(contribution);
  }

  /**
   * Performs the specific action only if the user has the privilege to modify the contribution on
   * which the action is.
   * @param action a privileged action.
   */
  public void doPrivileged(Action action) {
    if (hasPrivilege()) {
      action.run();
    }
  }

  /**
   * Performs the specific action only if the user hasn't the privilege to modify the contribution
   * on which the action is.
   * @param action an unprivileged action.
   */
  public void doUnPrivileged(Action action) {
    if (!hasPrivilege()) {
      action.run();
    }
  }

  public interface Action {
    void run();
  }

  private boolean hasPrivilege() {
    Contribution.Link[] links = contribution.getLinks();
    if (links != null && links.length > 0) {
      for (Contribution.Link link: links) {
        if (PRIVILEGED_HTTP_METHODS.contains(link.getHttpMethod())) {
          return true;
        }
      }
    }
    return false;
  }
}

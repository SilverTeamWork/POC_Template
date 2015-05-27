package org.silverpeas.poc.server;

import org.jboss.errai.ui.shared.api.annotations.Templated;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * As we use the HTML5 pushState feature in order to get beautiful URLs on client browser bars, it
 * is hard to work with errai {@link Templated} components that contains directly resource
 * URLs relative to the application servlet context.<br/>
 * This filter has been created in order to solved this problem without doing any work on actual
 * and next templates.
 * @author Yohann Chastagnier
 */
public class ResourceDispatcher implements Filter {

  private static String[] handledPaths = new String[]{"/img", "/css", "/js"};

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String requestUri = request.getRequestURI();
    if (!requestUri.startsWith("http")) {
      int index = getPositionOfHandledResourcePath(requestUri);
      if (index > 0) {
        // If a handled path is detected, then the url is rewritten.
        RequestDispatcher dispatcher = request.getRequestDispatcher(requestUri.substring(index));
        dispatcher.forward(request, servletResponse);
        return;
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  /**
   * Gets the index of one of the handledPaths.
   * @param uri the full uri on which the filter is performing.
   * @return an integer value greater or equals to 0 if found, -1 otherwise.
   */
  private int getPositionOfHandledResourcePath(String uri) {
    int index = -1;
    for (String handledPath : handledPaths) {
      index = uri.indexOf(handledPath);
      if (index != -1) {
        break;
      }
    }
    return index;
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }
}

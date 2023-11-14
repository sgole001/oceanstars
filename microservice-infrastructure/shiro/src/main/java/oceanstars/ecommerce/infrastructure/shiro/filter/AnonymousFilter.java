package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * 匿名过滤器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:37 上午
 */
public class AnonymousFilter extends RestControlFilter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "anon";

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    return Boolean.TRUE;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) {
    return Boolean.TRUE;
  }
}

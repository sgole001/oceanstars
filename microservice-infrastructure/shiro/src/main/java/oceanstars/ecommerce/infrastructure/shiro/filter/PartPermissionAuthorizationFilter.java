package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.subject.Subject;

/**
 * 权限过滤器（全部满足）
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:27 下午
 */
public class PartPermissionAuthorizationFilter extends AuthorizationFilter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "perm";

  private final Logger logger = LogManager.getLogger(PartPermissionAuthorizationFilter.class);

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    Subject subject = super.getSubject(request, response);

    // 权限过滤条件信息获取
    String[] perms = (String[]) mappedValue;

    if (null != perms) {

      for (String perm : perms) {
        if (subject.isPermitted(perm)) {
          return Boolean.TRUE;
        }
      }
    }

    return Boolean.FALSE;
  }
}

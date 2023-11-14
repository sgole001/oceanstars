package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;

/**
 * 权限过滤器（全部满足）
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:28 下午
 */
public class PermissionsAuthorizationFilter extends AuthorizationFilter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "perms";

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

    Subject subject = super.getSubject(request, response);

    // 权限过滤条件信息获取
    String[] perms = (String[]) mappedValue;

    if (perms != null && perms.length > 0) {
      if (perms.length == 1) {
        return subject.isPermitted(perms[0]);
      }
      return subject.isPermittedAll(perms);
    }

    return Boolean.FALSE;
  }
}

package oceanstars.ecommerce.infrastructure.shiro.filter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.springframework.util.CollectionUtils;

/**
 * 角色过滤器(部分满足)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:31 下午
 */
public class RolesAuthorizationFilter extends AuthorizationFilter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "roles";

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

    Subject subject = super.getSubject(request, response);

    // 角色过滤条件信息获取
    Set<String> roles = this.resolveRoles(mappedValue);

    if (CollectionUtils.isEmpty(roles)) {
      return Boolean.TRUE;
    }

    // 满足其中一个就授权通过
    for (String role : roles) {

      if (subject.hasRole(role)) {
        return Boolean.TRUE;
      }
    }

    return Boolean.FALSE;
  }

  /**
   * 根据过滤器参数解析角色信息
   *
   * @param mappedValue 过滤器参数
   * @return 角色信息
   */
  protected Set<String> resolveRoles(Object mappedValue) {
    return Arrays.stream((String[]) mappedValue).collect(Collectors.toSet());
  }
}

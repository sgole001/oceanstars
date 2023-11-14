package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import oceanstars.ecommerce.common.tools.MessageUtil;
import oceanstars.ecommerce.infrastructure.shiro.constant.ShiroConstant;
import org.apache.shiro.web.util.WebUtils;

/**
 * 授权过滤器抽象类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:37 上午
 */
public abstract class AuthorizationFilter extends RestControlFilter {

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

    // 对外错误信息
    String msgError = MessageUtil.ACCESSOR.getMessage(ShiroConstant.MSG_SHIRO_SYS_00000);

    // 错误信息返回
    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, msgError);

    return Boolean.FALSE;
  }
}

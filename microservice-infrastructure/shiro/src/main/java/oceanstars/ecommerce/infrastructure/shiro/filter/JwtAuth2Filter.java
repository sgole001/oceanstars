package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.tools.MessageUtil;
import oceanstars.ecommerce.infrastructure.shiro.constant.ShiroConstant;
import oceanstars.ecommerce.infrastructure.shiro.token.JwtAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;

/**
 * ShiroJwt认证过滤器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:43 上午
 */
public class JwtAuth2Filter extends RestControlFilter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "jwt";

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(JwtAuth2Filter.class);

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

    Subject subject = SecurityUtils.getSubject();

    if (null != subject && subject.isAuthenticated()) {
      return Boolean.TRUE;
    }

    return Boolean.FALSE;
  }

  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    if (this.isJwtHttpRequest(request)) {

      // 构建认证Token对象
      JwtAuthenticationToken token = this.buildJwtAuthToken(request);

      try {
        // 当前用户对象登陆
        SecurityUtils.getSubject().login(token);

        return Boolean.TRUE;
      } catch (AuthenticationException e) {
        if (e.getCause() instanceof BusinessException) {
          WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getCause().getMessage());
          return false;
        }
        logger.error(MessageUtil.ACCESSOR.getMessage(ShiroConstant.MSG_SHIRO_SYS_00000), e);
      }
    }

    // 错误信息返回
    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageUtil.ACCESSOR.getMessage(ShiroConstant.MSG_SHIRO_SYS_00000));

    return Boolean.FALSE;
  }

  /**
   * 判定是否HTTP请求，并且头部存在JWT信息
   *
   * @param request ServletRequest
   * @return 判定结果
   */
  private boolean isJwtHttpRequest(ServletRequest request) {

    // 判定是否HTTP请求
    if (request instanceof HttpServletRequest) {

      // 从header中获取JWT信息
      String jwt = WebUtils.toHttp(request).getHeader(ShiroConstant.JWT_AUTH_HTTP_HEAD);

      if (StringUtils.isNotBlank(jwt)) {
        return Boolean.TRUE;
      }
    }

    return Boolean.FALSE;
  }

  /**
   * 构建认证Token对象
   *
   * @param request ServletRequest
   * @return 认证Token对象
   */
  private JwtAuthenticationToken buildJwtAuthToken(ServletRequest request) {

    // 从header中获取JWT信息
    String jwt = WebUtils.toHttp(request).getHeader(ShiroConstant.JWT_AUTH_HTTP_HEAD);

    return new JwtAuthenticationToken(jwt);
  }
}

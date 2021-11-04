package oceanstars.ecommerce.infrastructure.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import oceanstars.ecommerce.common.tools.ServletUtil;
import oceanstars.ecommerce.infrastructure.shiro.constant.ShiroConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.InvalidRequestFilter;

/**
 * Restful请求处理过滤器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:29 下午
 */
public class RestControlFilter extends InvalidRequestFilter {

  /**
   * 默认构造器
   */
  public RestControlFilter() {
    // ...
  }

  /**
   * Subject对象获取
   *
   * @param request  HTTP请求
   * @param response HTTP响应
   * @return Subject对象
   */
  @Override
  protected Subject getSubject(ServletRequest request, ServletResponse response) {
    return SecurityUtils.getSubject();
  }

  /**
   * 路径匹配处理，支持Restful请求
   *
   * @param path    匹配格式
   * @param request HTTP请求
   * @return 匹配结果
   */
  @Override
  protected boolean pathsMatch(String path, ServletRequest request) {

    // 获取请求URI
    String requestUri = super.getPathWithinApplication(request);
    // 获取请求HttpMethod
    String requestMethod = ServletUtil.getRequestMethod(request);

    // 获取匹配URI
    String pathUri = path;
    // 获取匹配HTTP方法
    String pathHttpMethods = "";

    // 分割过滤信息
    String[] splitPath = path.split(ShiroConstant.REST_CHAIN_PATTERN_SEPARATOR_REGULAR);

    if (splitPath.length == 2) {
      pathUri = splitPath[0];
      pathHttpMethods = splitPath[1];
    }

    // 匹配URI处理
    boolean isMatched = super.pathsMatch(pathUri, requestUri);
    // 匹配HTTP方法
    return isMatched && (StringUtils.isBlank(pathHttpMethods) || pathHttpMethods.contains(requestMethod.toUpperCase()));
  }

  @Override
  public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    return this.isAccessAllowed(request, response, mappedValue) || this.onAccessDenied(request, response, mappedValue);
  }
}

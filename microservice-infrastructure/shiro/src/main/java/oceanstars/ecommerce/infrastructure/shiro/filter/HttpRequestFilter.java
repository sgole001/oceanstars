package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.constant.CommonMessageConstant;
import oceanstars.ecommerce.common.exception.SystemException;
import oceanstars.ecommerce.common.session.BaseSessionAttribute;
import oceanstars.ecommerce.common.session.Sessions;
import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.common.tools.PropertyUtil;
import oceanstars.ecommerce.common.tools.ServletUtil;
import oceanstars.ecommerce.infrastructure.redis.constant.RedisEnum.FUNCTION_GROUP;
import oceanstars.ecommerce.infrastructure.redis.tools.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Web请求拦截器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:38 上午
 */
public class HttpRequestFilter implements Filter {

  /**
   * 过滤器别名
   */
  public static final String FILTER_ALIAS = "http-request";

  public static final String STR_ENG_PATTERN = "^[a-z0-9A-Z]+$";

  private static Logger logger = LogManager.getLogger(HttpRequestFilter.class.getName());

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    // 获取HTTP请求对象
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

    // 获取请求跟踪ID
    String traceId = httpRequest.getHeader(CommonConstant.KEY_TRACE);

    // 获取ApiKey
    String apiKey = httpRequest.getHeader("Authentication");
    // 获取Session数据库（Redis）客户端
    final RedisUtil redisUtil = RedisUtil.build(FUNCTION_GROUP.SESSION.getName());
    // 是否授权认证请求
    boolean isAuthRequest = StringUtils.isBlank(apiKey);

    // 创建Session调用链ID
    if (StringUtils.isBlank(traceId)) {
      traceId = ServletUtil.generateTraceId();
    }

    // validate traceId
    if (!traceId.matches(STR_ENG_PATTERN)) {
      throw new SystemException(CommonMessageConstant.MSG_COM_00087);
    }
    // 获取当前服务名
    final String applicationName = PropertyUtil.BINDER.bind("spring.application.name", String.class).get();
    // 日志全局加入跟踪ID
    ThreadContext.put(CommonConstant.KEY_TRACE, traceId);

    // Session对象构建
    Sessions sessions;
    // 所有请求统一经过Auth服务进行授权认证，并分配当前请求跟踪ID
    if (isAuthRequest) {

      // 创建Session属性对象
      final BaseSessionAttribute sessionAttribute = new BaseSessionAttribute();
      // 设定Session信息——调用链ID
      sessionAttribute.setTraceId(traceId);
      // 设定Session信息——请求接受方
      sessionAttribute.setTraceProvider(applicationName);

      // 创建Session对象
      sessions = new Sessions(sessionAttribute);

      // 缓存Session信息，默认一次请求的最长时间(60s)
      redisUtil.set(traceId, sessions, 60);
    } else {
      sessions = JsonUtil.parse(JsonUtil.toString(redisUtil.get(traceId)), Sessions.class);
      if (null == sessions) {
        // 创建Session属性对象
        final BaseSessionAttribute sessionAttribute = new BaseSessionAttribute();
        // 设定Session信息——调用链ID
        sessionAttribute.setTraceId(traceId);
        // 设定Session信息——请求接受方
        sessionAttribute.setTraceProvider(applicationName);

        // 创建Session对象
        sessions = new Sessions(sessionAttribute);
      }
    }
    httpRequest.setAttribute(Sessions.SESSION_KEY, sessions);
    // 将跟踪ID回传
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    httpResponse.setHeader(CommonConstant.KEY_TRACE, traceId);

    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      ThreadContext.remove(CommonConstant.KEY_TRACE);
      // 获取HTTP响应状态
      int httpStatus = ((HttpServletResponse) servletResponse).getStatus();
      // 非授权认证的请求，一次请求后删除跟踪信息
      if (!isAuthRequest) {
        redisUtil.delete(traceId);
      }
      // 授权认证失败的请求，一次请求后删除跟踪信息
      else if (httpStatus >= HttpServletResponse.SC_BAD_REQUEST) {
        // 非Auth服务请求认证通过，找不到相对应的URI资源
        if (httpStatus != HttpServletResponse.SC_NOT_FOUND) {
          redisUtil.delete(traceId);
        }
      }
      // 其他情况（用户登录，注册等Auth服务请求等），默认60s自动过期清除跟踪信息
    }
  }
}

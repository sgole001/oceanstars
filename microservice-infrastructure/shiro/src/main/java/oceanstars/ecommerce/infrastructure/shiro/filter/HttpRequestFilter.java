package oceanstars.ecommerce.infrastructure.shiro.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import oceanstars.ecommerce.common.constant.CommonMessageConstant;
import oceanstars.ecommerce.common.exception.SystemException;
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
    String traceId = httpRequest.getHeader("trace_id");

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
    String applicationName = PropertyUtil.BINDER.bind("spring.application.name", String.class).get();
    // 日志全局加入跟踪ID
    ThreadContext.put("TRACE_ID", traceId);

    // Session对象构建
    Sessions sessions;
    // 所有请求统一经过Auth服务进行授权认证，并分配当前请求跟踪ID
    if (isAuthRequest) {
      // 获取餐厅号
      final String restaurantId = httpRequest.getHeader("rsid");
      // 获取桌号
      final String tableId = httpRequest.getHeader("tbid");
      // 获取房号
      final String roomId = httpRequest.getHeader("rmid");

      sessions = new Sessions();
      // 设定Session信息——调用链ID
      sessions.setTraceId(traceId);
      // 设定Session信息——请求接受方
      sessions.setTraceProvider(applicationName);
      // 设定Session信息——餐厅号
      if (StringUtils.isNotBlank(restaurantId)) {
        sessions.setRestaurantId(Long.valueOf(restaurantId));
      }
      // 设定Session信息——桌号
      if (StringUtils.isNotBlank(tableId)) {
        sessions.setTableId(Long.valueOf(tableId));
      }
      // 设定Session信息——房号
      if (StringUtils.isNotBlank(roomId)) {
        sessions.setRoomId(Long.valueOf(roomId));
      }

      // 缓存Session信息，默认一次请求的最长时间(60s)
      redisUtil.set(traceId, sessions, 60);
    } else {
      sessions = JsonUtil.parse(JsonUtil.toString(redisUtil.get(traceId)), Sessions.class);
      if (null == sessions) {
        sessions = new Sessions();

        // 设定Session信息——调用链ID
        sessions.setTraceId(traceId);
        // 设定Session信息——请求接受方
        sessions.setTraceProvider(applicationName);
      }
    }
    httpRequest.setAttribute(Sessions.SESSION_KEY, sessions);
    // 将跟踪ID回传
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    httpResponse.setHeader("trace_id", traceId);

    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      ThreadContext.remove("TRACE_ID");
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

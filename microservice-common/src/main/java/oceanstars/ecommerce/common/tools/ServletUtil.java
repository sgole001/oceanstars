package oceanstars.ecommerce.common.tools;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringJoiner;
import java.util.UUID;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.session.Sessions;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:08 下午
 */
public class ServletUtil {

  /**
   * 日志管理器
   */
  private static final Logger logger = LogManager.getLogger(ServletUtil.class.getName());

  /**
   * 工具类显示声明私有构造函数
   */
  private ServletUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 获取当前HttpServletRequest对象信息
   *
   * @return 当前HttpServletRequest对象信息
   */
  public static HttpServletRequest getHttpServletRequest() {

    // 获取请求属性信息
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (requestAttributes != null) {
      return requestAttributes.getRequest();
    }
    return null;
  }

  /**
   * 获取当前HttpServletResponse对象信息
   *
   * @return 当前HttpServletResponse对象信息
   */
  public static HttpServletResponse getHttpServletResponse() {

    // 获取请求属性信息
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (requestAttributes != null) {
      return requestAttributes.getResponse();
    }
    return null;
  }

  /**
   * 获取HTTP请求方法
   *
   * @param request HTTP请求信息
   * @return HTTP请求方法
   */
  public static String getRequestMethod(ServletRequest request) {

    try {
      return ((HttpServletRequest) request).getMethod();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 获取请求头部Token信息
   *
   * @return Token信息
   */
  public static String getToken() {

    // 获取当前HttpServletRequest对象信息
    HttpServletRequest servletRequest = getHttpServletRequest();

    if (servletRequest != null) {
      return servletRequest.getHeader(CommonConstant.KEY_JWT_HTTP_HEADER);
    }
    return null;
  }

  /**
   * 获取请求参数Session信息
   *
   * @return Session信息
   */
  public static Sessions getSession() {

    // 获取当前HttpServletRequest对象信息
    HttpServletRequest servletRequest = getHttpServletRequest();

    if (servletRequest != null) {
      return (Sessions) servletRequest.getAttribute(Sessions.SESSION_KEY);
    }
    return null;
  }

  /**
   * 刷新Token
   *
   * @param token Token信息
   */
  public static void refreshToken(String token) {

    // 获取当前HttpServletRequest对象信息
    HttpServletResponse servletResponse = getHttpServletResponse();

    if (servletResponse != null) {
      servletResponse.setHeader(CommonConstant.KEY_JWT_HTTP_HEADER, token);
    }
  }

  /**
   * 获取远程调用IP地址
   *
   * @return 远程调用IP地址
   */
  public static String getRemoteIpAddress() {

    // 获取当前HttpServletRequest对象信息
    HttpServletRequest servletRequest = getHttpServletRequest();

    // 根据X-Forwarded-For（XFF）获取IP地址（通过HTTP代理或负载均衡方式连接）
    String remoteIpAddress = null;
    if (servletRequest != null) {
      remoteIpAddress = servletRequest.getHeader("x-forwarded-for");
    }

    if (StringUtils.isNotBlank(remoteIpAddress)) {
      // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
      // 格式：X-Forwarded-For: client1, proxy1, proxy2, proxy3
      String[] ips = remoteIpAddress.split(",");
      remoteIpAddress = ips[0];
    }

    // 在apache+WebLogic整合系统中，apache会对request对象进行再包装，附加一些WLS要用的头信息
    // 代理客服端IP
    if (StringUtils.isBlank(remoteIpAddress) || CommonConstant.SQUID_OFF_IP_ADDRESS.equalsIgnoreCase(remoteIpAddress)) {
      remoteIpAddress = null != servletRequest ? servletRequest.getHeader("Proxy-Client-IP") : null;
    }
    // WebLogic代理客服端IP（webLogic设置了才会有这个参数）
    if (StringUtils.isBlank(remoteIpAddress) || CommonConstant.SQUID_OFF_IP_ADDRESS.equalsIgnoreCase(remoteIpAddress)) {
      remoteIpAddress = null != servletRequest ? servletRequest.getHeader("WL-Proxy-Client-IP") : null;
    }

    // 没有代理的情况
    if (StringUtils.isBlank(remoteIpAddress) || CommonConstant.SQUID_OFF_IP_ADDRESS.equalsIgnoreCase(remoteIpAddress)) {
      remoteIpAddress = null != servletRequest ? servletRequest.getRemoteAddr() : null;

      if (CommonConstant.LOOPBACK_IP_ADDRESS.equalsIgnoreCase(remoteIpAddress)) {
        try {
          // 根据网卡取本机配置的IP
          remoteIpAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
          logger.error(e);
        }
      }
    }

    return remoteIpAddress;
  }

  /**
   * 生成请求的跟踪ID
   *
   * @return Session的跟踪ID
   */
  public static String generateTraceId() {

    // 构建TraceId元素组成
    final StringJoiner traceId = new StringJoiner(CommonConstant.SEPARATOR_HYPHEN);
    // UUID
    traceId.add(UUID.randomUUID().toString());
    // 当前线程ID
    traceId.add(String.valueOf(Thread.currentThread().threadId()));
    // 当前线程名
    traceId.add(Thread.currentThread().getName());

    // Checksum
    return DigestUtils.md5DigestAsHex(traceId.toString().getBytes());
  }
}

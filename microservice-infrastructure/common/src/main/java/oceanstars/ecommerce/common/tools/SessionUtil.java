package oceanstars.ecommerce.common.tools;

import javax.servlet.http.HttpServletRequest;
import oceanstars.ecommerce.common.session.SessionThreadLocal;
import oceanstars.ecommerce.common.session.Sessions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Session工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:28 下午
 */
public class SessionUtil {

  /**
   * 日志管理器
   */
  private static Logger logger = LogManager.getLogger(SessionUtil.class.getName());

  /**
   * 获取Session信息
   *
   * @return Session信息
   */
  public static Sessions getSessions() {

    // 获取HTTP请求信息
    final HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();

    // 从HTTP请求中获取Session信息
    Sessions sessions = null;
    if (httpServletRequest != null) {
      sessions = (Sessions) httpServletRequest.getAttribute(Sessions.SESSION_KEY);
    }

    // GRPC中获取Session信息 TODO

    // 从ThreadLocal中获取Session信息(多线程的场合)
    if (null == sessions) {
      sessions = SessionThreadLocal.getSessions();
    }

    return sessions;
  }
}

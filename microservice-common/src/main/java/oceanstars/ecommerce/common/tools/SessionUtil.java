package oceanstars.ecommerce.common.tools;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import oceanstars.ecommerce.common.session.BaseSessionAttribute;
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
   * 构造函数：私有化，显式表示静态工具类
   */
  private SessionUtil() {
    throw new IllegalStateException("Utility class");
  }

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

    // 此逻辑仅在测试环境中弥补环境不完整的参数依赖
    if (null == sessions) {
      // Mock Session
      sessions = mockSessions();
    }

    return sessions;
  }

  /**
   * 获取Session属性信息
   *
   * @return Session属性信息
   */
  public static BaseSessionAttribute getSessionAttribute() {
    return getSessions().attribute();
  }

  /**
   * Mock Session
   *
   * @return Session属性信息
   */
  private static Sessions mockSessions() {

    // 创建Session属性信息
    final BaseSessionAttribute attributes = new BaseSessionAttribute();
    // Mock用户ID
    attributes.setUserId("Simulator");
    // Mock会话调用链ID
    attributes.setTraceId(UUID.randomUUID().toString());
    // Mock会话发起方
    attributes.setTraceConsumer(PropertyUtil.BINDER.bind("spring.application.name", String.class).get());
    // Mock会话接受方
    attributes.setTraceProvider(PropertyUtil.BINDER.bind("spring.application.name", String.class).get());

    // 创建Session信息
    return new Sessions(attributes);
  }
}

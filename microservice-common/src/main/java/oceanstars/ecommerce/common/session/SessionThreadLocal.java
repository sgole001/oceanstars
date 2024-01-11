package oceanstars.ecommerce.common.session;

/**
 * Session线程工具
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:08 下午
 */
public class SessionThreadLocal {

  private static final ThreadLocal<Sessions> SESSION = new ThreadLocal<>();

  private SessionThreadLocal() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 设定Session信息
   *
   * @param sessions Session信息
   */
  public static void setSessions(final Sessions sessions) {
    SESSION.set(sessions);
  }

  /**
   * 获取Session信息
   *
   * @return Session信息
   */
  public static Sessions getSessions() {
    return SESSION.get();
  }

  /**
   * 清除Session信息（防止内存泄露）
   */
  public static void removeSessions() {
    SESSION.remove();
  }
}

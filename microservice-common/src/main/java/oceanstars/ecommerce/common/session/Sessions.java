package oceanstars.ecommerce.common.session;

import java.io.Serial;
import java.io.Serializable;

/**
 * Session信息缓存对象
 *
 * @param attribute Session信息缓存属性对象
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:06 下午
 */
public record Sessions(BaseSessionAttribute attribute) implements Serializable {

  @Serial
  private static final long serialVersionUID = 9160642446906899140L;

  /**
   * Session信息缓存Key
   */
  public static final String SESSION_KEY = "session";

  /**
   * 多线程Session共享
   *
   * @param target 目标可运行逻辑
   */
  public void share(Runnable target) {

    try {
      // 将Session共享到当前线程
      SessionThreadLocal.setSessions(this);
      // 具体逻辑运行
      target.run();
    } finally {
      // 手动回收ThreadLocal，避免内存溢出
      SessionThreadLocal.removeSessions();
    }
  }
}

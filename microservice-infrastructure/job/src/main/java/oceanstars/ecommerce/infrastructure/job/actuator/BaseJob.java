package oceanstars.ecommerce.infrastructure.job.actuator;

import oceanstars.ecommerce.common.session.SessionThreadLocal;
import oceanstars.ecommerce.common.session.Sessions;
import oceanstars.ecommerce.common.tools.ServletUtil;
import org.apache.logging.log4j.ThreadContext;

/**
 * 任务调度基础执行器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 2:22 下午
 */
public class BaseJob {

  /**
   * JobHandler初始化方法
   */
  public void init() {
    // 构建当前线程Session
    Sessions sessions = new Sessions();
    // 会话调用链ID
    sessions.setTraceId(ServletUtil.generateTraceId());

    SessionThreadLocal.setSessions(sessions);

    // 日志全局加入跟踪ID
    ThreadContext.put("TRACE_ID", sessions.getTraceId());
  }

  /**
   * JobHandler销毁方法
   */
  public void destroy() {
    // 手动回收ThreadLocal，避免内存溢出
    SessionThreadLocal.removeSessions();

    ThreadContext.remove("TRACE_ID");
  }
}

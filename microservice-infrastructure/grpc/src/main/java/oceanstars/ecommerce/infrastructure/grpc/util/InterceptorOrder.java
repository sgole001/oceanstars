package oceanstars.ecommerce.infrastructure.grpc.util;

import org.springframework.core.Ordered;

/**
 * 拦截器顺序常量类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 6:09 下午
 */
public final class InterceptorOrder {

  /**
   * 拦截器第一个执行的顺序值
   */
  public static final int ORDER_FIRST = Ordered.HIGHEST_PRECEDENCE;

  /**
   * 全局异常处理拦截器的顺序值
   */
  public static final int ORDER_GLOBAL_EXCEPTION_HANDLING = 0;

  /**
   * 拦截器的顺序值与安全异常处理相关
   */
  public static final int ORDER_SECURITY_EXCEPTION_HANDLING = 5000;
  /**
   * 与身份验证相关的安全拦截器的顺序值
   */
  public static final int ORDER_SECURITY_AUTHENTICATION = 5100;
  /**
   * 与授权检查相关的安全拦截器的顺序值
   */
  public static final int ORDER_SECURITY_AUTHORISATION = 5200;

  /**
   * 拦截器最后执行的顺序值
   */
  public static final int ORDER_LAST = Ordered.LOWEST_PRECEDENCE;

  private InterceptorOrder() {
  }
}

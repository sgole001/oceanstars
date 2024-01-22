package oceanstars.ecommerce.common.restful;

/**
 * Restful API总线接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 13:54
 */
public interface RestBus {

  /**
   * 处理Restful API请求
   *
   * @param requestMessage Restful API请求消息
   * @param <R>            Restful API响应消息类型
   * @param <E>            Restful API请求消息类型
   * @return Restful API响应消息
   */
  <R extends RestResponseMessage, E extends RestRequestMessage> R handle(E requestMessage);
}

package oceanstars.ecommerce.common.restful;

/**
 * Restful API程序处理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 12:35 PM
 */
public interface IRestHandler<T extends RestRequestMessage> {

  /**
   * 请求处理
   *
   * @param restRequestMessage Restful API请求消息
   * @return Restful API响应消息
   */
  RestResponseMessage handle(final T restRequestMessage);
}

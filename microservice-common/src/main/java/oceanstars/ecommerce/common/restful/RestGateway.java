package oceanstars.ecommerce.common.restful;

import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * Restful网关：负责接收Restful API请求，根据请求消息类型，调用对应的Restful API程序处理接口，返回Restful API响应消息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 13:58
 */
@Component(value = "restGateway")
@SuppressWarnings("unchecked")
public class RestGateway implements RestBus {

  /**
   * Restful API程序处理接口映射表
   */
  private final Map<Class<? extends RestRequestMessage>, ? extends IRestHandler<?, ?>> handleProvider;

  /**
   * 构造函数: 依赖注入初始化成员变量
   *
   * @param handleProvider Restful API程序处理接口映射表
   */
  public RestGateway(ObjectProvider<Map<Class<? extends RestRequestMessage>, ? extends IRestHandler<?, ?>>> handleProvider) {
    this.handleProvider = handleProvider.getIfAvailable();
  }

  @Override
  public <R extends RestResponseMessage, E extends RestRequestMessage> R handle(E requestMessage) {
    IRestHandler<R, E> restHandler = (IRestHandler<R, E>) this.handleProvider.get(requestMessage.getClass());
    return restHandler.handle(requestMessage);
  }
}

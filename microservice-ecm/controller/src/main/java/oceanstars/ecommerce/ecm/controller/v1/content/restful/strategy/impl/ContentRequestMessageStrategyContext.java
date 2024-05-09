package oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy.impl;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy.ContentRequestMessageStrategy;

/**
 * 内容请求消息处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/7 17:45
 */
public class ContentRequestMessageStrategyContext {


  /**
   * 内容请求消息处理策略
   */
  private ContentRequestMessageStrategy strategy;

  /**
   * 构造函数
   *
   * @param type 资产类型
   */
  public ContentRequestMessageStrategyContext(final ContentType type) {

    if (null == type) {
      throw new IllegalArgumentException("不支持的内容类型");
    }

    switch (type) {
      case ContentType.MENU:
        this.strategy = new MenuStrategy();
        break;
    }
  }

  /**
   * 内容创建请求消息统一化处理
   *
   * @param message 内容创建请求消息
   * @return 内容创建请求消息统一化接口数据
   */
  public CreateContentRequestMessage<? extends Message> unifyCreateRequestMessage(RestRequestMessage message) {
    return this.strategy.create(message);
  }
}

package oceanstars.ecommerce.ecm.controller.v1.content.handle.strategy.impl;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.controller.v1.content.handle.strategy.ContentRawDataStrategy;

/**
 * 内容特有数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:51
 */
public class ContentRawDataStrategyContext {

  /**
   * 内容特有数据处理策略接口
   */
  private ContentRawDataStrategy strategy;

  /**
   * 构造函数: 根据内容类型选择不同的策略
   *
   * @param type 内容类型
   */
  public ContentRawDataStrategyContext(final Short type) {

    // 映射内容类型枚举
    final ContentType contentType = ContentType.of(type);

    if (null == contentType) {
      throw new IllegalArgumentException("不支持的内容类型");
    }

    switch (contentType) {
      case WEB_FUNCTION:
        this.strategy = new WebFunctionStrategy();
        break;
    }
  }

  /**
   * 解析请求消息
   *
   * @param requestMessage 请求消息
   */
  public Any parsingRequestMessage(Map<String, String> requestMessage) {
    return this.strategy.parsingRequestMessage(requestMessage);
  }
}

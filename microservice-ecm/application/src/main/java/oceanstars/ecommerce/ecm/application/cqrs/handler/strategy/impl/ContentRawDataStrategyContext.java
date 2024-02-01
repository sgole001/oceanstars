package oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.impl;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.ContentRawDataStrategy;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;

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
  public ContentRawDataStrategyContext(final Integer type) {

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
   * 打包请求消息
   *
   * @param message 请求消息
   * @return 打包后的请求消息
   */
  public Any pack(Map<String, String> message) {
    return this.strategy.pack(message);
  }

  /**
   * 解包请求消息
   *
   * @param message   打包后的请求消息
   * @param contentId 内容委托者ID
   * @return 解包后的请求消息
   */
  public Entity<?> unpack(Any message, Long contentId) {
    return this.strategy.unpack(message, contentId);
  }
}

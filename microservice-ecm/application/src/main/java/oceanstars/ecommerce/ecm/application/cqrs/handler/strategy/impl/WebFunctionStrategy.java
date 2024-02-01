package oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.impl;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmContentWebFunction;
import oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.ContentRawDataStrategy;
import oceanstars.ecommerce.ecm.domain.content.entity.WebFunction;

/**
 * Web功能内容特有数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class WebFunctionStrategy implements ContentRawDataStrategy {

  @Override
  public Any pack(Map<String, String> message) {

    final EcmContentWebFunction webFunction = EcmContentWebFunction.newBuilder()
        // Web功能类型
        .setType(Integer.parseInt(message.get("type")))
        // Web功能跳转
        .setHref(message.get("href"))
        // Web功能图标
        .setIcon(message.get("icon"))
        // Web功能隶属 - 通过内容ID关联隶属关系
        .setParent(Long.parseLong(message.get("parent")))
        // 构建执行
        .build();

    return Any.pack(webFunction);
  }

  @Override
  public Entity<?> unpack(Any message, Long contentId) {
    try {
      final EcmContentWebFunction webFunction = message.unpack(EcmContentWebFunction.class);

      return WebFunction.newBuilder(contentId)
          // Web功能跳转
          .href(webFunction.getHref())
          // Web功能图标
          .icon(webFunction.getIcon())
          // Web功能隶属 - 通过内容ID关联隶属关系
          .parent(webFunction.getParent())
          // 实施构建
          .build();

    } catch (Exception e) {
      throw new IllegalArgumentException("Web功能内容特有数据解包失败!");
    }
  }
}

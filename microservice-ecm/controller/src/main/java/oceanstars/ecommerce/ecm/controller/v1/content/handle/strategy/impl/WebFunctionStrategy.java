package oceanstars.ecommerce.ecm.controller.v1.content.handle.strategy.impl;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.EcmContentWebFunction;
import oceanstars.ecommerce.ecm.controller.v1.content.handle.strategy.ContentRawDataStrategy;

/**
 * Web功能内容特有数据处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class WebFunctionStrategy implements ContentRawDataStrategy {

  @Override
  public Any parsingRequestMessage(Map<String, String> requestMessage) {

    final EcmContentWebFunction webFunction = EcmContentWebFunction.newBuilder()
        // Web功能类型
        .setType(Integer.parseInt(requestMessage.get("type")))
        // Web功能跳转
        .setHref(requestMessage.get("href"))
        // Web功能图标
        .setIcon(requestMessage.get("icon"))
        // Web功能隶属 - 通过内容ID关联隶属关系
        .setParent(Long.parseLong(requestMessage.get("parent")))
        // 构建执行
        .build();

    return Any.pack(webFunction);
  }
}

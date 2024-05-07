package oceanstars.ecommerce.ecm.application.content.cqrs.strategy.impl;

import com.google.protobuf.Any;
import java.util.Map;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmContentFunctionMenu;
import oceanstars.ecommerce.ecm.application.content.cqrs.strategy.ContentRawDataStrategy;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentFunctionMenuValueObject;
import org.springframework.util.StringUtils;

/**
 * 内容原始信息处理策略：功能菜单
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class FunctionMenuStrategy implements ContentRawDataStrategy {

  @Override
  public Any pack(Map<String, String> message) {

    final EcmContentFunctionMenu.Builder functionMenu = EcmContentFunctionMenu.newBuilder()
        // 功能菜单对应的功能ID
        .setFunc(Long.parseLong(message.get("func")))
        // 功能菜单动作：点击菜单后的执行脚本
        .setAction(message.get("action"))
        // 功能菜单图标
        .setIcon(message.get("icon"));
    // 功能菜单隶属 - 通过内容ID关联隶属关系
    if (StringUtils.hasText(message.get("parent"))) {
      functionMenu.setParent(Long.parseLong(message.get("parent")));
    }

    return Any.pack(functionMenu.build());
  }

  @Override
  public ValueObject unpack(Any message) {
    try {
      final EcmContentFunctionMenu functionMenu = message.unpack(EcmContentFunctionMenu.class);

      return ContentFunctionMenuValueObject.newBuilder()
          // 功能菜单对应的功能ID
          .func(functionMenu.getFunc())
          // 功能菜单动作：点击菜单后的执行脚本
          .action(functionMenu.getAction())
          // 功能菜单图标
          .icon(functionMenu.getIcon())
          // 功能菜单隶属 - 通过内容ID关联隶属关系
          .parent(functionMenu.getParent())
          // 实施构建
          .build();

    } catch (Exception e) {
      throw new IllegalArgumentException("内容原始数据功能菜单解包失败!");
    }
  }
}

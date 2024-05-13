package oceanstars.ecommerce.ecm.application.content.cqrs.strategy.impl;

import com.google.protobuf.Any;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmContentMenu;
import oceanstars.ecommerce.ecm.application.content.cqrs.strategy.ContentRawDataStrategy;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentMenuType;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentMenuValueObject;

/**
 * 内容原始信息处理策略：功能菜单
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:26
 */
public class MenuStrategy implements ContentRawDataStrategy {

  @Override
  public ValueObject unpack(Any message) {
    try {
      final EcmContentMenu menu = message.unpack(EcmContentMenu.class);

      final ContentMenuValueObject.Builder rawDataBuild = ContentMenuValueObject.newBuilder()
          // 菜单类型
          .type(ContentMenuType.of(menu.getType()))
          // 菜单对应的功能ID
          .func(menu.getFunc())
          // 菜单动作：点击菜单后的执行脚本
          .action(menu.getAction())
          // 菜单图标
          .icon(menu.getIcon())
          // 菜单是否可见
          .visible(menu.getVisible());
      // 菜单隶属
      if (0L != menu.getParent()) {
        rawDataBuild.parent(menu.getParent());
      }

      return rawDataBuild.build();

    } catch (Exception e) {
      throw new IllegalArgumentException("内容原生数据菜单解包失败!");
    }
  }
}

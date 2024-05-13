package oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy.impl;

import com.google.protobuf.Message;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateContentRequestMessage;
import oceanstars.ecommerce.ecm.api.rest.v1.request.content.CreateMenuRequestMessage;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmContentMenu;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.controller.v1.content.restful.strategy.ContentRequestMessageStrategy;

/**
 * 菜单Restful请求消息处理策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/7 17:12
 */
public class MenuStrategy implements ContentRequestMessageStrategy {

  @Override
  public CreateContentRequestMessage<? extends Message> create(RestRequestMessage message) {

    // 转换请求消息
    final CreateMenuRequestMessage requestMessage = (CreateMenuRequestMessage) message;

    // 创建内容请求消息
    final CreateContentRequestMessage<EcmContentMenu> contentRequestMessage = new CreateContentRequestMessage<>();
    // 内容名称
    contentRequestMessage.setName(requestMessage.getName());
    // 内容类型
    contentRequestMessage.setType(ContentType.MENU.key());
    // 内容展示名称
    contentRequestMessage.setDisplayName(requestMessage.getDisplayName());
    // 内容描述
    contentRequestMessage.setDescription(requestMessage.getDescription());
    // 内容标签
    contentRequestMessage.setTags(requestMessage.getTags());
    // 内容分类
    contentRequestMessage.setCategories(requestMessage.getCategories());

    // 初始化菜单原生内容构建器
    final EcmContentMenu.Builder rawDataBuilder = EcmContentMenu.newBuilder()
        // 菜单类型
        .setType(requestMessage.getType())
        // 菜单对应的功能ID
        .setFunc(requestMessage.getFunc())
        // 菜单动作
        .setAction(requestMessage.getAction())
        // 菜单图标
        .setIcon(requestMessage.getIcon())
        // 菜单是否可见
        .setVisible(requestMessage.getVisible());
    // 菜单隶属
    if (null != requestMessage.getParent()) {
      rawDataBuilder.setParent(requestMessage.getParent());
    }

    // 资产原生信息设定
    contentRequestMessage.setRawData(rawDataBuilder.build());

    return contentRequestMessage;
  }
}

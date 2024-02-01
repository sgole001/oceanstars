package oceanstars.ecommerce.ecm.application.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.EventGateway;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.content.EcmCreateContentResult;
import oceanstars.ecommerce.ecm.application.cqrs.handler.strategy.impl.ContentRawDataStrategyContext;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.repository.ContentRepository;
import org.springframework.stereotype.Component;

/**
 * 创建内容命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:00
 */
@Component
public class CreateContentCommandHandler implements ICommandHandler<EcmCreateContentResult, EcmCreateContentCommand> {

  /**
   * 事件总线
   */
  private final EventGateway eventGateway;

  /**
   * 内容资源库
   */
  private final ContentRepository contentRepository;

  /**
   * 构造函数
   *
   * @param eventGateway      事件总线
   * @param contentRepository 内容资源库
   */
  public CreateContentCommandHandler(EventGateway eventGateway, ContentRepository contentRepository) {
    this.eventGateway = eventGateway;
    this.contentRepository = contentRepository;
  }

  @Override
  public EcmCreateContentResult handle(EcmCreateContentCommand command) {

    // 获取内容类型
    final ContentType contentType = ContentType.of(command.getType());
    // 构建内容元数据实体
    final Content content = Content.newBuilder(command.getName(), contentType)
        // 内容展示名称
        .displayName(command.getDisplayName())
        // 内容描述
        .description(command.getDescription())
        // 内容标签
        .tags(command.getTagsList())
        // 内容分类
        .categories(command.getCategoriesList())
        // 审核流程状态
        .status(AuditProcessStatus.DRAFT)
        // 实施构建
        .build();
    // 加载内容原始数据
    content.setRaw(new ContentRawDataStrategyContext(command.getType()).unpack(command.getRawData(), content.getDelegator().getId()));

    // 创建内容
    contentRepository.save(content);
    // 发布内容创建事件
//    eventGateway.publish(new ContentCreated(content, new ContentCreatedPayload(content.getDelegator().getId())));

    return EcmCreateContentResult.newBuilder().setId(content.getDelegator().getId()).build();
  }
}

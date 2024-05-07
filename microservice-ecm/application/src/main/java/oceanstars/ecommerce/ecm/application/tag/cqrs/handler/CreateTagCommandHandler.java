package oceanstars.ecommerce.ecm.application.tag.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.tag.EcmCreateTagResult;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.domain.tag.entity.Tag;
import oceanstars.ecommerce.ecm.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Component;

/**
 * 创建标签命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 09:56
 */
@Component
public class CreateTagCommandHandler implements ICommandHandler<EcmCreateTagResult, EcmCreateTagCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 标签资源库
   */
  private final TagRepository tagRepository;

  /**
   * 构造函数
   *
   * @param eventGateway  事件总线
   * @param tagRepository 标签资源库
   */
  public CreateTagCommandHandler(EventBus eventGateway, TagRepository tagRepository) {
    this.eventGateway = eventGateway;
    this.tagRepository = tagRepository;
  }


  @Override
  public EcmCreateTagResult handle(EcmCreateTagCommand command) {

    // 构建标签实体
    final Tag tag = Tag.newBuilder(command.getName())
        // 标签展示名称
        .displayName(command.getDisplayName())
        // 标签描述
        .description(command.getDescription())
        // 标签URL
        .url(command.getUrl())
        // 标签图标
        .icon(command.getIcon())
        // 审核流程状态
        .status(AuditProcessStatus.DRAFT)
        // 构建标签实体
        .build();

    // 保存标签
    this.tagRepository.save(tag);
    // 发布标签创建事件
//    this.eventGateway.publish(new TagCreated(tag, new TagCreatedPayload(tag.getDelegator().getId())));

    return EcmCreateTagResult.newBuilder().setId(tag.getDelegator().getId()).build();
  }
}

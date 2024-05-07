package oceanstars.ecommerce.ecm.application.category.cqrs.handler;

import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryCommand;
import oceanstars.ecommerce.ecm.api.rpc.v1.dto.category.EcmCreateCategoryResult;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.domain.category.entity.Category;
import oceanstars.ecommerce.ecm.domain.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 创建分类命令处理器
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 09:56
 */
@Component
public class CreateCategoryCommandHandler implements ICommandHandler<EcmCreateCategoryResult, EcmCreateCategoryCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 分类资源库
   */
  private final CategoryRepository categoryRepository;

  /**
   * 构造函数
   *
   * @param eventGateway       事件总线
   * @param categoryRepository 分类资源库
   */
  public CreateCategoryCommandHandler(EventBus eventGateway, CategoryRepository categoryRepository) {
    this.eventGateway = eventGateway;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public EcmCreateCategoryResult handle(EcmCreateCategoryCommand command) {

    // 构建分类实体
    final Category category = Category.newBuilder(command.getName())
        // 分类展示名称
        .displayName(command.getDisplayName())
        // 分类描述
        .description(command.getDescription())
        // 分类URL
        .url(command.getUrl())
        // 审核流程状态
        .status(AuditProcessStatus.DRAFT)
        // 构建分类实体
        .build();

    // 查询父级分类
    if (!CollectionUtils.isEmpty(command.getParentsList())) {
//      category.setParents(this.categoryRepository.findByDelegatorIds(command.getParentsList()));
    }

    // 保存分类
    this.categoryRepository.save(category);
    // 发布分类创建事件
//    eventGateway.publish(new CategoryCreated(category, new CategoryCreatedPayload(category.getDelegator().getId())));

    return EcmCreateCategoryResult.newBuilder().setId(category.getDelegator().getId()).build();
  }
}

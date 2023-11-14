package oceanstars.ecommerce.user.application.cqrs.handler;

import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.CreateResourceTypeResult;
import oceanstars.ecommerce.user.api.rpc.v1.dto.ResourceLink;
import oceanstars.ecommerce.user.domain.resource.entity.ResourceTypeEntity;
import oceanstars.ecommerce.user.domain.resource.entity.valueobject.ResourceLinkValueObject;
import oceanstars.ecommerce.user.domain.resource.repository.IResourceRepository;
import org.springframework.stereotype.Component;

/**
 * 创建权限资源类型命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/17 1:52 PM
 */
@Component(value = "createResourceTypeCommandHandler")
public class CreateResourceTypeCommandHandler implements ICommandHandler<CreateResourceTypeResult, CreateResourceTypeCommand> {

  /**
   * 权限资源聚合仓储接口
   */
  @Resource(name = "resourceRepository")
  private IResourceRepository resourceRepository;

  @Override
  public CreateResourceTypeResult handle(CreateResourceTypeCommand command) {

    // 获取资源数据获取链接信息
    final ResourceLink resourceLink = command.getLink();

    // 构建资源数据获取链接值对象信息
    final ResourceLinkValueObject resourceLinkValueObject = ResourceLinkValueObject.newBuilder()
        // 资源数据API路径
        .href(resourceLink.getHref())
        // 资源数据API的HTTP请求方法
        .method(resourceLink.getMethod())
        // 资源数据PI的HTTP请求Body(JSON字符串)
        .body(resourceLink.getBody())
        // 实施构建
        .build();

    // 构建权限资源类型实体信息
    final ResourceTypeEntity resourceTypeEntity = ResourceTypeEntity.newBuilder(command.getName())
        // 资源类型功能说明
        .desc(command.getDesc())
        // 操作逻辑有效标志位
        .enabled(Boolean.TRUE)
        // 资源数据获取链接
        .link(resourceLinkValueObject)
        // 实施构建
        .build();

    // 创建权限资源类型数据
    resourceRepository.createResourceType(resourceTypeEntity);

    return CreateResourceTypeResult.newBuilder().setIdentifier(resourceTypeEntity.getIdentifier().toString()).build();
  }
}

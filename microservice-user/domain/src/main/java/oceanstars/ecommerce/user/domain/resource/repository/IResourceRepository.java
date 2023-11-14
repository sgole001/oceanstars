package oceanstars.ecommerce.user.domain.resource.repository;

import java.util.List;
import oceanstars.ecommerce.user.domain.resource.entity.ResourceTypeEntity;
import oceanstars.ecommerce.user.domain.resource.entity.ResourceTypeIdentifier;

/**
 * 权限资源聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/10 10:44 PM
 */
public interface IResourceRepository {

  /**
   * 创建权限资源类型数据
   *
   * @param resourceTypeEntity 预创建权限资源类型实体信息
   */
  void createResourceType(final ResourceTypeEntity resourceTypeEntity);

  /**
   * 更新权限资源类型数据
   *
   * @param resourceTypeEntity 预更新权限资源类型实体信息
   */
  void updateResourceType(final ResourceTypeEntity resourceTypeEntity);

  /**
   * 禁用指定权限资源类型数据
   *
   * @param identifiers 权限资源类型唯一识别符对象列表
   */
  void disableResourceType(final List<ResourceTypeIdentifier> identifiers);
}

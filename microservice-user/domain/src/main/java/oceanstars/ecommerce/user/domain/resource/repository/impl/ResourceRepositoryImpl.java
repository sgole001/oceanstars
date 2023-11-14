package oceanstars.ecommerce.user.domain.resource.repository.impl;

import java.util.List;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums;
import oceanstars.ecommerce.user.domain.resource.entity.ResourceTypeEntity;
import oceanstars.ecommerce.user.domain.resource.entity.ResourceTypeIdentifier;
import oceanstars.ecommerce.user.domain.resource.entity.valueobject.ResourceLinkValueObject;
import oceanstars.ecommerce.user.domain.resource.repository.IResourceRepository;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserResourceTypeDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserResourceTypePojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 权限资源聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/11 10:24 AM
 */
@Component(value = "resourceRepository")
public class ResourceRepositoryImpl implements IResourceRepository {

  /**
   * 权限资源类型数据访问对象
   */
  @Resource
  private UserResourceTypeDao resourceTypeDao;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(ResourceRepositoryImpl.class);

  @Override
  public void createResourceType(ResourceTypeEntity resourceTypeEntity) {

    // 获取权限资源类型唯一识别符
    final String identifier = resourceTypeEntity.getIdentifier().toString();
    // 根据权限资源类型唯一识别符获取权限资源类型数据
    UserResourceTypePojo resourceTypePojo = this.resourceTypeDao.fetchOneByCode(identifier);

    if (null != resourceTypePojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00002, identifier);
    }

    // 根据权限资源类型实体构建权限操作数据库映射
    resourceTypePojo = this.fromResourceTypeEntity(resourceTypeEntity);

    // 插入数据库
    this.resourceTypeDao.insert(resourceTypePojo);
  }

  @Override
  public void updateResourceType(ResourceTypeEntity resourceTypeEntity) {

    // 获取权限资源类型唯一识别符
    final String identifier = resourceTypeEntity.getIdentifier().toString();

    // 根据权限资源类型唯一识别符获取权限资源类型数据
    UserResourceTypePojo resourceTypePojo = this.resourceTypeDao.fetchOneByCode(identifier);

    if (null == resourceTypePojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00003, identifier);
    }

    // 更新权限资源类型描述
    if (null != resourceTypeEntity.getDesc()) {
      resourceTypePojo.setDesc(resourceTypeEntity.getDesc());
    }
    // 更新权限资源类型数据链接
    if (null != resourceTypeEntity.getLink()) {

      // 更新资源数据API路径
      if (null != resourceTypeEntity.getLink().getHref()) {
        resourceTypePojo.setHref(resourceTypeEntity.getLink().getHref());
      }
      // 更新资源数据API的HTTP请求方法
      if (null != resourceTypeEntity.getLink().getMethod()) {
        resourceTypePojo.setMethod(resourceTypeEntity.getLink().getMethod());
      }
      // 更新资源数据PI的HTTP请求Body(JSON字符串)
      if (null != resourceTypeEntity.getLink().getBody()) {
        resourceTypePojo.setBody(resourceTypeEntity.getLink().getBody());
      }
    }

    // 更新数据库
    this.resourceTypeDao.update(resourceTypePojo);
  }

  @Override
  public void disableResourceType(List<ResourceTypeIdentifier> identifiers) {

    identifiers.forEach(identifier -> {

      // 根据权限资源类型唯一识别符获取权限操作数据
      UserResourceTypePojo resourceTypePojo = this.resourceTypeDao.fetchOneByCode(identifier.toString());

      if (null == resourceTypePojo) {
        logger.warn(UserEnums.Message.MSG_BIZ_00002.value(new String[]{identifier.toString()}));
        return;
      }

      // 更新权限资源类型逻辑有效标志位
      resourceTypePojo.setEnabled(Boolean.FALSE);

      this.resourceTypeDao.update(resourceTypePojo);
    });
  }

  /**
   * 根据权限资源类型实体构建权限资源类型数据库映射
   *
   * @param resourceTypeEntity 权限资源类型实体信息
   * @return 权限资源类型数据库映射
   */
  private UserResourceTypePojo fromResourceTypeEntity(final ResourceTypeEntity resourceTypeEntity) {

    // 初始化权限资源数据库映射
    final UserResourceTypePojo resourceTypePojo = new UserResourceTypePojo();
    // 权限资源编码
    resourceTypePojo.setCode(resourceTypeEntity.getIdentifier().toString());
    // 权限资源名
    resourceTypePojo.setName(resourceTypeEntity.getName());
    // 权限资源说明
    resourceTypePojo.setDesc(resourceTypeEntity.getDesc());
    // 资源数据API路径
    resourceTypePojo.setHref(resourceTypeEntity.getLink().getHref());
    // 资源数据API的HTTP请求方法
    resourceTypePojo.setMethod(resourceTypeEntity.getLink().getMethod());
    // 资源数据PI的HTTP请求Body(JSON字符串)
    resourceTypePojo.setBody(resourceTypeEntity.getLink().getBody());
    // 权限资源逻辑有效标志位
    resourceTypePojo.setEnabled(resourceTypeEntity.getEnabled());

    return resourceTypePojo;
  }

  /**
   * 根据权限资源类型数据库映射构建权限资源类型实体
   *
   * @param resourceTypePojo 权限资源类型数据库映射
   * @return 权限资源类型实体信息
   */
  private ResourceTypeEntity toResourceTypeEntity(final UserResourceTypePojo resourceTypePojo) {

    // 构建权限资源数据链接值对象
    final ResourceLinkValueObject resourceLinkValueObject = ResourceLinkValueObject.newBuilder()
        // 资源数据API路径
        .href(resourceTypePojo.getHref())
        // 资源数据API的HTTP请求方法
        .method(resourceTypePojo.getMethod())
        // 资源数据PI的HTTP请求Body(JSON字符串)
        .body(resourceTypePojo.getBody())
        // 执行构建
        .build();

    // 构建权限资源类型实体
    final ResourceTypeEntity resourceTypeEntity = ResourceTypeEntity.newBuilder(resourceTypePojo.getName())
        // 权限资源说明
        .desc(resourceTypePojo.getDesc())
        // 权限资源数据链接
        .link(resourceLinkValueObject)
        // 权限资源逻辑有效标志位
        .enabled(resourceTypePojo.getEnabled())
        // 执行构建
        .build();

    // 配置委托者
    resourceTypeEntity.delegate(resourceTypePojo);

    return resourceTypeEntity;
  }
}

package oceanstars.ecommerce.user.domain.permission.repository.impl;

import java.util.List;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionEntity;
import oceanstars.ecommerce.user.domain.permission.entity.PermissionIdentifier;
import oceanstars.ecommerce.user.domain.permission.repository.IPermissionRepository;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelPermissionResourceDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserPermissionDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 权限聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/11 2:48 PM
 */
public class PermissionRepositoryImpl implements IPermissionRepository {

  /**
   * 权限数据访问对象
   */
  @Resource
  private UserPermissionDao permissionDao;

  /**
   * 权限资源映射关系数据访问对象
   */
  @Resource
  private RelPermissionResourceDao permissionResourceDao;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(PermissionRepositoryImpl.class);

  @Override
  public void createPermission(PermissionEntity permissionEntity) {

    // 获取权限唯一识别符
    final String identifier = permissionEntity.getIdentifier().toString();
    // 根据权限资源类型唯一识别符获取权限资源类型数据
    UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(identifier);

    if (null != permissionPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00004, identifier);
    }

    // 根据权限资源类型实体构建权限操作数据库映射
    permissionPojo = this.fromPermissionEntity(permissionEntity);
    // 插入数据库
    this.permissionDao.insert(permissionPojo);
  }

  @Override
  public void updatePermission(PermissionEntity permissionEntity) {

    // 获取权限唯一识别符
    final String identifier = permissionEntity.getIdentifier().toString();

    // 根据权限唯一识别符获取权限数据
    final UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(identifier);

    if (null == permissionPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00005, identifier);
    }

    // 更新权限描述
    permissionPojo.setDesc(permissionEntity.getDesc());

    // 更新数据库
    this.permissionDao.update(permissionPojo);
  }

  @Override
  public void disablePermission(List<PermissionIdentifier> identifiers) {

    identifiers.forEach(identifier -> {

      // 根据权限操作唯一识别符获取权限操作数据
      UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(identifier.toString());

      if (null == permissionPojo) {
        logger.warn(UserEnums.Message.MSG_BIZ_00005.value(new String[]{identifier.toString()}));
        return;
      }

      // 更新权限操作逻辑有效标志位
      permissionPojo.setEnabled(Boolean.FALSE);

      this.permissionDao.update(permissionPojo);
    });
  }

  /**
   * 根据权限实体构建权限数据库映射
   *
   * @param permissionEntity 权限实体信息
   * @return 权限数据库映射
   */
  private UserPermissionPojo fromPermissionEntity(final PermissionEntity permissionEntity) {

    // 初始化权限数据库映射对象
    final UserPermissionPojo permissionPojo = new UserPermissionPojo();

    // 权限编号
    permissionPojo.setCode(permissionEntity.getIdentifier().toString());
    // 权限名
    permissionPojo.setName(permissionEntity.getName());
    // 权限类型
    permissionPojo.setType(permissionEntity.getType().key().shortValue());
    // 权限描述
    permissionPojo.setDesc(permissionEntity.getDesc());
    // 权限逻辑有效标志位
    if (null == permissionEntity.getEnabled()) {
      permissionPojo.setEnabled(Boolean.TRUE);
    } else {
      permissionPojo.setEnabled(permissionEntity.getEnabled());
    }

    return permissionPojo;
  }

  /**
   * 根据权限数据库映射构建权限实体
   *
   * @param permissionPojo 权限数据库映射
   * @return 权限实体
   */
  private PermissionEntity toPermissionEntity(final UserPermissionPojo permissionPojo) {

    // 构建权限实体
    final PermissionEntity permissionEntity = PermissionEntity.newBuilder(
            // 权限名
            permissionPojo.getName(),
            // 权限类型
            UserEnums.PermissionType.of(permissionPojo.getType().intValue()))
        // 权限描述
        .desc(permissionPojo.getDesc())
        // 权限逻辑有效标志位
        .enabled(permissionPojo.getEnabled())
        // 执行构建
        .build();

    // 配置委托者
    permissionEntity.delegate(permissionPojo);

    return permissionEntity;
  }
}

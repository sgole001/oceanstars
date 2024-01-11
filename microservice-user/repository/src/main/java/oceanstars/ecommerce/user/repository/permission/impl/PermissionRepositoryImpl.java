package oceanstars.ecommerce.user.repository.permission.impl;

import jakarta.annotation.Resource;
import java.util.List;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelPermissionResourceDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserPermissionDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelPermissionResourcePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionPojo;
import oceanstars.ecommerce.user.repository.permission.IPermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 权限聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/11 2:48 PM
 */
@Repository
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
  public void createPermission(UserPermissionPojo permission) {

    // 根据权限资源类型唯一识别符获取权限资源类型数据
    UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(permission.getCode());

    if (null != permissionPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00004, permission.getCode());
    }

    // 插入数据库
    this.permissionDao.insert(permission);
  }

  @Override
  public void updatePermission(UserPermissionPojo permission) {

    // 根据权限唯一识别符获取权限数据
    final UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(permission.getCode());

    if (null == permissionPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00005, permission.getCode());
    }

    // 更新数据库
    this.permissionDao.update(permission);
  }

  @Override
  public void disablePermission(List<String> ids) {

    ids.forEach(id -> {

      // 根据权限操作唯一识别符获取权限操作数据
      UserPermissionPojo permissionPojo = this.permissionDao.fetchOneByCode(id);

      if (null == permissionPojo) {
        logger.warn(UserEnums.Message.MSG_BIZ_00005.value(new String[]{id}));
        return;
      }

      // 更新权限操作逻辑有效标志位
      permissionPojo.setEnabled(Boolean.FALSE);

      this.permissionDao.update(permissionPojo);
    });
  }

  @Override
  public void assignPermissionBehavior(List<RelPermissionResourcePojo> behaviors) {

    this.permissionResourceDao.insert(behaviors);
  }
}

package oceanstars.ecommerce.user.domain.operation.repository.impl;

import java.util.List;
import jakarta.annotation.Resource;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.user.constant.enums.UserEnums;
import oceanstars.ecommerce.user.domain.operation.entity.OperationEntity;
import oceanstars.ecommerce.user.domain.operation.entity.OperationIdentifier;
import oceanstars.ecommerce.user.domain.operation.repository.IOperationRepository;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserPermissionOperationDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserPermissionOperationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 权限操作聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/10 5:24 PM
 */
@Component(value = "operationRepository")
public class OperationRepositoryImpl implements IOperationRepository {

  /**
   * 权限操作数据的数据访问对象
   */
  @Resource
  private UserPermissionOperationDao operationDao;

  /**
   * 日志管理器
   */
  private final Logger logger = LoggerFactory.getLogger(OperationRepositoryImpl.class);

  @Override
  public void createOperation(OperationEntity operationEntity) {

    // 获取权限操作唯一识别符
    final String identifier = operationEntity.getIdentifier().toString();
    // 根据权限操作唯一识别符获取权限操作数据
    UserPermissionOperationPojo operationPojo = this.operationDao.fetchOneByCode(identifier);

    if (null != operationPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00000, identifier);
    }

    // 根据权限操作实体构建权限操作数据库映射
    operationPojo = this.fromOperationEntity(operationEntity);

    // 插入数据库
    this.operationDao.insert(operationPojo);

  }

  @Override
  public void updateOperation(OperationEntity operationEntity) {

    // 获取权限操作唯一识别符
    final String identifier = operationEntity.getIdentifier().toString();

    // 根据权限操作唯一识别符获取权限操作数据
    UserPermissionOperationPojo operationPojo = this.operationDao.fetchOneByCode(identifier);

    if (null == operationPojo) {
      throw new BusinessException(UserEnums.Message.MSG_BIZ_00001, identifier);
    }

    // 更新权限操作描述
    operationPojo.setDesc(operationEntity.getDesc());

    // 更新数据库
    this.operationDao.update(operationPojo);
  }

  @Override
  public void disableOperation(List<OperationIdentifier> identifiers) {

    identifiers.forEach(identifier -> {

      // 根据权限操作唯一识别符获取权限操作数据
      UserPermissionOperationPojo operationPojo = this.operationDao.fetchOneByCode(identifier.toString());

      if (null == operationPojo) {
        logger.warn(UserEnums.Message.MSG_BIZ_00001.value(new String[]{identifier.toString()}));
        return;
      }

      // 更新权限操作逻辑有效标志位
      operationPojo.setEnabled(Boolean.FALSE);

      this.operationDao.update(operationPojo);
    });
  }

  /**
   * 根据权限操作实体构建权限操作数据库映射
   *
   * @param operationEntity 权限操作实体信息
   * @return 权限操作数据库映射
   */
  private UserPermissionOperationPojo fromOperationEntity(final OperationEntity operationEntity) {

    // 初始化权限操作数据库映射
    final UserPermissionOperationPojo operationPojo = new UserPermissionOperationPojo();
    // 权限操作编码-自然键
    operationPojo.setCode(operationEntity.getIdentifier().toString());
    // 权限操作行为
    operationPojo.setBehavior(operationEntity.getBehavior());
    // 权限操作说明
    operationPojo.setDesc(operationEntity.getDesc());

    return operationPojo;
  }

  /**
   * 权限操作数据库映射构建根据权限操作实体
   *
   * @param operationPojo 权限操作数据库映射
   * @return 根据权限操作实体
   */
  private OperationEntity toOperationEntity(final UserPermissionOperationPojo operationPojo) {

    // 构建权限操作数据实体
    final OperationEntity operationEntity = OperationEntity.newBuilder(operationPojo.getBehavior())
        // 权限操作说明
        .desc(operationPojo.getDesc())
        // 权限操作逻辑有效标志位
        .enabled(operationPojo.getEnabled())
        // 执行构建
        .build();

    // 配置委托者
    operationEntity.delegate(operationPojo);

    return operationEntity;
  }
}

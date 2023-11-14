package oceanstars.ecommerce.user.domain.operation.repository;

import java.util.List;
import oceanstars.ecommerce.user.domain.operation.entity.OperationEntity;
import oceanstars.ecommerce.user.domain.operation.entity.OperationIdentifier;

/**
 * 权限操作聚合仓储接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/10 5:13 PM
 */
public interface IOperationRepository {

  /**
   * 创建权限操作数据
   *
   * @param operationEntity 预创建的权限操作实体信息
   */
  void createOperation(final OperationEntity operationEntity);

  /**
   * 更新权限操作数据
   *
   * @param operationEntity 预更新的权限操作实体信息
   */
  void updateOperation(final OperationEntity operationEntity);

  /**
   * 禁用权限操作数据
   *
   * @param identifiers 预禁用的权限操作唯一识别符对象列表
   */
  void disableOperation(final List<OperationIdentifier> identifiers);
}

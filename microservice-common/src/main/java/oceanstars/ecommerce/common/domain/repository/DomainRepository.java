package oceanstars.ecommerce.common.domain.repository;

import java.util.List;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;

/**
 * 领域资源库接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 14:15
 */
public interface DomainRepository<T extends AggregateRoot<?>> {

  /**
   * 根据条件查找聚合根(唯一查询结果)
   *
   * @param condition 查询条件
   * @return 聚合根
   */
  Optional<T> findOne(ICondition condition);

  /**
   * 根据条件查找聚合根(复数查询结果)
   *
   * @param condition 查询条件
   * @return 聚合根集合
   */
  List<T> find(ICondition condition);

  /**
   * 保存聚合根
   *
   * @param aggregator 聚合根
   */
  void save(T aggregator);

  /**
   * 删除聚合根
   *
   * @param aggregator 聚合根
   */
  void delete(T aggregator);

}

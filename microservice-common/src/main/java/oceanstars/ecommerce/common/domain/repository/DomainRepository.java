package oceanstars.ecommerce.common.domain.repository;

import java.util.List;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import org.springframework.util.CollectionUtils;

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
  default Optional<T> findOne(ICondition condition) {

    // 根据条件查找聚合根(唯一查询结果)
    final List<T> result = this.find(condition);

    // 如果查询结果为空，则返回null
    if (CollectionUtils.isEmpty(result)) {
      return Optional.empty();
    }

    // 如果查询结果不唯一，则抛出异常
    if (result.size() > 1) {
      throw new RuntimeException("查询结果不唯一");
    }

    return Optional.of(result.getFirst());
  }

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

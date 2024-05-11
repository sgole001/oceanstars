package oceanstars.ecommerce.common.domain.repository;

import java.util.List;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.domain.entity.EntityDelegator;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import org.springframework.util.CollectionUtils;

/**
 * 领域资源库基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/10 18:00
 */
public abstract class BaseDomainRepository<T extends AggregateRoot<?>> implements DomainRepository<T> {

  @Override
  public Optional<T> findOne(ICondition condition) {

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

  @Override
  public void save(T aggregator) {

    // 获取实体代理对象
    final EntityDelegator delegator = aggregator.getDelegator();

    // 判断实体代理对象是否为空
    if (null == delegator) {
      // 创建聚合根
      this.create(aggregator);
    } else {
      // 更新聚合根
      this.modify(aggregator);
    }
  }

  /**
   * 创建聚合根
   *
   * @param aggregator 聚合根
   */
  protected abstract void create(T aggregator);

  /**
   * 更新聚合根
   *
   * @param aggregator 聚合根
   */
  protected abstract void modify(T aggregator);
}

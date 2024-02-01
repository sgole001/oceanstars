package oceanstars.ecommerce.common.domain;

import java.util.List;
import java.util.Optional;

/**
 * 领域资源库接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 14:15
 */
public interface DomainRepository<I extends IEntityIdentifier<?>, T extends AggregateRoot<I>> {

  /**
   * 根据标识符查找聚合根
   *
   * @param identifier 标识符
   * @return 聚合根
   */
  Optional<T> findByIdentifier(I identifier);

  /**
   * 根据委托者查找聚合根
   *
   * @param delegator 委托者
   * @return 聚合根
   */
  Optional<T> findByDelegator(EntityDelegator delegator);

  /**
   * 根据委托者标识符查找聚合根
   *
   * @param id 委托者标识符
   * @return 聚合根
   */
  Optional<T> findByDelegator(Long id);

  /**
   * 根据条件查找聚合根
   *
   * @param conditions 查询条件
   * @return 聚合根集合
   */
  List<T> find(T conditions);

  /**
   * 根据委托者集合查找聚合根集合
   *
   * @param delegators 委托者集合
   * @return 聚合根集合
   */
  List<T> findByDelegators(List<EntityDelegator> delegators);

  /**
   * 根据委托者标识符集合查找聚合根集合
   *
   * @param ids 委托者标识符集合
   * @return 聚合根集合
   */
  List<T> findByDelegatorIds(List<Long> ids);

  /**
   * 根据标识符集合查找聚合根集合
   *
   * @param identifiers 标识符集合
   * @return 聚合根集合
   */
  List<T> findByIdentifiers(List<I> identifiers);

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

package oceanstars.ecommerce.common.domain.entity;

/**
 * 领域模型聚合根基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/2 2:58 下午
 */
public class AggregateRoot<T extends IEntityIdentifier<?>> extends Entity<T> {

  /**
   * 构造函数: 初始化领域实体唯一标识符
   *
   * @param identifier 领域实体唯一标识符
   */
  protected AggregateRoot(T identifier) {
    super(identifier);
  }
}

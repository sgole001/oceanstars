package oceanstars.ecommerce.common.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import oceanstars.ecommerce.common.tools.ReflectUtil;

/**
 * 领域模型实体基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/2 2:57 下午
 */
@SuppressWarnings("unchecked")
public class Entity<T extends IEntityIdentifier<?>> implements IEntity {

  /**
   * 领域实体唯一标识符（自然键-业务可见）
   */
  protected final T identifier;

  /**
   * 领域实体委托者
   */
  private EntityDelegator delegator;

  /**
   * 构造函数: 初始化领域实体唯一标识符
   *
   * @param identifier 领域实体唯一标识符
   */
  protected Entity(T identifier) {
    this.identifier = identifier;
//    this.delegator = EntityDelegator.newBuilder(PkWorker.build().nextId()).build();
  }

  public T getIdentifier() {
    return identifier;
  }

  public EntityDelegator getDelegator() {
    return delegator;
  }

  /**
   * 委托实体对象
   *
   * @param delegatePojo 委托者信息
   */
  public void delegate(Object delegatePojo) {

    if (delegatePojo instanceof EntityDelegator entityDelegator) {
      this.delegator = entityDelegator;
    } else {
      // 反射持久层数据对象，获取委托者数据
      final Long id = (Long) ReflectUtil.getFieldValue(delegatePojo, "id");
      final String createBy = (String) ReflectUtil.getFieldValue(delegatePojo, "createBy");
      final LocalDateTime createAt = (LocalDateTime) ReflectUtil.getFieldValue(delegatePojo, "createAt");
      final String updateBy = (String) ReflectUtil.getFieldValue(delegatePojo, "updateBy");
      final LocalDateTime updateAt = (LocalDateTime) ReflectUtil.getFieldValue(delegatePojo, "updateAt");
      final Integer version = (Integer) ReflectUtil.getFieldValue(delegatePojo, "version");

      this.delegator = EntityDelegator.newBuilder(id, createAt, createBy)
          .updateAt(updateAt)
          .updateBy(updateBy)
          .version(version).build();
    }
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    // 如果比较对象类型不同
    if (this.getClass() != obj.getClass()) {
      return false;
    }

    return this.identifier.equals(((Entity<T>) obj).identifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identifier);
  }

  @Override
  public String toString() {
    return getClass().getName() + "@" + this.identifier.toString();
  }
}

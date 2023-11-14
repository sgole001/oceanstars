package oceanstars.ecommerce.common.domain;

import java.io.Serial;
import java.time.Clock;
import org.springframework.context.ApplicationEvent;

/**
 * 领域事件基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/7 2:37 下午
 */
public abstract class AbstractDomainEvent<E extends AggregateRoot<?>, D> extends ApplicationEvent implements IDomainEvent {

  @Serial
  private static final long serialVersionUID = 5553666445784506037L;

  /**
   * 事件业务数据属性
   */
  protected D data;

  /**
   * 构造函数：初始化事件源对象
   *
   * @param aggregate 领域事件聚合对象
   */
  protected AbstractDomainEvent(E aggregate) {
    super(aggregate);
    applyOn();
  }

  /**
   * 构造函数：初始化事件源对象&发生时间
   *
   * @param aggregate 领域事件聚合对象
   * @param clock     发生时间
   */
  protected AbstractDomainEvent(E aggregate, Clock clock) {
    super(aggregate, clock);
    applyOn();
  }

  @Override
  @SuppressWarnings("unchecked")
  public String getSourceId() {

    // 获取事件源对象
    final E source = (E) super.getSource();
    // 返回事件源ID
    return source.getIdentifier().toString();
  }

  @Override
  public String getSourceType() {
    return super.getSource().getClass().getTypeName();
  }

  public D getData() {
    return data;
  }

  @Override
  public String toString() {
    return withDomain() + "." + withAggregate() + "." + this.getClass().getSimpleName().toLowerCase();
  }
}

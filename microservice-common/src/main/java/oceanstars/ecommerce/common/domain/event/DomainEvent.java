package oceanstars.ecommerce.common.domain.event;

import java.io.Serial;
import java.io.Serializable;
import java.time.Clock;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.tools.PkWorker;
import org.springframework.context.ApplicationEvent;

/**
 * 领域事件基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/7 2:37 下午
 */
public class DomainEvent<E extends AggregateRoot<?>, D extends Serializable> extends ApplicationEvent {

  @Serial
  private static final long serialVersionUID = 5553666445784506037L;

  /**
   * 事件唯一标识
   */
  private final String id;

  /**
   * 事件业务数据属性
   */
  private final D data;

  /**
   * 构造函数：初始化事件源对象
   *
   * @param source 事件源对象
   * @param data   事件业务对象
   */
  protected DomainEvent(E source, D data) {
    super(source);
    this.id = getClass().getSimpleName() + "_" + PkWorker.build().nextId();
    this.data = data;
  }

  /**
   * 构造函数：初始化事件源对象&发生时间
   *
   * @param source 事件源对象
   * @param data   事件业务对象
   * @param clock  发生时间
   */
  protected DomainEvent(E source, D data, Clock clock) {
    super(source, clock);
    this.id = getClass().getSimpleName() + "_" + PkWorker.generateWorkerId();
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public D getData() {
    return data;
  }
}

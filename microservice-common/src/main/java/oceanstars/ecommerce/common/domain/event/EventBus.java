package oceanstars.ecommerce.common.domain.event;

/**
 * 事件总线接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/11 11:46
 */
public interface EventBus {

  /**
   * 发布领域事件
   *
   * @param event 领域事件
   * @return 处理结果
   */
  <R, E extends DomainEvent<?, ?>> R publish(E event);
}

package oceanstars.ecommerce.common.domain;

/**
 * 事件发布接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/10 14:21
 */
public interface IEventPublisher<R, E extends DomainEvent<?, ?>> {

  /**
   * 发布领域事件
   *
   * @param event 领域事件
   * @return 处理结果
   */
  R publish(E event);
}

package oceanstars.ecommerce.message.domain.aggregate.event.service;

/**
 * 事件领域服务接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/14 2:51 下午
 */
public interface IEventDomainService {

  /**
   * 事件对账（补偿未能正常发送或消费的事件）
   */
  void reconcileEvent();
}

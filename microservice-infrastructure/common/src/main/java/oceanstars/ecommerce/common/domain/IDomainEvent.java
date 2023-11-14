package oceanstars.ecommerce.common.domain;

/**
 * 领域事件接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/10 12:20 上午
 */
public interface IDomainEvent {

  /**
   * 事件所在领域
   *
   * @return 事件所在领域名
   */
  String withDomain();

  /**
   * 事件所在聚合
   *
   * @return 事件所在聚合名
   */
  String withAggregate();

  /**
   * 获取事件源对象ID
   *
   * @return 事件源对象ID
   */
  String getSourceId();

  /**
   * 获取事件源类型
   *
   * @return 事件源类型
   */
  String getSourceType();

  /**
   * 聚合根申请领域事件
   */
  void applyOn();
}

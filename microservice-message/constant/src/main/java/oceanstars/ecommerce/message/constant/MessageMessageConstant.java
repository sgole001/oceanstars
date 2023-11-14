package oceanstars.ecommerce.message.constant;

/**
 * 消息服务消息码常量接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:20 下午
 */
public interface MessageMessageConstant {

  /**
   * Kafka消息Topic为空
   */
  String MSG_SYS_00000 = "MSG_SYS_00000";

  /**
   * 事件编码为{0}的事件元数据不存在！
   */
  String MSG_BIZ_00000 = "MSG_BIZ_00000";

  /**
   * 事件类型为{0}的事件元数据不存在！
   */
  String MSG_BIZ_00001 = "MSG_BIZ_00001";

  /**
   * 事件总线类型为{0}的事件元数据不存在！
   */
  String MSG_BIZ_00002 = "MSG_BIZ_00002";

}

package oceanstars.ecommerce.common.constant;

/**
 * 消息码常量接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:30 下午
 */
public interface CommonMessageConstant {

  /**
   * token无效 (已过期 | 被篡改)
   */
  String MSG_COM_00000 = "MSG_COM_00000";

  /**
   * 系统繁忙，请稍后再试
   */
  String MSG_COM_00001 = "MSG_COM_00001";

  /**
   * 工作ID不能 > {0},或者 < 0!
   */
  String MSG_COM_00002 = "MSG_COM_00002";

  /**
   * 时钟回拨 {0} 毫秒.  拒绝生成ID！
   */
  String MSG_COM_00003 = "MSG_COM_00003";

  /**
   * 生成WorkerId过程中，获取IP失败！
   */
  String MSG_COM_00004 = "MSG_COM_00004";

  /**
   * DAG存在环形依赖！
   */
  String MSG_COM_00005 = "MSG_COM_00005";

  /**
   * 序列化处理异常！
   */
  String MSG_COM_00006 = "MSG_COM_00006";

  /**
   * 反序列化处理异常！
   */
  String MSG_COM_00007 = "MSG_COM_00007";

  /**
   * JWT的过期时间[{0}]必须要大于签发时间[{1}]！
   */
  String MSG_COM_00008 = "MSG_COM_00008";

  /**
   * traceId 验证未通过
   */
  String MSG_COM_00087 = "MSG_COM_00087";
}

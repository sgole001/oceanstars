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
   * 开始时间必须小于结束时间！
   */
  String MSG_COM_00081 = "MSG_COM_00081";

  /**
   * 开始时间、结束时间必须成对！
   */
  String MSG_COM_00082 = "MSG_COM_00082";

  /**
   * SQL限制执行报错
   */
  String MSG_COM_00083 = "MSG_COM_00083";

  /**
   * UPDATE|DELETE statement is missing the WHERE condition
   */
  String MSG_COM_00084 = "MSG_COM_00084";

  /**
   * 缺少用户信息
   */
  String MSG_COM_00085 = "MSG_COM_00085";

  /**
   * 缺少用户信息: sourceType
   */
  String MSG_COM_00086 = "MSG_COM_00086";

  /**
   * traceId 验证未通过
   */
  String MSG_COM_00087 = "MSG_COM_00087";

  /**
   * 数据读取失败:错误原因:{0}
   */
  String MSG_COM_00090 = "MSG_COM_00090";
}

package oceanstars.ecommerce.ecm.constant.enums;

import oceanstars.ecommerce.common.constant.IEnum;
import oceanstars.ecommerce.common.tools.MessageUtil;

/**
 * ECM服务枚举类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 10:49
 */

public class EcmEnums {

  /**
   * 消息
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/11 10:49
   */
  public enum Message implements IEnum<String, String, Message> {

    // 编码为{0}的权限操作数据已经存在！
    MSG_BIZ_00000("MSG_BIZ_00000"),
    ;

    /**
     * 枚举编号
     */
    private final String code;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     */
    Message(String code) {
      this.code = code;
    }

    @Override
    public Message get() {
      return this;
    }

    @Override
    public String key() {
      return this.code;
    }

    @Override
    public String value() {
      return MessageUtil.ACCESSOR.getMessage(this.code);
    }

    public String value(Object[] arg) {
      return MessageUtil.ACCESSOR.getMessage(this.code, arg);
    }
  }
}

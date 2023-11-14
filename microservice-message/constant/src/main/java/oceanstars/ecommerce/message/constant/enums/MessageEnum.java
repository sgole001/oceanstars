package oceanstars.ecommerce.message.constant.enums;

/**
 * 消息服务枚举管理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:19 下午
 */
public interface MessageEnum {

  /**
   * 消息总线类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/1 6:12 下午
   */
  enum MessageBus implements MessageEnum {

    /**
     * Kafka
     */
    KAFKA(0, "Kafka");

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    MessageBus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    public static MessageBus of(Integer code) {
      for (MessageBus value : MessageBus.values()) {

        if (code.equals(value.getCode())) {
          return value;
        }
      }
      return null;
    }

    public Integer getCode() {
      return code;
    }

    public String getName() {
      return name;
    }
  }
}

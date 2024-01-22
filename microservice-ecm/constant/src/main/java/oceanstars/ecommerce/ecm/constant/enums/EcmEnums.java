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

  public enum ContentType implements IEnum<Short, String, ContentType> {

    // WEB功能
    WEB_FUNCTION(Short.valueOf("0"), "WEB_FUNCTION"),
    ;

    /**
     * 枚举编号
     */
    private final Short code;

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
    ContentType(Short code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ContentType of(Short key) {
      for (ContentType item : ContentType.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public ContentType get() {
      return this;
    }

    @Override
    public Short key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  public enum ContentStatus implements IEnum<Integer, String, ContentStatus> {

    // 草稿
    DRAFT(0, "DRAFT"),

    // 待审核
    PENDING(1, "PENDING"),

    // 审核通过
    PASS(2, "PASS"),

    // 审核不通过
    REJECT(3, "REJECT"),

    // 已发布
    PUBLISHED(4, "PUBLISHED"),

    // 已下架
    OFFLINE(5, "OFFLINE"),

    // 已删除
    DELETED(6, "DELETED"),
    ;

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
    ContentStatus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ContentStatus of(Integer key) {
      for (ContentStatus item : ContentStatus.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public ContentStatus get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  public enum ContentTagStatus implements IEnum<Integer, String, ContentTagStatus> {

    // 已删除
    DELETED(0, "DELETED"),

    // 已发布
    PUBLISHED(1, "PUBLISHED"),
    ;

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
    ContentTagStatus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ContentTagStatus of(Integer key) {
      for (ContentTagStatus item : ContentTagStatus.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public ContentTagStatus get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  public enum WebFunctionType implements IEnum<Integer, String, WebFunctionType> {

    // 页面
    SITE(0, "PAGE"),

    // 内联页面（弹出|抽屉）
    CHANNEL(1, "INLINE PAGE"),

    // 跳转(外部跳转 | 内部锚点)
    PAGE(2, "HREF"),

    // 交互事件 (按钮 | KEYBOARD | MOUSE | 音视频流)
    COMPONENT(3, "EVENT"),
    ;

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
    WebFunctionType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static WebFunctionType of(Integer key) {
      for (WebFunctionType item : WebFunctionType.values()) {
        if (item.code.equals(key)) {
          return item;
        }
      }
      return null;
    }

    @Override
    public WebFunctionType get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

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

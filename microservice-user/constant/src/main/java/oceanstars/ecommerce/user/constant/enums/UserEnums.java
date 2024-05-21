package oceanstars.ecommerce.user.constant.enums;

import oceanstars.ecommerce.common.constant.IEnum;
import oceanstars.ecommerce.common.tools.MessageUtil;

/**
 * 用户服务枚举类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 1:04 PM
 */
public class UserEnums {

  /**
   * 账号注册手段
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/10 3:15 PM
   */
  public enum AccountRegisterMeans implements IEnum<Integer, String, AccountRegisterMeans> {

    // 邮箱注册
    EMAIL(0, "EMAIL"),

    // 手机注册
    MOBILE(1, "MOBILE"),

    // 第三方授权注册
    EXTERNAL(2, "EXTERNAL");

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
    AccountRegisterMeans(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AccountRegisterMeans of(Integer key) {
      for (AccountRegisterMeans value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public AccountRegisterMeans get() {
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
   * 账号注册源类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 11:06 AM
   */
  public enum AccountRegisterSource implements IEnum<Integer, String, AccountRegisterSource> {

    // 邮箱登录
    BACKOFFICE(0, "BACKOFFICE"),

    // 手机登录
    MALL(1, "MALL"),

    // 账号正常
    ALIPAY(2, "ALIPAY"),

    // 账号注销
    WECHAT(3, "WECHAT"),

    // 账号禁用
    SSO(4, "SSO");

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
    AccountRegisterSource(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AccountRegisterSource of(Integer key) {

      for (AccountRegisterSource value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public AccountRegisterSource get() {
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
   * 账号状态类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 11:12 AM
   */
  public enum AccountStatus implements IEnum<Integer, String, AccountStatus> {

    // 账号正常
    NORMAL(0, "NORMAL"),

    // 账号注销
    CANCELLATION(1, "CANCELLATION"),

    // 账号禁用
    DISABLE(2, "DISABLE");

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
    AccountStatus(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AccountStatus of(Integer key) {
      for (AccountStatus value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public AccountStatus get() {
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
   * 性别枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 11:58 AM
   */
  public enum Gender implements IEnum<Integer, String, Gender> {

    // 账号正常
    MALE(0, "男性"),

    // 账号注销
    FEMALE(1, "女性"),

    // 账号禁用
    UNKNOWN(2, "未知");

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
    Gender(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static Gender of(Integer key) {
      for (Gender value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public Gender get() {
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
   * 权限类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/5 1:41 PM
   */
  public enum PermissionType implements IEnum<Integer, String, PermissionType> {

    // 功能权限
    FUNCTION(0, "F"),

    // 数据权限
    DATA(1, "D"),

    // 多媒体文件权限
    MEDIA(2, "M");

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
    PermissionType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static PermissionType of(Integer key) {
      for (PermissionType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public PermissionType get() {
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
   * 权限操作类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/8 16:10
   */
  public enum PermissionOperationType implements IEnum<Byte, String, PermissionOperationType> {

    // 资源禁止操作
    PROHIBIT((byte) 0x01, "资源禁止操作"),
    // 资源允许读入
    READ((byte) 0x02, "资源允许读入"),
    // 资源可允许写入
    WRITE((byte) 0x04, "资源可允许写入"),
    // 资源允许更新
    UPDATE((byte) 0x08, "资源允许更新"),
    // 资源允许删除
    DELETE((byte) 0x10, "资源允许删除"),
    ;

    /**
     * 枚举编号
     */
    private final Byte code;

    /**
     * 枚举显示名
     */
    private final String name;

    PermissionOperationType(Byte code, String name) {
      this.code = code;
      this.name = name;
    }

    @Override
    public PermissionOperationType get() {
      return this;
    }

    @Override
    public Byte key() {
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
   * @since 2022/1/10 5:42 PM
   */
  public enum Message implements IEnum<String, String, Message> {

    // 编码为{0}的权限操作数据已经存在！
    MSG_BIZ_00000("MSG_BIZ_00000"),

    // 编码为{0}的权限操作数据不存在！
    MSG_BIZ_00001("MSG_BIZ_00001"),

    // 编码为{0}的权限资源类型数据已经存在！
    MSG_BIZ_00002("MSG_BIZ_00002"),

    // 编码为{0}的权限资源类型数据不存在！
    MSG_BIZ_00003("MSG_BIZ_00003"),

    // 编码为{0}的权限数据已经存在！
    MSG_BIZ_00004("MSG_BIZ_00004"),

    // 编码为{0}的权限数据不存在！
    MSG_BIZ_00005("MSG_BIZ_00005"),
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

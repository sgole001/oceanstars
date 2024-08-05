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
    FUNCTION_MENU(100, "F_MENU"),

    // 数据权限
    DATA(200, "D"),

    // 多媒体文件权限
    MEDIA(300, "M");

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
   * 约束类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/4 16:30
   */
  public enum ConstraintType implements IEnum<Integer, String, ConstraintType> {

    // 静态约束
    STATIC(0, "Static Constraint"),

    // 动态约束
    DYNAMIC(1, "Dynamic Constraint"),

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
    ConstraintType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ConstraintType of(Integer key) {
      for (ConstraintType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public ConstraintType get() {
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
   * 约束规则类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/27 16:59
   */
  public enum ConstraintRuleType implements IEnum<Integer, String, ConstraintRuleType> {

    // 关系约束(角色分离SoD | 角色基数 | 权限分离 | 静态授权分离SSD | 动态授权分离DSD | 动态角色分配)
    RELATION(0, "Relation Constraint Rule"),

    // 会话约束(上下文限制(时间|地点|设备|网络) | 会话状态限制(最大会话数 | 会话时长 | 会话频率 | 会话时间间隔)
    SESSION(1, "Session Constraint Rule"),

    // 账号信息约束(历史访问限制 | 账号属性限制(MFA激活等))
    ACCOUNT(2, "Account Constraint Rule"),

    // 数据约束(数据访问限制 | 数据属性限制(数据敏感度等) | 数据安全级别)
    DATA(3, "DATA Constraint Rule"),

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
    ConstraintRuleType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static ConstraintRuleType of(Integer key) {
      for (ConstraintRuleType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public ConstraintRuleType get() {
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
   * @since 2022/1/10 5:42 PM
   */
  public enum Message implements IEnum<String, String, Message> {

    // 名称为{0}的权限数据已经存在！
    MSG_BIZ_00000("MSG_BIZ_00000"),

    // 名称为{0}的权限数据不存在！
    MSG_BIZ_00001("MSG_BIZ_00001"),

    // 名称为{0}的角色数据已经存在！
    MSG_BIZ_10000("MSG_BIZ_10000"),

    // 角色继承的父角色全部不存在！
    MSG_BIZ_10001("MSG_BIZ_10001"),

    // 账号[{0}]的数据不存在！
    MSG_BIZ_20000("MSG_BIZ_20000"),

    // 通过{0}注册账号[{0}]已经存在！
    MSG_BIZ_20001("MSG_BIZ_20000"),

    // 名称为{0}的约束数据不存在！
    MSG_BIZ_30000("MSG_BIZ_30000"),

    // 名称为{0}的约束数据已经存在！
    MSG_BIZ_30001("MSG_BIZ_30001"),
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

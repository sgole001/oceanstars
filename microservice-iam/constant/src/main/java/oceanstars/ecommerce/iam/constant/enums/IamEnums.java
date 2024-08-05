package oceanstars.ecommerce.iam.constant.enums;

import oceanstars.ecommerce.common.constant.IEnum;
import oceanstars.ecommerce.common.tools.MessageUtil;

/**
 * IAM服务枚举类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/5 17:26
 */
public class IamEnums {

  /**
   * 身份类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 10:39
   */
  public enum IdentityType implements IEnum<Integer, String, IdentityType> {

    // 用户
    USER(1, "Users"),
    // 服务
    SERVICE(2, "Services"),
    // 设备
    DEVICE(3, "Devices"),
    // 未知
    UNKNOWN(0, "Unknown"),
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
    IdentityType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static IdentityType of(Integer key) {
      for (IdentityType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public IdentityType get() {
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
   * 身份域枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/15 17:57
   */
  public enum IdentityDomain implements IEnum<String, String, IdentityDomain> {

    // 后台（员工）
    BACKOFFICE("00", "Backoffice"),
    // 商城（客户）
    MALL("01", "Mall"),
    ;

    /**
     * 枚举编号
     */
    private final String code;

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
    IdentityDomain(String code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static IdentityDomain of(String key) {
      for (IdentityDomain value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public IdentityDomain get() {
      return this;
    }

    @Override
    public String key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 身份认证类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/10 17:20
   */
  public enum IdentityAuthType implements IEnum<Integer, String, IdentityAuthType> {

    // 账号绑定邮箱访问
    EMAIL(0, "Email"),

    // 账号绑定手机访问
    MOBILE(1, "Mobile"),

    // 账号绑定第三方授权访问 (微信)
    EXTERNAL_WECHAT(2, "External Wechat"),

    // 账号绑定第三方授权访问 (支付宝)
    EXTERNAL_ALIPAY(3, "External Alipay"),

    // 账号绑定第三方授权访问 (抖音)
    EXTERNAL_DOUYIN(4, "External Douyin"),

    // 账号绑定第三方授权访问 (QQ)
    EXTERNAL_QQ(5, "External QQ"),

    // 账号绑定第三方授权访问 (微博)
    EXTERNAL_WEIBO(6, "External Weibo"),

    // 账号绑定第三方授权访问 (Facebook)
    EXTERNAL_FACEBOOK(7, "External Facebook"),

    // 账号绑定第三方授权访问 (Google)
    EXTERNAL_GOOGLE(8, "External Google"),

    // 账号绑定第三方授权访问 (Twitter)
    EXTERNAL_TWITTER(9, "External Twitter"),

    // 账号绑定第三方授权访问 (Apple)
    EXTERNAL_APPLE(10, "External Apple"),

    // 账号绑定第三方授权访问 (LinkedIn)
    EXTERNAL_LINKEDIN(11, "External LinkedIn"),

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
    IdentityAuthType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static IdentityAuthType of(Integer key) {
      for (IdentityAuthType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public IdentityAuthType get() {
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
   * 访问类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 15:32
   */
  public enum AccessType implements IEnum<Integer, String, AccessType> {

    // API
    API(0, "API"),
    // 多媒体
    MEDIA(1, "Media"),
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
    AccessType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static AccessType of(Integer key) {
      for (AccessType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public AccessType get() {
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
   * 主体类型枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 16:49
   */
  public enum PrincipalType implements IEnum<Integer, String, PrincipalType> {

    // 角色
    ROLE(1, "Role"),
    // 权限
    PERMISSION(2, "Permission"),
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
    PrincipalType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static PrincipalType of(Integer key) {
      for (PrincipalType value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public PrincipalType get() {
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
   * 消息枚举
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/5 17:28
   */
  public enum Message implements IEnum<String, String, Message> {

    // 申请注册的账号已经存在！
    MSG_BIZ_00000("MSG_BIZ_00000"),

    // 生成访问令牌失败！
    MSG_BIZ_00001("MSG_BIZ_00001"),
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

package oceanstars.ecommerce.integration.constant.enums;

/**
 * 集成服务枚举管理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 5:19 下午
 */
public interface IntegrationEnum {

  /**
   * 集成目标系统
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/29 3:56 下午
   */
  enum IntegrationSystem implements IntegrationEnum {

    /**
     * 集成POS
     */
    POS(0, "POS"),

    /**
     * 集成OMS
     */
    OMS(1, "OMS"),

    /**
     * 集成PMS
     */
    PMS(2, "PMS"),

    /**
     * 集成CMS
     */
    CMS(3, "CMS"),

    /**
     * 集成CRM
     */
    CRM(4, "CRM"),
    /**
     * 集成微信
     */
    WECHAT(5, "WeChat"),

    /**
     * 集成支付宝
     */
    ALIPAY(6, "AliPay");

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
    IntegrationSystem(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    public static IntegrationSystem of(Integer code) {
      for (IntegrationSystem value : IntegrationSystem.values()) {

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

  /**
   * 集成类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/29 3:56 下午
   */
  enum IntegrationType implements IntegrationEnum {

    /**
     * 数据入境
     */
    INBOUND(0, "inbound"),

    /**
     * 数据出境
     */
    OUTBOUND(1, "outbound");


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
    IntegrationType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    public static IntegrationType of(Integer code) {
      for (IntegrationType value : IntegrationType.values()) {

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

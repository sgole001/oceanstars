package oceanstars.ecommerce.common.domain;

/**
 * 领域枚举
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/15 4:37 下午
 */
public interface DomainEnum {

  /**
   * 消息总线类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/1 6:12 下午
   */
  enum Domain implements DomainEnum {

    /**
     * 消息领域
     */
    MESSAGE(0, "message"),
    /**
     * 购物车领域
     */
    CART(1, "cart"),
    /**
     * 订单领域
     */
    ORDER(2, "order"),
    /**
     * 商品领域
     */
    PRODUCT(3, "product"),
    /**
     * 促销领域
     */
    PROMOTION(4, "promotion"),
    /**
     * 用户领域
     */
    USER(5, "user"),
    /**
     * 认证领域
     */
    AUTH(6, "auth"),
    /**
     * 集成领域
     */
    INTEGRATION(7, "integration");

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
    Domain(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    public static Domain of(Integer code) {
      for (Domain value : Domain.values()) {

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

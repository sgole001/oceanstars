package oceanstars.ecommerce.infrastructure.jooq.constant;

import oceanstars.ecommerce.common.constant.IEnum;

/**
 * Jooq枚举类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/1/27 12:53
 */
public class JooqEnums {

  /**
   * 数据源
   *
   * @author Clover
   * @version 1.0.0
   * @since 2023/1/27 12:42
   */
  public enum DataSource implements IEnum<Integer, String, DataSource> {

    // 默认单一数据源
    SINGLE(-1, "single"),

    // 用户用数据源
    USER(0, "user"),

    // 商品用数据源
    PRODUCT(1, "product"),

    // 购物车用数据源
    CART(2, "cart"),

    // 订单用数据源
    ORDER(3, "order"),

    // 促销用数据源
    PROMOTION(4, "promotion"),

    // 授权认证用数据源
    AUTH(5, "auth"),

    // 消息总线用数据源
    MESSAGE(6, "message"),

    // 集成用数据源
    HUB(7, "hub");

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
    DataSource(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param key 枚举项KEY
     * @return 枚举对象
     */
    public static DataSource of(Integer key) {
      for (DataSource value : values()) {
        if (value.key().equals(key)) {
          return value;
        }
      }
      return null;
    }

    @Override
    public DataSource get() {
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
}

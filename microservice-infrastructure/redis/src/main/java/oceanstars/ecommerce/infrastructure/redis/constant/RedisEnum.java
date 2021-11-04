package oceanstars.ecommerce.infrastructure.redis.constant;

/**
 * Redis数据库相关枚举数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:00 上午
 */
public interface RedisEnum {

  enum FUNCTION_GROUP implements RedisEnum {

    // 默认配置组
    DEFAULT(0, "default"),
    // Session配置组
    SESSION(1, "session"),
    // 购物车配置组
    CART(2, "cart"),
    // 用户配置组
    USER(3, "user"),
    // 商品配置组
    PRODUCT(4, "product"),
    // 促销配置组
    PROMOTION(5, "promotion");

    /**
     * 枚举编号
     */
    Integer code;

    /**
     * 枚举显示名
     */
    String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    FUNCTION_GROUP(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    public static FUNCTION_GROUP of(Integer code) {
      for (FUNCTION_GROUP value : FUNCTION_GROUP.values()) {

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

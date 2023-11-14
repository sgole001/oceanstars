package oceanstars.ecommerce.common.constant;

/**
 * 枚举统一管理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/5 1:01 PM
 */
public interface IEnum<K, V, C extends Enum<?>> {

  /**
   * 返回枚举对象
   *
   * @return 枚举对象
   */
  C get();

  /**
   * 返回枚举项KEY
   *
   * @return 枚举项KEY
   */
  K key();

  /**
   * 返回枚举项Value
   *
   * @return 枚举项Value
   */
  V value();
}

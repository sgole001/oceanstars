package oceanstars.ecommerce.common.domain;

/**
 * 实体唯一标识（自然键）管理接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/7 2:56 下午
 */
public interface IEntityIdentifier<T> {

  /**
   * 获取唯一标识（自然键）
   *
   * @return 唯一标识（自然键）
   */
  T getIdentifier();

  /**
   * 创建唯一标识（自然键）
   *
   * @return 唯一标识（自然键）
   */
  T generateIdentifier();
}

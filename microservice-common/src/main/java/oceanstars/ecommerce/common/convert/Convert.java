package oceanstars.ecommerce.common.convert;

import java.io.Serializable;

/**
 * 数据类型转换接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:41 下午
 */
public interface Convert<D, E> extends Serializable {

  /**
   * 持久对象构建DTO
   *
   * @param entity 持久对象
   * @return DTO
   */
  D convertToDto(E entity);

  /**
   * 构建持久层实体类对象
   *
   * @param dto DTO
   * @return 持久层实体类对象
   */
  E convertToEntity(D dto);
}

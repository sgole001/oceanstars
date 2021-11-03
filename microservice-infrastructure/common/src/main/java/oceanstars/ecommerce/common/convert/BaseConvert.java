package oceanstars.ecommerce.common.convert;


import java.io.Serial;
import oceanstars.ecommerce.common.domain.BaseEntity;
import oceanstars.ecommerce.common.dto.BaseDto;
import oceanstars.ecommerce.common.exception.SystemException;

/**
 * 数据类型转换虚类（DTO <-> Entity）
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:36 下午
 */
public abstract class BaseConvert<D extends BaseDto, E extends BaseEntity> implements Convert<D, E> {

  @Serial
  private static final long serialVersionUID = 398623324319968209L;

  @Override
  public D convertToDto(E entity) {

    try {
      // 初始化DTO对象
      final D dto = this.dtoType().getDeclaredConstructor().newInstance();

      dto.setId(entity.getId());
      dto.setCreateAt(entity.getCreateAt());
      dto.setCreateBy(entity.getCreateBy());
      dto.setUpdateAt(entity.getUpdateAt());
      dto.setUpdateBy(entity.getUpdateBy());

      return dto;

    } catch (Exception e) {
      throw new SystemException("", e);
    }
  }

  @Override
  public E convertToEntity(D dto) {

    try {
      // 初始化实体对象
      final E entity = this.entityType().getDeclaredConstructor().newInstance();

      entity.setId(dto.getId());
      entity.setCreateAt(dto.getCreateAt());
      entity.setCreateBy(dto.getCreateBy());
      entity.setUpdateAt(dto.getUpdateAt());
      entity.setUpdateBy(dto.getUpdateBy());

      return entity;

    } catch (Exception e) {
      throw new SystemException("", e);
    }
  }

  /**
   * 获取DTO类型
   *
   * @return DTO类型
   */
  public abstract Class<D> dtoType();

  /**
   * 获取领域类型
   *
   * @return 领域类型
   */
  public abstract Class<E> entityType();
}

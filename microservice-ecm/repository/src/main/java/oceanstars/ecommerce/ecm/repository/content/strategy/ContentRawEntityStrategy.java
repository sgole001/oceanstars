package oceanstars.ecommerce.ecm.repository.content.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.Entity;

/**
 * 内容原生实体策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 15:33
 */
public interface ContentRawEntityStrategy {

  /**
   * 根据内容ID获取内容类型对应的特有数据
   *
   * @param contentId 内容ID
   * @return 内容类型对应的特有数据
   */
  Optional<Entity<?>> fetch(final Long contentId);

  /**
   * 根据内容ID集合获取内容类型对应的特有数据集合
   *
   * @param contentIds 内容ID集合
   * @return 内容类型对应的特有数据集合
   */
  Map<Long, Entity<?>> fetch(final List<Long> contentIds);

  /**
   * 保存内容类型对应的特有数据
   *
   * @param entity 内容类型对应的特有数据
   */
  void save(final Entity<?> entity);
}

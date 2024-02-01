package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import java.util.Optional;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRawEntityStrategy;

/**
 * 内容特有数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:51
 */
public class ContentRawEntityStrategyContext {

  /**
   * 内容原生实体策略接口
   */
  private ContentRawEntityStrategy strategy;

  /**
   * 构造函数: 根据内容类型选择不同的策略
   *
   * @param type 内容类型
   */
  public ContentRawEntityStrategyContext(final ContentType contentType) {

    if (null == contentType) {
      throw new IllegalArgumentException("不支持的内容类型");
    }

    switch (contentType) {
      case WEB_FUNCTION:
        this.strategy = new WebFunctionEntityStrategy();
        break;
    }
  }

  /**
   * 根据内容ID获取内容类型对应的特有数据
   *
   * @param contentId 内容ID
   * @return 内容类型对应的特有数据
   */
  public Optional<Entity<?>> fetch(final Long contentId) {
    return this.strategy.fetch(contentId);
  }

  /**
   * 保存内容类型对应的特有数据
   *
   * @param entity 内容类型对应的特有数据实体
   */
  public void save(final Entity<?> entity) {
    this.strategy.save(entity);
  }
}

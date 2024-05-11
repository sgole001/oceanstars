package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRepositoryStrategy;

/**
 * 内容原始数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:51
 */
public class ContentRepositoryStrategyContext {

  /**
   * 内容原生实体策略接口
   */
  private ContentRepositoryStrategy strategy;

  /**
   * 构造函数: 根据内容类型选择不同的策略
   *
   * @param contentType 内容类型
   */
  public ContentRepositoryStrategyContext(final ContentType contentType) {

    if (null == contentType) {
      throw new IllegalArgumentException("不支持的内容类型");
    }

    switch (contentType) {
      case MENU:
        this.strategy = new MenuStrategy();
        break;
    }
  }

  /**
   * 查询内容
   *
   * @param condition 查询条件
   * @return 内容列表
   */
  public List<Content> fetch(final ICondition condition) {
    return this.strategy.fetch(condition);
  }

  /**
   * 创建内容
   *
   * @param content 内容实体
   */
  public void create(final Content content) {
    this.strategy.create(content);
  }

  /**
   * 修改内容
   *
   * @param content 内容实体
   */
  public void modify(final Content content) {
    this.strategy.modify(content);
  }

}

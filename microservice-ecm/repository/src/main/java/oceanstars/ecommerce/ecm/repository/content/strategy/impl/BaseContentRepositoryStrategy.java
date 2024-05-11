package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRepositoryStrategy;
import oceanstars.ecommerce.ecm.repository.generate.tables.RelContentCategory;
import oceanstars.ecommerce.ecm.repository.generate.tables.RelContentTag;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/8 11:26
 */
public abstract class BaseContentRepositoryStrategy implements ContentRepositoryStrategy {

  /**
   * 内容标签关联表
   */
  final static RelContentTag REL_CONTENT_TAG = RelContentTag.REL_CONTENT_TAG.as("rel_content_tag");

  /**
   * 内容分类关联表
   */
  final static RelContentCategory REL_CONTENT_CATEGORY = RelContentCategory.REL_CONTENT_CATEGORY.as("rel_content_category");

  /**
   * 内容序列步长
   */
  final static Long SEQ_STEP = 1000L;
}

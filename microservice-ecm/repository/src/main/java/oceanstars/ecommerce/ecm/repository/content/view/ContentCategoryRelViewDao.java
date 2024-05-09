package oceanstars.ecommerce.ecm.repository.content.view;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.repository.generate.tables.RelContentCategory;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 内容分类关联视图DAO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/9 15:35
 */
@Repository
public class ContentCategoryRelViewDao {

  /**
   * 内容分类关联表
   */
  final static RelContentCategory REL_CONTENT_CATEGORY = RelContentCategory.REL_CONTENT_CATEGORY.as("rel_content_category");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询内容分类关联信息
   *
   * @param contents   查询条件：内容ID集合
   * @param type       查询条件：内容类型
   * @param categories 查询条件：内容分类ID集合
   * @return 内容分类关联映射信息
   */
  public Map<Long, List<Long>> fetch(final Set<Long> contents, final ContentType type, final Set<Long> categories) {

    // 初始化查询条件
    final Condition searchCondition = DSL.trueCondition();

    // 内容ID
    if (!CollectionUtils.isEmpty(contents)) {
      searchCondition.and(REL_CONTENT_CATEGORY.CONTENT.in(contents));
    }
    // 内容类型
    if (null != type) {
      searchCondition.and(REL_CONTENT_CATEGORY.TYPE.eq(type.key().shortValue()));
    }
    // 分类ID
    if (!CollectionUtils.isEmpty(categories)) {
      searchCondition.and(REL_CONTENT_CATEGORY.CATEGORY.in(categories));
    }

    // 查询内容分类关联信息
    return dsl.select(REL_CONTENT_CATEGORY.CONTENT, REL_CONTENT_CATEGORY.CATEGORY)
        .from(REL_CONTENT_CATEGORY)
        .where(searchCondition)
        .fetchGroups(REL_CONTENT_CATEGORY.CONTENT, REL_CONTENT_CATEGORY.CATEGORY);
  }
}

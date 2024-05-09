package oceanstars.ecommerce.ecm.repository.content.view;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.repository.generate.tables.RelContentTag;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 内容标签关联视图DAO
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/9 15:20
 */
@Repository
public class ContentTagRelViewDao {

  /**
   * 内容标签关联表
   */
  final static RelContentTag REL_CONTENT_TAG = RelContentTag.REL_CONTENT_TAG.as("rel_content_tag");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据查询条件查询内容标签关联信息
   *
   * @param contents 查询条件：内容ID集合
   * @param type     查询条件：内容类型
   * @param tags     查询条件：内容标签ID集合
   * @return 内容标签关联映射信息
   */
  public Map<Long, List<Long>> fetch(final Set<Long> contents, final ContentType type, final Set<Long> tags) {

    // 初始化查询条件
    final Condition searchCondition = DSL.trueCondition();

    // 内容ID
    if (!CollectionUtils.isEmpty(contents)) {
      searchCondition.and(REL_CONTENT_TAG.CONTENT.in(contents));
    }
    // 内容类型
    if (null != type) {
      searchCondition.and(REL_CONTENT_TAG.TYPE.eq(type.key().shortValue()));
    }
    // 标签ID
    if (!CollectionUtils.isEmpty(tags)) {
      searchCondition.and(REL_CONTENT_TAG.TAG.in(tags));
    }

    // 查询内容标签关联信息
    return dsl.select(REL_CONTENT_TAG.CONTENT, REL_CONTENT_TAG.TAG)
        .from(REL_CONTENT_TAG)
        .where(searchCondition)
        .fetchGroups(REL_CONTENT_TAG.CONTENT, REL_CONTENT_TAG.TAG);
  }
}

package oceanstars.ecommerce.ecm.repository.content.strategy;

import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentCategoryDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentTagDao;
import org.jooq.impl.DefaultDSLContext;

/**
 * 内容资源库策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 15:33
 */
public interface ContentRepositoryStrategy {

  /**
   * 内容标签关联DAO
   */
  RelContentTagDao REL_CONTENT_TAG_DAO = ApplicationContextProvider.getApplicationContext().getBean(RelContentTagDao.class);

  /**
   * 内容分类关联DAO
   */
  RelContentCategoryDao REL_CONTENT_CATEGORY_DAO = ApplicationContextProvider.getApplicationContext().getBean(RelContentCategoryDao.class);

  /**
   * Jooq数据库操作对象
   */
  DefaultDSLContext DSL_CONTEXT = ApplicationContextProvider.getApplicationContext().getBean(DefaultDSLContext.class);

  /**
   * 根据查询条件查询内容
   *
   * @param condition 查询条件
   * @return 内容列表
   */
  List<Content> fetch(final ICondition condition);

  /**
   * 保存资产
   *
   * @param content 内容实体
   */
  void save(final Content content);

  /**
   * 构建内容实体
   *
   * @param contentPojo 内容POJO
   * @return 内容实体
   */
  Content buildContentEntity(final Object contentPojo);

  /**
   * 构建内容POJO
   *
   * @param content 内容实体
   * @return 内容POJO
   */
  Object buildContentPojo(final Content content);
}

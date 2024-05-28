package oceanstars.ecommerce.ecm.repository.content;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentStatisticsType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentStatisticsValueObject;
import oceanstars.ecommerce.ecm.domain.content.repository.ContentRepository;
import oceanstars.ecommerce.ecm.domain.content.repository.condition.ContentFetchCondition;
import oceanstars.ecommerce.ecm.repository.content.strategy.impl.ContentRepositoryStrategyContext;
import oceanstars.ecommerce.ecm.repository.content.view.ContentCategoryRelViewDao;
import oceanstars.ecommerce.ecm.repository.content.view.ContentStatisticsViewDao;
import oceanstars.ecommerce.ecm.repository.content.view.ContentTagRelViewDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentCategoryDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentTagDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentCategoryPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentTagPojo;
import oceanstars.ecommerce.common.spring.OceanstarsTransactional;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 内容聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 14:29
 */
@Repository
public class JooqContentRepository extends BaseDomainRepository<Content> implements ContentRepository {

  /**
   * 内容分类关联DAO
   */
  @Resource
  private RelContentCategoryDao relContentCategoryDao;

  /**
   * 内容标签关联DAO
   */
  @Resource
  private RelContentTagDao relContentTagDao;

  /**
   * 内容标签关联视图DAO
   */
  @Resource
  private ContentTagRelViewDao contentTagRelViewDao;

  /**
   * 内容分类关联视图DAO
   */
  @Resource
  private ContentCategoryRelViewDao contentCategoryRelViewDao;

  /**
   * 内容统计数据视图DAO
   */
  @Resource
  private ContentStatisticsViewDao contentStatisticsViewDao;

  @Override
  public List<Content> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 公共查询条件类型转换获取资产类型信息
    final ContentFetchCondition contentFetchCondition = (ContentFetchCondition) condition;
    // 获取内容类型
    final ContentType contentType = contentFetchCondition.getType();

    // 初始化创建资产资源库策略上下文
    final ContentRepositoryStrategyContext repositoryStrategy = new ContentRepositoryStrategyContext(contentType);

    // 查询内容主体信息
    final List<Content> contents = repositoryStrategy.fetch(contentFetchCondition);

    // 获取内容ID集合
    final Set<Long> contentIds = contents.stream().map(content -> content.getDelegator().getId()).collect(Collectors.toSet());
    // 查询内容标签
    final Map<Long, List<Long>> contentTags = this.contentTagRelViewDao.fetch(contentIds, contentType, null);
    // 查询内容分类
    final Map<Long, List<Long>> contentCategories = this.contentCategoryRelViewDao.fetch(contentIds, contentType, null);
    // 查询内容统计数据
    final Map<Short, Map<Long, Map<Short, Long>>> contentTypeStats = this.contentStatisticsViewDao.aggregate(contentIds, contentType, null);

    // 获取指定内容类型的内容统计数据
    final Map<Long, Map<Short, Long>> contentStats = contentTypeStats.get(contentType.key().shortValue());

    contents.forEach(content -> {
      // 设置内容统计信息
      content.setStatistics(this.buildContentStatistics(contentStats.get(content.getDelegator().getId())));
      // 设置内容标签
      content.setTags(new HashSet<>(contentTags.get(content.getDelegator().getId())));
      // 设置内容分类
      content.setCategories(new HashSet<>(contentCategories.get(content.getDelegator().getId())));
    });

    return contents;
  }

  @Override
  protected void create(Content content) {

    // 校验参数
    requireNonNull(content, "content");

    // 初始化创建内容资源库策略上下文
    final ContentRepositoryStrategyContext repositoryStrategy = new ContentRepositoryStrategyContext(content.getIdentifier().getType());

    // 创建内容
    repositoryStrategy.create(content);

    // 保存内容与标签关系
    this.saveContentTags(content);

    // 保存内容与分类关系
    this.saveContentCategories(content);
  }

  @Override
  protected void modify(Content aggregator) {

  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void delete(Content content) {

  }

  /**
   * 构建内容统计数据
   *
   * @param contentStats 内容统计数据
   * @return 内容统计数据
   */
  private ContentStatisticsValueObject buildContentStatistics(final Map<Short, Long> contentStats) {

    // 初始化创建内容统计数据
    final ContentStatisticsValueObject.Builder contentStatistics = ContentStatisticsValueObject.newBuilder();

    contentStats.forEach((key, value) -> {

      // 获取内容统计类型
      final ContentStatisticsType statisticsType = ContentStatisticsType.of(key.intValue());

      if (null != statisticsType) {
        // 设置内容统计数据
        switch (statisticsType) {
          case VISITS:
            // 内容统计数据-访问量
            contentStatistics.visits(value);
            break;
          case COMMENTS:
            // 内容统计数据-评论数量
            contentStatistics.comments(value);
            break;
          case LIKES:
            // 内容统计数据-点赞量
            contentStatistics.likes(value);
            break;
          case DISLIKES:
            // 内容统计数据-点踩量
            contentStatistics.dislikes(value);
            break;
          case FAVORITES:
            // 内容统计数据-收藏量
            contentStatistics.favorites(value);
            break;
          case SHARES:
            // 内容统计数据-分享量
            contentStatistics.shares(value);
            break;
          case DOWNLOADS:
            // 内容统计数据-下载量
            contentStatistics.downloads(value);
            break;
          default:
            break;
        }
      }
    });

    return contentStatistics.build();
  }

  /**
   * 保存内容标签
   *
   * @param content 内容实体信息
   */
  private void saveContentTags(final Content content) {

    // 保存内容与标签关系
    if (!CollectionUtils.isEmpty(content.getTags())) {

      // 构建内容与标签关系数据库实体
      final List<RelContentTagPojo> contentTags = content.getTags().stream().map(tag -> {
        // 初始化创建内容标签数据库实体
        final RelContentTagPojo contentTagPojo = new RelContentTagPojo();
        // 内容ID
        contentTagPojo.setContent(content.getDelegator().getId());
        // 内容类型
        contentTagPojo.setType(content.getIdentifier().getType().key().shortValue());
        // 标签ID
        contentTagPojo.setTag(tag);

        return contentTagPojo;
      }).toList();

      // 保存内容与标签关系
      this.relContentTagDao.insert(contentTags);
    }
  }

  /**
   * 保存内容分类
   *
   * @param content 内容实体信息
   */
  private void saveContentCategories(final Content content) {

    // 保存内容与标签关系
    if (!CollectionUtils.isEmpty(content.getCategories())) {

      // 构建内容与标签关系数据库实体
      final List<RelContentCategoryPojo> contentCategories = content.getCategories().stream().map(category -> {
        // 初始化创建内容标签数据库实体
        final RelContentCategoryPojo contentCategoryPojo = new RelContentCategoryPojo();
        // 内容ID
        contentCategoryPojo.setContent(content.getDelegator().getId());
        // 内容类型
        contentCategoryPojo.setType(content.getIdentifier().getType().key().shortValue());
        // 分类ID
        contentCategoryPojo.setCategory(category);

        return contentCategoryPojo;
      }).toList();

      // 保存内容与标签关系
      this.relContentCategoryDao.insert(contentCategories);
    }
  }
}

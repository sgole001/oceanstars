package oceanstars.ecommerce.ecm.repository.content;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;
import oceanstars.ecommerce.common.domain.EntityDelegator;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.entity.ContentIdentifier;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentStatisticsValueObject;
import oceanstars.ecommerce.ecm.domain.content.repository.ContentRepository;
import oceanstars.ecommerce.ecm.repository.content.strategy.impl.ContentRawEntityStrategyContext;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentCategoryDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentTagDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentCategoryPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentTagPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 内容聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 14:29
 */
@Repository
public class JooqContentRepository implements ContentRepository {

  @Resource
  private EcmContentDao contentDao;

  @Resource
  private RelContentTagDao relContentTagDao;

  @Resource
  private RelContentCategoryDao relContentCategoryDao;

  @Override
  public Optional<Content> findByIdentifier(ContentIdentifier identifier) {

    // 校验参数
    requireNonNull(identifier, "identifier");

    // 根据内容唯一识别码查询内容实体
    final EcmContentPojo contentPojo = contentDao.fetchOneByCode(identifier.getIdentifier());

    // 判断内容实体是否存在, 不存在则返回空Optional
    if (null == contentPojo) {
      return Optional.empty();
    }

    return Optional.of(this.buildContentEntity(contentPojo));
  }

  @Override
  public Optional<Content> findByDelegator(EntityDelegator delegator) {

    // 校验参数
    requireNonNull(delegator, "delegator");

    // 根据内容委托者ID查询内容实体
    final EcmContentPojo contentPojo = contentDao.fetchOneById(delegator.getId());

    // 判断内容实体是否存在, 不存在则返回空Optional
    if (null == contentPojo) {
      return Optional.empty();
    }

    return Optional.of(this.buildContentEntity(contentPojo));
  }

  @Override
  public Optional<Content> findByDelegator(Long id) {

    // 校验参数
    requireNonNull(id, "id");

    // 根据内容委托者ID查询内容实体
    final EcmContentPojo contentPojo = contentDao.fetchOneById(id);

    // 判断内容实体是否存在, 不存在则返回空Optional
    if (null == contentPojo) {
      return Optional.empty();
    }

    return Optional.of(this.buildContentEntity(contentPojo));
  }

  @Override
  public List<Content> find(Content conditions) {

    // 校验参数
    requireNonNull(conditions, "conditions");

    // TODO: 2021/1/23 14:29

    return null;
  }

  @Override
  public List<Content> findByDelegators(List<EntityDelegator> delegators) {

    // 校验参数
    requireNonNull(delegators, "delegators");

    // 根据内容委托者集合查询内容实体集合
    final List<EcmContentPojo> contentPojoList = contentDao.fetchById(delegators.stream().map(EntityDelegator::getId).toArray(Long[]::new));

    if (CollectionUtils.isEmpty(contentPojoList)) {
      return null;
    }

    // 初始化创建内容实体集合
    return contentPojoList.stream().map(this::buildContentEntity).toList();
  }

  @Override
  public List<Content> findByDelegatorIds(List<Long> ids) {

    // 校验参数
    requireNonNull(ids, "ids");

    // 根据内容委托者ID集合查询内容实体集合
    final List<EcmContentPojo> contentPojoList = contentDao.fetchById(ids.toArray(new Long[0]));

    if (CollectionUtils.isEmpty(contentPojoList)) {
      return null;
    }

    // 初始化创建内容实体集合
    return contentPojoList.stream().map(this::buildContentEntity).toList();
  }

  @Override
  public List<Content> findByIdentifiers(List<ContentIdentifier> identifiers) {

    // 校验参数
    requireNonNull(identifiers, "identifiers");

    // 根据内容委托者ID集合查询内容实体集合
    final List<EcmContentPojo> contentPojoList = contentDao.fetchByCode(
        identifiers.stream().map(ContentIdentifier::getIdentifier).toArray(String[]::new));

    if (CollectionUtils.isEmpty(contentPojoList)) {
      return null;
    }

    // 初始化创建内容实体集合
    return contentPojoList.stream().map(this::buildContentEntity).toList();
  }

  @Transactional
  @Override
  public void save(Content content) {

    // 校验参数
    requireNonNull(content, "content");

    // 根据内容唯一识别码查询内容实体
    EcmContentPojo contentPojo = contentDao.fetchOneByCode(content.getIdentifier().getIdentifier());
    // 判断内容实体是否存在, 存在则抛出业务异常
    if (null != contentPojo) {
      // 业务异常：内容创建失败，{0}类型的名称为{1}的内容已经存在！
      throw new BusinessException(Message.MSG_BIZ_00002, content.getType().value(), content.getName());
    }

    // 保存内容元数据
    contentPojo = this.buildContentPojo(content);
    this.contentDao.insert(contentPojo);

    // 保存内容与标签关系
    if (!CollectionUtils.isEmpty(content.getTags())) {
      // 获取内容ID
      final Long contentId = contentPojo.getId();
      // 构建内容与标签关系数据库实体
      final List<RelContentTagPojo> contentTagPojo =
          content.getTags().stream().map(tag -> this.buildRelContentTagPojo(tag, contentId)).toList();
      // 保存内容与标签关系
      this.relContentTagDao.insert(contentTagPojo);
    }

    // 保存内容与分类关系
    if (!CollectionUtils.isEmpty(content.getCategories())) {
      // 获取内容ID
      final Long contentId = contentPojo.getId();
      // 构建内容与分类关系数据库实体
      final List<RelContentCategoryPojo> contentCategoryPojo =
          content.getCategories().stream().map(category -> this.buildRelContentCategoryPojo(category, contentId)).toList();
      // 保存内容与分类关系
      this.relContentCategoryDao.insert(contentCategoryPojo);
    }

    // 保存内容原始数据
    new ContentRawEntityStrategyContext(content.getType()).save(content.getRaw());
  }

  @Override
  public void delete(Content content) {

  }

  /**
   * 将内容数据库实体转换为内容实体
   *
   * @param contentPojo 内容数据库实体
   * @return 内容实体
   */
  private Content buildContentEntity(final EcmContentPojo contentPojo) {

    // 获取内容类型
    final ContentType contentType = ContentType.of(contentPojo.getType().intValue());

    // 构建内容实体
    return Content.newBuilder(contentPojo.getName(), contentType)
        // 内容展示名称
        .displayName(contentPojo.getDisplayName())
        // 内容描述
        .description(contentPojo.getDescription())
        // 内容状态
        .status(AuditProcessStatus.of(contentPojo.getStatus().intValue()))
        // 内容统计数据
        .statistics(ContentStatisticsValueObject.newBuilder()
            // 内容统计数据-访问量
            .visits(contentPojo.getVisits())
            // 内容统计数据-评论数量
            .comments(contentPojo.getComments())
            // 内容统计数据-点赞量
            .likes(contentPojo.getLikes())
            // 内容统计数据-点踩量
            .dislikes(contentPojo.getDislikes())
            // 内容统计数据-收藏量
            .favorites(contentPojo.getFavorites())
            // 内容统计数据-分享量
            .shares(contentPojo.getShares())
            // 内容统计数据-下载量
            .downloads(contentPojo.getDownloads())
            // 实施构建
            .build())
        // 不同内容类型特有数据
        .raw(new ContentRawEntityStrategyContext(contentType).fetch(contentPojo.getId()).orElse(null))
        // 实施构建
        .build();
  }

  /**
   * 将内容实体转换为内容数据库实体
   *
   * @param content 内容实体
   * @return 内容数据库实体
   */
  private EcmContentPojo buildContentPojo(final Content content) {

    // 初始化创建内容数据库实体
    final EcmContentPojo contentPojo = new EcmContentPojo();
    // 内容ID
    contentPojo.setId(content.getDelegator().getId());
    // 内容编码
    contentPojo.setCode(content.getIdentifier().getIdentifier());
    // 内容名称
    contentPojo.setName(content.getName());
    // 内容类型
    contentPojo.setType(content.getType().key().shortValue());
    // 内容展示名称
    contentPojo.setDisplayName(content.getDisplayName());
    // 内容描述
    contentPojo.setDescription(content.getDescription());
    // 内容状态
    contentPojo.setStatus(content.getStatus().key().shortValue());
    // 内容统计数据
    final ContentStatisticsValueObject statistics = content.getStatistics();
    if (null != statistics) {
      // 内容统计数据-访问量
      contentPojo.setVisits(statistics.getVisits());
      // 内容统计数据-评论数量
      contentPojo.setComments(statistics.getComments());
      // 内容统计数据-点赞量
      contentPojo.setLikes(statistics.getLikes());
      // 内容统计数据-点踩量
      contentPojo.setDislikes(statistics.getDislikes());
      // 内容统计数据-收藏量
      contentPojo.setFavorites(statistics.getFavorites());
      // 内容统计数据-分享量
      contentPojo.setShares(statistics.getShares());
      // 内容统计数据-下载量
      contentPojo.setDownloads(statistics.getDownloads());
    }

    return contentPojo;
  }

  /**
   * 将内容标签实体转换为内容标签数据库实体
   *
   * @param tagId     标签ID
   * @param contentId 内容ID
   * @return 内容标签数据库实体
   */
  private RelContentTagPojo buildRelContentTagPojo(final Long tagId, final Long contentId) {

    // 初始化创建内容标签数据库实体
    final RelContentTagPojo contentTagPojo = new RelContentTagPojo();
    // 内容ID
    contentTagPojo.setCid(contentId);
    // 标签ID
    contentTagPojo.setTid(tagId);

    return contentTagPojo;
  }

  /**
   * 将内容分类实体转换为内容分类数据库实体
   *
   * @param categoryId 分类ID
   * @param contentId  内容ID
   * @return 内容分类数据库实体
   */
  private RelContentCategoryPojo buildRelContentCategoryPojo(final Long categoryId, final Long contentId) {

    // 初始化创建内容分类数据库实体
    final RelContentCategoryPojo contentCategoryPojo = new RelContentCategoryPojo();
    // 内容ID
    contentCategoryPojo.setCid(contentId);
    // 分类ID
    contentCategoryPojo.setCatid(categoryId);

    return contentCategoryPojo;
  }
}

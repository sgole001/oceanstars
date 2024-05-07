package oceanstars.ecommerce.ecm.repository.content;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.List;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.content.entity.Content;
import oceanstars.ecommerce.ecm.domain.content.entity.ContentIdentifier;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentStatisticsValueObject;
import oceanstars.ecommerce.ecm.domain.content.repository.ContentRepository;
import oceanstars.ecommerce.ecm.repository.content.strategy.impl.ContentRawStrategyContext;
import oceanstars.ecommerce.ecm.repository.content.view.ContentViewDao;
import oceanstars.ecommerce.ecm.repository.content.view.bo.ContentView;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmContentDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelContentTagDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmContentPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelContentTagPojo;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsTransactional;
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
public class JooqContentRepository implements ContentRepository {

  @Resource
  private EcmContentDao contentDao;

  @Resource
  private ContentViewDao contentViewDao;

  @Resource
  private RelContentTagDao relContentTagDao;

  @Override
  public List<Content> find(ICondition condition) {
    return List.of();
  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void save(Content content) {

    // 校验参数
    requireNonNull(content, "content");

    // 根据内容唯一识别码查询内容实体
    final List<ContentView<?>> contentView = contentViewDao.fetchByIdentifier(content.getIdentifier());
    // 判断内容实体是否存在, 存在则抛出业务异常
    if (!CollectionUtils.isEmpty(contentView)) {
      // 获取内容唯一识别对象
      final ContentIdentifier identifier = content.getIdentifier();
      // 业务异常：内容创建失败，{0}类型的名称为{1}的内容已经存在！
      throw new BusinessException(Message.MSG_BIZ_00002, identifier.getType().value(), identifier.getName());
    }

    // 保存内容元数据
    final EcmContentPojo contentPojo = this.buildContentPojo(content);
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

    // 保存内容空间（内容分类）
//    if (!CollectionUtils.isEmpty(content.getCategories())) {
//      // 获取内容ID
//      final Long contentId = contentPojo.getId();
//      // 构建内容与分类关系数据库实体
//      final List<RelContentCategoryPojo> contentCategoryPojo =
//          content.getCategories().stream().map(category -> this.buildRelContentCategoryPojo(category, contentId)).toList();
//      // 保存内容与分类关系
//      this.relContentCategoryDao.insert(contentCategoryPojo);
//    }

    // 获取内容特有数据处理策略上下文
//    final ContentRawStrategyContext strategyContext = new ContentRawStrategyContext(content.getIdentifier().getType());
//    // 保存内容原始数据
//    strategyContext.executeSave(content.getRaw());
//    // 内容排序处理
//    if (strategyContext.determineSort()) {
//
//      // 执行排序
//    }
  }

  @Override
  public void delete(Content content) {

  }

  /**
   * 将内容数据视图实体转换为内容领域实体
   *
   * @param contentView 内容数据视图实体
   * @return 内容领域实体
   */
  private Content buildContentEntity(final ContentView<?> contentView) {

    // 获取内容元数据
    final EcmContentPojo contentMeta = contentView.getMeta();
    // 获取内容原生数据
    final Object contentRaw = contentView.getRaw();

    // 获取内容类型
    final ContentType contentType = ContentType.of(contentMeta.getType().intValue());

    // 初始化创建内容特有数据处理策略上下文
    final ContentRawStrategyContext strategyContext = new ContentRawStrategyContext(contentType);

    // 构建内容原生领域实体
    final ValueObject raw = strategyContext.executeBuildRawValueObject(contentRaw);

    // 构建内容实体
    return Content.newBuilder(contentType, contentMeta.getName())
        // 内容展示名称
        .displayName(contentMeta.getDisplayName())
        // 内容描述
        .description(contentMeta.getDescription())
        // 内容状态
        .status(AuditProcessStatus.of(contentMeta.getStatus().intValue()))
        // 内容统计数据
        .statistics(ContentStatisticsValueObject.newBuilder()
            // 内容统计数据-访问量
            .visits(contentMeta.getVisits())
            // 内容统计数据-评论数量
            .comments(contentMeta.getComments())
            // 内容统计数据-点赞量
            .likes(contentMeta.getLikes())
            // 内容统计数据-点踩量
            .dislikes(contentMeta.getDislikes())
            // 内容统计数据-收藏量
            .favorites(contentMeta.getFavorites())
            // 内容统计数据-分享量
            .shares(contentMeta.getShares())
            // 内容统计数据-下载量
            .downloads(contentMeta.getDownloads())
            // 实施构建
            .build())
        // 内容原生信息
        .raw(raw)
        // 实施构建
        .build();
  }

//  /**
//   * 将内容空间数据实体转换为内容空间领域实体
//   *
//   * @param contentSpacePojo 内容空间数据实体
//   * @return 内容空间领域实体
//   */
//  private ContentSpace buildContentSpaceEntity(final EcmContentSpacePojo contentSpacePojo) {
//
//    return ContentSpace.newBuilder(contentSpacePojo.getContent(), contentSpacePojo.getCategory())
//        // 内容所在空间的序列
//        .link(contentSpacePojo.getLink())
//        // 内容所在序列的相邻内容ID（前）
//        .prev(contentSpacePojo.getPrev())
//        // 内容所在序列的相邻内容ID（后）
//        .next(contentSpacePojo.getNext())
//        // 内容所在序列的序号 （前 < 后）
//        .seq(contentSpacePojo.getSeq())
//        // 实施构建
//        .build();
//  }

  /**
   * 将内容实体转换为内容数据库实体
   *
   * @param content 内容实体
   * @return 内容数据库实体
   */
  private EcmContentPojo buildContentPojo(final Content content) {

    // 初始化创建内容数据库实体
    final EcmContentPojo contentPojo = new EcmContentPojo();
    // 内容名称
    contentPojo.setName(content.getIdentifier().getName());
    // 内容类型
    contentPojo.setType(content.getIdentifier().getType().key().shortValue());
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
    // 内容原始数据ID
    contentPojo.setRawId(content.getRaw().getDelegator().getId());

    return contentPojo;
  }
//
//  private void buildContentSortPojoList(final EcmContentSortPojo srcSort, final EcmContentSortPojo destSort) {
//    this.buildContentSortPojoList(List.of(srcSort), destSort);
//  }
//
//  private void buildContentSortPojoList(final List<EcmContentSortPojo> srcSorts, final EcmContentSortPojo destSort) {
//
//    // 初始化创建内容排序
//    final List<EcmContentSortPojo> sortedPojoList = new ArrayList<>();
//
//    // 初始化排序对象集合Mapping
//    final Map<Long, EcmContentSortPojo> sortPojoMap = HashMap.newHashMap((srcSorts.size() + 1) * 3);
//
//    // 初始化目标排序对象
//    sortPojoMap.put(destSort.getId(), destSort);
//
//    // 初始化目标排序对象
//    Long targetSortId = destSort.getId();
//
//    for (EcmContentSortPojo srcSort : srcSorts) {
//
//      if (!sortPojoMap.containsKey(targetSortId)) {
//        sortPojoMap.put(targetSortId, this.contentSortDao.fetchOneById(targetSortId));
//      }
//      if (!sortPojoMap.containsKey(srcSort.getPrev())) {
//        sortPojoMap.put(srcSort.getPrev(), this.contentSortDao.fetchOneById(srcSort.getPrev()));
//      }
//      if (!sortPojoMap.containsKey(srcSort.getNext())) {
//        sortPojoMap.put(srcSort.getNext(), this.contentSortDao.fetchOneById(srcSort.getNext()));
//      }
//
//      // 获取原排序位置的前置排序
//      final EcmContentSortPojo srcPrevSort = sortPojoMap.get(srcSort.getPrev());
//      // 获取原排序位置的后置排序
//      final EcmContentSortPojo srcNextSort = sortPojoMap.get(srcSort.getNext());
//
//      if (null != srcPrevSort && null != srcNextSort) {
//        // 更新源排序位置的前置排序的后置节点
//        srcPrevSort.setNext(srcNextSort.getId());
//        // 更新源排序位置的后置排序的前置节点
//        srcNextSort.setPrev(srcPrevSort.getId());
//      }
//
//      // 更新目标排序位置的后置排序
//      destSort.setNext(srcSort.getId());
//      // 更新源排序位置的前置排序
//      srcSort.setPrev(destSort.getId());
//      srcSort.setLinkid(destSort.getLinkid());
//
//      targetSortId = srcSort.getId();
//    }
//
//    // 获取目标排序位置的后置排序
//    final EcmContentSortPojo nextSort = this.contentSortDao.fetchOneById(destSort.getNext());
//
//    // 查询获取排序目的地对象信息
//    EcmContentSortPojo destSortPojo;
//    // 排序目的位置为头部
//    if (ContentSort.LINK_HEAD_PREV.equals(destSortId)) {
//      destSortPojo = null;
//    }
//    // 排序目的位置为尾部
//    else if (ContentSort.LINK_TAIL_NEXT.equals(destSortId)) {
//      destSortPojo = null;
//    }
//    // 排序目的位置为指定位置
//    else {
//      destSortPojo = this.contentSortDao.fetchOneById(destSortId);
//    }
//
//    // 判断排序目的地对象是否存在, 不存在则抛出业务异常
//    if (null == destSortPojo) {
//      // 业务异常：指定的排序位置{0}不存在，无法进行排序！
//      throw new BusinessException(Message.MSG_BIZ_00003, destSortId);
//    }
//
//    // 查询获取排序源对象信息
//    final List<EcmContentSortPojo> srcSortPojoList = this.contentSortDao.fetchById(srcSortIds.toArray(new Long[0]));
//    // 判断排序源对象是否存在, 不存在则抛出业务异常
//    if (CollectionUtils.isEmpty(srcSortPojoList)) {
//      // 业务异常：指定的排序对象{0}不存在，无法进行排序！
//      throw new BusinessException(Message.MSG_BIZ_00004, srcSortIds.toString());
//    }
//    // 判断是否存在部分排序对象不存在, 存在则抛出业务异常
//    else if (srcSortPojoList.size() != srcSortIds.size()) {
//      // 获取存在的排序对象ID集合
//      final List<Long> existSortIds = srcSortPojoList.stream().map(EcmContentSortPojo::getId).toList();
//      // 获取不存在的排序对象ID集合
//      final List<Long> notExistSortIds = srcSortIds.stream().filter(sortId -> !existSortIds.contains(sortId)).toList();
//      // 业务异常：指定的排序对象{0}中{1}不存在，无法进行排序！
//      throw new BusinessException(Message.MSG_BIZ_00005, srcSortIds.toString(), notExistSortIds.toString());
//    }
//
//    // 创建内容排序对象集合
//    final List<EcmContentSortPojo> contentSortPojoList = new ArrayList<>(srcSortIds.size());
//
//    // 初始化创建内容排序数据库实体
//    final EcmContentSortPojo contentSortPojo = new EcmContentSortPojo();
//    // 内容ID
//    contentSortPojo.setCid(content.getDelegator().getId());
//    // 排序值
//    contentSortPojo.setSort(content.getSort());
//    // 排序类型
//    contentSortPojo.setType(content.getSortType().key().shortValue());
//    // 排序字段
//    contentSortPojo.setField(content.getSortField().key().shortValue());
//    // 排序方向
//    contentSortPojo.setDirection(content.getSortDirection().key().shortValue());
//    // 实施构建
//    return sortedPojoList;
//  }

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
    contentTagPojo.setContent(contentId);
    // 标签ID
    contentTagPojo.setTag(tagId);

    return contentTagPojo;
  }
//
//  /**
//   * 将内容分类实体转换为内容分类数据库实体
//   *
//   * @param categoryId 分类ID
//   * @param contentId  内容ID
//   * @return 内容分类数据库实体
//   */
//  private RelContentCategoryPojo buildRelContentCategoryPojo(final Long categoryId, final Long contentId) {
//
//    // 初始化创建内容分类数据库实体
//    final RelContentCategoryPojo contentCategoryPojo = new RelContentCategoryPojo();
//    // 内容ID
//    contentCategoryPojo.setCid(contentId);
//    // 分类ID
//    contentCategoryPojo.setCatid(categoryId);
//
//    return contentCategoryPojo;
//  }
}

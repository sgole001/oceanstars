package oceanstars.ecommerce.ecm.repository.tag;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.EntityDelegator;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.tag.entity.Tag;
import oceanstars.ecommerce.ecm.domain.tag.entity.TagIdentifier;
import oceanstars.ecommerce.ecm.domain.tag.repository.TagRepository;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmTagDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmTagPojo;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 标签聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 15:29
 */
@Repository
public class JooqTagRepository implements TagRepository {

  @Resource
  private EcmTagDao tagDao;

  @Override
  public Optional<Tag> findByIdentifier(TagIdentifier identifier) {

    // 校验参数
    requireNonNull(identifier, "identifier");

    // 根据标签唯一识别码查询标签实体
    final EcmTagPojo tagPojo = this.tagDao.fetchOneByName(identifier.getIdentifier());

    // 判断标签实体是否存在
    if (null == tagPojo) {
      return Optional.empty();
    }

    // 构建标签实体并返回实体Optional
    return Optional.of(this.buildTagEntity(tagPojo));
  }

  @Override
  public Optional<Tag> findByDelegator(EntityDelegator delegator) {

    // 校验参数
    requireNonNull(delegator, "delegator");

    // 根据标签委托者唯一识别码查询标签实体
    final EcmTagPojo tagPojo = this.tagDao.fetchOneById(delegator.getId());

    // 判断标签实体是否存在
    if (null == tagPojo) {
      return Optional.empty();
    }

    // 构建标签实体并返回实体Optional
    return Optional.of(this.buildTagEntity(tagPojo));
  }

  @Override
  public Optional<Tag> findByDelegator(Long id) {

    // 校验参数
    requireNonNull(id, "id");

    // 根据标签委托者唯一识别码查询标签实体
    final EcmTagPojo tagPojo = this.tagDao.fetchOneById(id);

    // 判断标签实体是否存在
    if (null == tagPojo) {
      return Optional.empty();
    }

    // 构建标签实体并返回实体Optional
    return Optional.of(this.buildTagEntity(tagPojo));
  }

  @Override
  public List<Tag> find(Tag conditions) {
    return null;
  }

  @Override
  public List<Tag> findByDelegators(List<EntityDelegator> delegators) {

    // 校验参数
    requireNonNull(delegators, "delegators");

    // 根据标签委托者唯一识别码集合查询标签实体
    final List<EcmTagPojo> tagPojoList = this.tagDao.fetchById(delegators.stream().map(EntityDelegator::getId).toArray(Long[]::new));

    // 判断标签实体是否存在
    if (CollectionUtils.isEmpty(tagPojoList)) {
      return null;
    }

    return tagPojoList.stream().map(this::buildTagEntity).collect(Collectors.toList());
  }

  @Override
  public List<Tag> findByDelegatorIds(List<Long> ids) {

    // 校验参数
    requireNonNull(ids, "ids");

    // 根据标签委托者唯一识别码集合查询标签实体
    final List<EcmTagPojo> tagPojoList = this.tagDao.fetchById(ids.toArray(new Long[0]));

    // 判断标签实体是否存在
    if (CollectionUtils.isEmpty(tagPojoList)) {
      return null;
    }

    return tagPojoList.stream().map(this::buildTagEntity).collect(Collectors.toList());
  }

  @Override
  public List<Tag> findByIdentifiers(List<TagIdentifier> identifiers) {

    // 校验参数
    requireNonNull(identifiers, "identifiers");

    // 根据标签唯一标识符集合查询标签实体数据
    final List<EcmTagPojo> tagPojoList =
        this.tagDao.fetchByName(identifiers.stream().map(TagIdentifier::getIdentifier).toArray(String[]::new));

    // 判断标签实体是否存在
    if (CollectionUtils.isEmpty(tagPojoList)) {
      return null;
    }

    return tagPojoList.stream().map(this::buildTagEntity).collect(Collectors.toList());
  }

  @Override
  public void save(Tag aggregator) {

    // 校验参数
    requireNonNull(aggregator, "aggregator");

    // 根据标签唯一识别码查询标签实体
    EcmTagPojo tagPojo = this.tagDao.fetchOneByName(aggregator.getName());
    // 校验标签实体是否存在，存在则抛出业务异常
    if (null != tagPojo) {
      // 业务异常：标签创建失败，名称为{0}的标签已经存在！
      throw new BusinessException(Message.MSG_BIZ_00001, aggregator.getName());
    }

    // 保存标签数据
    tagPojo = this.buildTagPojo(aggregator);
    tagDao.insert(tagPojo);
  }

  @Override
  public void delete(Tag aggregator) {

  }

  /**
   * 将标签数据库实体转换为标签实体
   *
   * @param tagPojo 标签数据库实体
   * @return 标签实体
   */
  private Tag buildTagEntity(final EcmTagPojo tagPojo) {

    // 构建标签实体
    return Tag.newBuilder(tagPojo.getName())
        // 标签展示名称
        .displayName(tagPojo.getDisplayName())
        // 标签描述
        .description(tagPojo.getDescription())
        // 标签URL
        .url(tagPojo.getUrl())
        // 标签图标
        .icon(tagPojo.getIcon())
        // 审核流程状态
        .status(AuditProcessStatus.of(tagPojo.getStatus().intValue()))
        // 实施构建
        .build();
  }

  /**
   * 将标签实体转换为标签数据库实体
   *
   * @param tag 标签实体
   * @return 标签数据库实体
   */
  private EcmTagPojo buildTagPojo(final Tag tag) {

    // 初始化创建标签数据库实体
    final EcmTagPojo tagPojo = new EcmTagPojo();
    // 标签ID
    tagPojo.setId(tag.getDelegator().getId());
    // 标签名称
    tagPojo.setName(tag.getName());
    // 标签展示名称
    tagPojo.setDisplayName(tag.getDisplayName());
    // 标签描述
    tagPojo.setDescription(tag.getDescription());
    // 标签URL
    tagPojo.setUrl(tag.getUrl());
    // 标签图标
    tagPojo.setIcon(tag.getIcon());
    // 审核流程状态
    tagPojo.setStatus(tag.getStatus().key().shortValue());

    return tagPojo;
  }
}

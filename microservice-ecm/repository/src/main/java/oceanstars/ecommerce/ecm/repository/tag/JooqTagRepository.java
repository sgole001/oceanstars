package oceanstars.ecommerce.ecm.repository.tag;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.List;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.tag.entity.Tag;
import oceanstars.ecommerce.ecm.domain.tag.repository.TagRepository;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmTagDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmTagPojo;
import org.springframework.stereotype.Repository;

/**
 * 标签聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 15:29
 */
@Repository
public class JooqTagRepository extends BaseDomainRepository<Tag> implements TagRepository {

  @Resource
  private EcmTagDao tagDao;

  @Override
  public List<Tag> find(ICondition condition) {
    return List.of();
  }

  @Override
  protected void create(Tag tag) {

    // 校验参数
    requireNonNull(tag, "tag");

    // 根据标签唯一识别码查询标签实体
    EcmTagPojo tagPojo = this.tagDao.fetchOneByName(tag.getIdentifier().getIdentifier());
    // 校验标签实体是否存在，存在则抛出业务异常
    if (null != tagPojo) {
      // 业务异常：标签创建失败，名称为{0}的标签已经存在！
      throw new BusinessException(Message.MSG_BIZ_00001, tag.getIdentifier().getIdentifier());
    }

    // 保存标签数据
    tagPojo = this.buildTagPojo(tag);
    tagDao.insert(tagPojo);
  }

  @Override
  protected void modify(Tag tag) {

  }

  @Override
  public void delete(Tag tag) {

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
    // 标签名称
    tagPojo.setName(tag.getIdentifier().getIdentifier());
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

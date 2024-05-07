package oceanstars.ecommerce.ecm.repository.category;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.List;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.Message;
import oceanstars.ecommerce.ecm.domain.category.entity.Category;
import oceanstars.ecommerce.ecm.domain.category.repository.CategoryRepository;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.EcmCategoryDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.daos.RelCategoryCategoryDao;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.EcmCategoryPojo;
import oceanstars.ecommerce.ecm.repository.generate.tables.pojos.RelCategoryCategoryPojo;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsTransactional;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 分类聚合资源库接口实现(JOOQ基础设施)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/25 15:29
 */
@Repository
public class JooqCategoryRepository implements CategoryRepository {

  @Resource
  private EcmCategoryDao categoryDao;

  @Resource
  private RelCategoryCategoryDao relCategoryCategoryDao;

  @Override
  public List<Category> find(ICondition condition) {
    return List.of();
  }

  @OceanstarsTransactional(rollbackFor = Exception.class)
  @Override
  public void save(Category aggregator) {

    // 校验参数
    requireNonNull(aggregator, "aggregator");

    // 根据分类唯一标识符查询分类数据
    EcmCategoryPojo categoryPojo = this.categoryDao.fetchOneByName(aggregator.getIdentifier().getIdentifier());
    // 判断分类数据是否存在, 存在则抛出业务异常
    if (null != categoryPojo) {
      // 业务异常：分类创建失败，名称为{0}的分类已经存在！
      throw new BusinessException(Message.MSG_BIZ_00000, aggregator.getIdentifier().getIdentifier());
    }

    // 保存分类数据
    categoryPojo = this.buildCategoryPojo(aggregator);
    this.categoryDao.insert(categoryPojo);

    // 判断分类是否有父级分类
    if (!CollectionUtils.isEmpty(aggregator.getParents())) {
      // 获取分类委托者ID
      final Long categoryId = categoryPojo.getId();
      // 构建分类隶属关系数据
      final List<RelCategoryCategoryPojo> categoryCategoryPojoList = aggregator.getParents().stream()
          .map(parent -> this.buildRelCategoryCategoryPojo(categoryId, parent.getDelegator().getId())).toList();
      // 保存分类隶属关系数据
      this.relCategoryCategoryDao.insert(categoryCategoryPojoList);
    }
  }

  @Override
  public void delete(Category aggregator) {

  }

  /**
   * 将分类数据库实体转换为分类实体
   *
   * @param categoryPojo 分类数据库实体
   * @return 分类实体
   */
  private Category buildCategoryEntity(final EcmCategoryPojo categoryPojo) {

    // 构建并返回分类实体
    final Category category = Category.newBuilder(categoryPojo.getName())
        // 分类展示名称
        .displayName(categoryPojo.getDisplayName())
        // 分类描述
        .description(categoryPojo.getDescription())
        // 分类URL
        .url(categoryPojo.getUrl())
        // 审核流程状态
        .status(AuditProcessStatus.of(categoryPojo.getStatus().intValue()))
        // 构建分类实体
        .build();

    // 构建分类委托者实体
    category.delegate(categoryPojo);

    return category;
  }

  /**
   * 将分类实体转换为分类数据库实体
   *
   * @param category 分类实体
   * @return 分类数据库实体
   */
  private EcmCategoryPojo buildCategoryPojo(final Category category) {

    // 初始化创建分类数据库实体
    final EcmCategoryPojo categoryPojo = new EcmCategoryPojo();
    // 分类ID
    categoryPojo.setId(category.getDelegator().getId());
    // 分类名称
    categoryPojo.setName(category.getIdentifier().getIdentifier());
    // 分类展示名称
    categoryPojo.setDisplayName(category.getDisplayName());
    // 分类描述
    categoryPojo.setDescription(category.getDescription());
    // 分类URL
    categoryPojo.setUrl(category.getUrl());
    // 审核流程状态
    categoryPojo.setStatus(category.getStatus().key().shortValue());

    return categoryPojo;
  }

  /**
   * 将分类隶属关系实体转换为分类隶属关系数据库实体
   *
   * @param cid 分类ID
   * @param pid 父级分类ID
   * @return 分类隶属关系数据库实体
   */
  private RelCategoryCategoryPojo buildRelCategoryCategoryPojo(final Long cid, final Long pid) {

    // 初始化创建分类隶属关系数据库实体
    final RelCategoryCategoryPojo categoryCategoryPojo = new RelCategoryCategoryPojo();
    // 分类ID
    categoryCategoryPojo.setCategory(cid);
    // 父级分类ID
    categoryCategoryPojo.setParent(pid);

    return categoryCategoryPojo;
  }
}

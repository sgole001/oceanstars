package oceanstars.ecommerce.user.repository.constraint;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import oceanstars.ecommerce.common.domain.repository.BaseDomainRepository;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.common.exception.BusinessException;
import oceanstars.ecommerce.common.tools.JsonUtil;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintType;
import oceanstars.ecommerce.user.constant.enums.UserEnums.Message;
import oceanstars.ecommerce.user.domain.constraint.entity.Constraint;
import oceanstars.ecommerce.user.domain.constraint.entity.ConstraintIdentifier;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.ConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.repository.ConstraintRepository;
import oceanstars.ecommerce.user.domain.constraint.repository.condition.ConstraintFetchCondition;
import oceanstars.ecommerce.user.repository.constraint.factory.ConstraintRuleFactory;
import oceanstars.ecommerce.user.repository.constraint.view.ConstraintViewDao;
import oceanstars.ecommerce.user.repository.constraint.view.bo.ConstraintView;
import oceanstars.ecommerce.user.repository.generate.tables.daos.RelConstraintRuleDao;
import oceanstars.ecommerce.user.repository.generate.tables.daos.UserConstraintDao;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelConstraintRulePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserConstraintPojo;
import org.jooq.JSON;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 约束聚合仓储实现类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/28 14:26
 */
@Repository
public class JooqConstraintRepository extends BaseDomainRepository<Constraint> implements ConstraintRepository {

  /**
   * 用户约束数据访问对象
   */
  @Resource
  private UserConstraintDao constraintDao;

  /**
   * 约束规则关联数据访问对象
   */
  @Resource
  private RelConstraintRuleDao constraintRuleDao;

  /**
   * 约束视图数据访问对象
   */
  @Resource
  private ConstraintViewDao constraintViewDao;

  @Override
  protected void create(Constraint constraint) {

    // 校验参数
    requireNonNull(constraint, "constraint");

    // 获取约束唯一标识符
    final ConstraintIdentifier constraintIdentifier = constraint.getIdentifier();

    // 构建约束查询条件(根据约束唯一标识符)
    final ConstraintFetchCondition condition = ConstraintFetchCondition.newBuilder()
        // 约束名称
        .name(constraintIdentifier.getName())
        // 构建查询条件
        .build();

    // 根据约束查询条件查询约束视图信息
    final List<ConstraintView> constraints = this.constraintViewDao.fetch(condition);

    // 校验约束是否已存在，如果存在则抛出业务异常
    if (!CollectionUtils.isEmpty(constraints)) {
      throw new BusinessException(Message.MSG_BIZ_30001, constraintIdentifier.getName());
    }

    // 构建约束数据库映射
    final UserConstraintPojo constraintPojo = this.buildConstraintRepoPojo(constraint);
    // 保存数据数据
    this.constraintDao.insert(constraintPojo);

    // 委托账号实体
    constraint.delegate(constraintPojo);

    // 构建约束规则关系数据库映射
    final List<RelConstraintRulePojo> constraintRulePojoList = this.buildRelConstraintRuleRepoPojo(constraint);
    // 保存约束规则关系数据
    this.constraintRuleDao.insert(constraintRulePojoList);
  }

  @Override
  protected void modify(Constraint constraint) {

  }

  @Override
  public List<Constraint> find(ICondition condition) {

    // 校验参数
    requireNonNull(condition, "condition");

    // 根据约束查询条件查询约束视图信息
    final List<ConstraintView> constraints = this.constraintViewDao.fetch(condition);

    // 校验约束是否存在,如果不存在则返回空列表
    if (CollectionUtils.isEmpty(constraints)) {
      return List.of();
    }

    // 构建权限实体列表
    return constraints.stream().map(this::buildConstraintEntity).toList();
  }

  @Override
  public void delete(Constraint constraint) {

  }

  /**
   * 构建约束实体
   *
   * @param constraintView 约束视图
   * @return 约束实体
   */
  private Constraint buildConstraintEntity(final ConstraintView constraintView) {

    // 获取约束数据
    final UserConstraintPojo constraintPojo = constraintView.getConstraint();
    // 获取约束规则关系数据
    final List<RelConstraintRulePojo> constraintRulePojoList = constraintView.getRules();

    // 获取约束类型
    final ConstraintType type = ConstraintType.of(constraintPojo.getType().intValue());

    // 初始化约束实体
    final Constraint constraint = Constraint.newBuilder(constraintPojo.getName(), type)
        // 约束说明
        .description(constraintPojo.getDesc())
        // 约束规则
        .rules(new ArrayList<>(constraintRulePojoList.size()))
        // 实施构建
        .build();

    // 遍历约束规则关系数据
    constraintRulePojoList.forEach(rulePojo -> {
      // 解析约束规则
      final ConstraintRuleValueObject constraintRule = ConstraintRuleFactory.getConstraintRule(rulePojo.getType(), rulePojo.getRule().data());
      // 委托约束规则数据
      constraintRule.delegate(rulePojo);
      // 添加约束规则
      constraint.getRules().add(constraintRule);
    });

    // 委托约束数据
    constraint.delegate(constraintPojo);

    return constraint;
  }

  /**
   * 构建约束数据库映射
   *
   * @param constraint 约束实体
   * @return 约束数据库映射
   */
  private UserConstraintPojo buildConstraintRepoPojo(final Constraint constraint) {

    // 初始化约束数据库映射
    final UserConstraintPojo constraintPojo = new UserConstraintPojo();

    // 约束名称
    constraintPojo.setName(constraint.getIdentifier().getName());
    // 约束类型
    constraintPojo.setType(constraint.getType().key().shortValue());
    // 约束说明
    if (StringUtils.hasText(constraint.getDescription())) {
      constraintPojo.setDesc(constraint.getDescription());
    }

    return constraintPojo;
  }

  /**
   * 构建约束规则关系数据库映射列表
   *
   * @param constraint 约束实体
   * @return 约束规则关系数据库映射列表
   */
  private List<RelConstraintRulePojo> buildRelConstraintRuleRepoPojo(final Constraint constraint) {

    // 构建约束规则关系数据库映射列表
    return constraint.getRules().stream().map(rule -> {
      // 初始化约束规则关系数据库映射
      final RelConstraintRulePojo relConstraintRulePojo = new RelConstraintRulePojo();
      // 约束ID
      relConstraintRulePojo.setConstraint(constraint.getDelegator().getId());
      // 约束规则类型
      relConstraintRulePojo.setType(rule.determineRuleType().key().shortValue());
      // 约束规则
      relConstraintRulePojo.setRule(JSON.json(JsonUtil.toStringWithLocalDateTime(rule)));

      return relConstraintRulePojo;
    }).toList();
  }
}

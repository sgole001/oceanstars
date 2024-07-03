package oceanstars.ecommerce.user.repository.constraint.view;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import oceanstars.ecommerce.common.domain.repository.condition.ICondition;
import oceanstars.ecommerce.user.domain.constraint.repository.condition.ConstraintFetchCondition;
import oceanstars.ecommerce.user.repository.constraint.view.bo.ConstraintView;
import oceanstars.ecommerce.user.repository.generate.tables.RelConstraintRule;
import oceanstars.ecommerce.user.repository.generate.tables.UserConstraint;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelConstraintRulePojo;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.UserConstraintPojo;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 约束视图数据访问对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/28 14:34
 */
@Repository
public class ConstraintViewDao {

  /**
   * 用户约束表
   */
  final static UserConstraint T_CONSTRAINT = UserConstraint.USER_CONSTRAINT.as("constraint");

  /**
   * 约束规则关联表
   */
  final static RelConstraintRule REL_CONSTRAINT_RULE = RelConstraintRule.REL_CONSTRAINT_RULE.as("rule");

  /**
   * Jooq数据库操作对象
   */
  @Resource
  private DefaultDSLContext dsl;

  /**
   * 根据条件查询约束视图
   *
   * @param condition 查询条件
   * @return 约束视图列表
   */
  public List<ConstraintView> fetch(final ICondition condition) {

    // 初始化查询条件
    Condition searchCondition = DSL.trueCondition();

    // 转换查询条件为约束查询条件
    final ConstraintFetchCondition fetchCondition = (ConstraintFetchCondition) condition;

    // 约束ID
    if (null != fetchCondition.getId()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.ID.eq(fetchCondition.getId()));
    }

    // 约束名称
    if (null != fetchCondition.getName()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.NAME.likeIgnoreCase(fetchCondition.getName().trim()));
    }
    // 约束类型
    if (!CollectionUtils.isEmpty(fetchCondition.getTypes())) {
      if (fetchCondition.getTypes().size() == 1) {
        searchCondition = searchCondition.and(T_CONSTRAINT.TYPE.eq(fetchCondition.getTypes().stream().findFirst().orElseThrow().key().shortValue()));
      } else {
        searchCondition = searchCondition.and(T_CONSTRAINT.TYPE.in(fetchCondition.getTypes()));
      }
    }
    // 约束规则类型
    if (!CollectionUtils.isEmpty(fetchCondition.getRuleTypes())) {
      if (fetchCondition.getRuleTypes().size() == 1) {
        searchCondition = searchCondition.and(
            REL_CONSTRAINT_RULE.TYPE.eq(fetchCondition.getRuleTypes().stream().findFirst().orElseThrow().key().shortValue()));
      } else {
        searchCondition = searchCondition.and(REL_CONSTRAINT_RULE.TYPE.in(fetchCondition.getRuleTypes()));
      }
    }

    // 约束创建开始时间
    if (null != fetchCondition.getCreateStartTime()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.CREATE_AT.ge(fetchCondition.getCreateStartTime()));
    }
    // 约束创建结束时间
    if (null != fetchCondition.getCreateEndTime()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.CREATE_AT.le(fetchCondition.getCreateEndTime()));
    }
    // 约束更新开始时间
    if (null != fetchCondition.getUpdateStartTime()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.UPDATE_AT.ge(fetchCondition.getUpdateStartTime()));
    }
    // 约束更新结束时间
    if (null != fetchCondition.getUpdateEndTime()) {
      searchCondition = searchCondition.and(T_CONSTRAINT.UPDATE_AT.le(fetchCondition.getUpdateEndTime()));
    }

    // 查询约束视图信息
    final Map<ConstraintView, List<RelConstraintRulePojo>> results = dsl.select(T_CONSTRAINT.fields())
        .select(REL_CONSTRAINT_RULE.fields())
        .from(T_CONSTRAINT)
        .join(REL_CONSTRAINT_RULE)
        .on(T_CONSTRAINT.ID.eq(REL_CONSTRAINT_RULE.CONSTRAINT))
        .where(searchCondition)
        .orderBy(T_CONSTRAINT.CREATE_AT.desc(), T_CONSTRAINT.ID.asc(), REL_CONSTRAINT_RULE.CREATE_AT.desc())
        .collect(
            Collectors.groupingBy(
                record -> {
                  // 初始化约束视图对象
                  final ConstraintView constraintView = new ConstraintView();
                  // 设定约束数据
                  constraintView.setConstraint(record.into(T_CONSTRAINT).into(UserConstraintPojo.class));

                  return constraintView;
                },
                Collectors.mapping(record -> record.into(REL_CONSTRAINT_RULE).into(RelConstraintRulePojo.class), Collectors.toList())
            ));

    // 查询结果为空，返回空
    if (CollectionUtils.isEmpty(results)) {
      return null;
    }

    // 遍历查询结果, 构建约束视图信息并排序后返回
    return results.entrySet().stream().map(entry -> {
      entry.getKey().setRules(entry.getValue());
      return entry.getKey();
    }).sorted().toList();
  }
}

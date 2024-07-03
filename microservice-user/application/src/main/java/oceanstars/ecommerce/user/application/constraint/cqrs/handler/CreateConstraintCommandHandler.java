package oceanstars.ecommerce.user.application.constraint.cqrs.handler;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import oceanstars.ecommerce.common.cqrs.ICommandHandler;
import oceanstars.ecommerce.common.domain.event.EventBus;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.BaseConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.UserCreateConstraintCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.UserCreateConstraintResult;
import oceanstars.ecommerce.user.constant.enums.UserEnums.ConstraintType;
import oceanstars.ecommerce.user.domain.constraint.entity.Constraint;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.AccountConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.ConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.DataConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.RelationConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.entity.valueobject.SessionConstraintRuleValueObject;
import oceanstars.ecommerce.user.domain.constraint.repository.ConstraintRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 创建约束命令处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/2 11:44
 */
@Component(value = "createConstraintCommandHandler")
public class CreateConstraintCommandHandler implements ICommandHandler<UserCreateConstraintResult, UserCreateConstraintCommand> {

  /**
   * 事件总线
   */
  private final EventBus eventGateway;

  /**
   * 约束资源仓储
   */
  private final ConstraintRepository constraintRepository;

  /**
   * 构造函数
   *
   * @param eventGateway         事件总线
   * @param constraintRepository 约束资源仓储
   */
  public CreateConstraintCommandHandler(EventBus eventGateway, ConstraintRepository constraintRepository) {
    this.eventGateway = eventGateway;
    this.constraintRepository = constraintRepository;
  }

  @Override
  public UserCreateConstraintResult handle(UserCreateConstraintCommand command) {

    // 构建约束规则列表
    final List<ConstraintRuleValueObject> rules = new ArrayList<>(command.getRulesList().size());

    // 遍历约束规则列表
    command.getRulesList().forEach(rule -> {
      // 判定是否存在关系约束
      if (rule.hasRelation()) {
        // 构建关系约束规则
        final RelationConstraintRuleValueObject.Builder builder = RelationConstraintRuleValueObject.newBuilder();
        // 构建基础约束规则
        this.buildBaseConstraintRule(rule.getRelation().getBase(), builder);

        // 互斥关系列表
        if (!CollectionUtils.isEmpty(rule.getRelation().getExclusivityList())) {
          builder.exclusivity(new HashSet<>(rule.getRelation().getExclusivityList()));
        }

        // 最大基数
        if (-1 != rule.getRelation().getMaxCardinality()) {
          builder.maxCardinality(rule.getRelation().getMaxCardinality());
        }

        // 最小基数
        if (-1 != rule.getRelation().getMinCardinality()) {
          builder.minCardinality(rule.getRelation().getMinCardinality());
        }

        // 添加关系约束规则
        rules.add(builder.build());
      }

      // 判定是否存在会话约束
      if (rule.hasSession()) {
        // 构建会话约束规则
        final SessionConstraintRuleValueObject.Builder builder = SessionConstraintRuleValueObject.newBuilder();
        // 构建基础约束规则
        this.buildBaseConstraintRule(rule.getSession().getBase(), builder);

        // 添加会话约束规则
        rules.add(builder.build());
      }

      // 判定是否存在账号约束
      if (rule.hasAccount()) {
        // 构建账号约束规则
        final AccountConstraintRuleValueObject.Builder builder = AccountConstraintRuleValueObject.newBuilder();
        // 构建基础约束规则
        this.buildBaseConstraintRule(rule.getAccount().getBase(), builder);

        // 添加账号约束规则
        rules.add(builder.build());
      }

      // 判定是否存在数据约束
      if (rule.hasData()) {
        // 构建数据约束规则
        final DataConstraintRuleValueObject.Builder builder = DataConstraintRuleValueObject.newBuilder();
        // 构建基础约束规则
        this.buildBaseConstraintRule(rule.getData().getBase(), builder);

        // 添加数据约束规则
        rules.add(builder.build());
      }
    });

    // 构建约束实体
    final Constraint constraint = Constraint.newBuilder(command.getName(), ConstraintType.of(command.getType()))
        // 约束说明
        .description(command.getDesc())
        // 约束规则列表
        .rules(rules)
        // 实施构建
        .build();

    // 保存约束实体信息
    this.constraintRepository.save(constraint);

    // 发布领域事件
//    this.eventGateway.publish(new ConstraintCreated(constraint, new ConstraintCreatedPayload(constraint.getDelegator().getId())));

    return UserCreateConstraintResult.newBuilder().setId(constraint.getDelegator().getId()).build();
  }

  /**
   * 构建基础约束规则
   *
   * @param base    基础约束规则
   * @param builder 约束规则构建器
   */
  private void buildBaseConstraintRule(BaseConstraintRule base, ConstraintRuleValueObject.Builder<?> builder) {

    // 规则执行优先级设定
    if (-1 != base.getSalience()) {
      builder.salience(base.getSalience());
    }

    // 规则是否启用设定
    builder.enabled(base.getEnabled());

    // 判定是否设置规则生效时间
    if (Timestamp.getDefaultInstance() != base.getEffective()) {
      // 获取时间戳
      final Instant instant = Instant.ofEpochSecond(base.getEffective().getSeconds(), base.getEffective().getNanos());
      // 设置规则生效时间
      builder.effective(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
    }

    // 判定是否设置规则失效时间
    if (Timestamp.getDefaultInstance() != base.getExpire()) {
      // 获取时间戳
      final Instant instant = Instant.ofEpochSecond(base.getExpire().getSeconds(), base.getExpire().getNanos());
      // 设置规则失效时间
      builder.expire(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
    }
  }
}

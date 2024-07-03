package oceanstars.ecommerce.user.controller.v1.constraint.handle;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import oceanstars.ecommerce.common.cqrs.Bus;
import oceanstars.ecommerce.common.restful.BaseRestHandler;
import oceanstars.ecommerce.common.restful.BaseRestResponseData;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.CreateConstraintRequestMessage;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.AccountConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.BaseConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.ConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.DataConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.RelationConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.SessionConstraintRule;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.UserCreateConstraintCommand;
import oceanstars.ecommerce.user.api.rpc.v1.dto.constraint.UserCreateConstraintResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 创建约束的Restful请求处理
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 19:21
 */
@Component(value = "createConstraintRestHandler")
public class CreateConstraintRestHandler extends BaseRestHandler<CreateConstraintRequestMessage> {

  /**
   * CQRS处理总线
   */
  private final Bus cqrsGateway;

  /**
   * 构造函数
   *
   * @param cqrsGateway CQRS处理总线
   */
  public CreateConstraintRestHandler(Bus cqrsGateway) {
    this.cqrsGateway = cqrsGateway;
  }

  @Override
  public GeneratedMessageV3[] parsingRequestMessage(CreateConstraintRequestMessage restRequestMessage) {

    // 构建创建账号命令请求参数
    final UserCreateConstraintCommand.Builder userCreateConstraintCommandBuilder = UserCreateConstraintCommand.newBuilder()
        // 约束名称
        .setName(restRequestMessage.getName())
        // 约束类型
        .setType(restRequestMessage.getType())
        // 约束说明
        .setDesc(restRequestMessage.getDesc());

    // 判定是否添加关系约束规则列表
    if (!CollectionUtils.isEmpty(restRequestMessage.getRelationRules())) {

      restRequestMessage.getRelationRules().forEach(relationRule -> {
        // 添加关系约束规则
        userCreateConstraintCommandBuilder.addRules(ConstraintRule.newBuilder().setRelation(RelationConstraintRule.newBuilder()
            // 基础约束
            .setBase(this.buildBaseConstraintRule(relationRule))
            // 互斥关系列表
            .addAllExclusivity(relationRule.getExclusivity())
            // 最大基数
            .setMaxCardinality(relationRule.getMaxCardinality())
            // 最小基数
            .setMinCardinality(relationRule.getMinCardinality())
            // 实施构建
            .build()));
      });
    }

    // 判定是否添加会话约束规则列表
    if (!CollectionUtils.isEmpty(restRequestMessage.getSessionRules())) {

      restRequestMessage.getSessionRules().forEach(sessionRule -> {
        // 添加会话约束规则
        userCreateConstraintCommandBuilder.addRules(ConstraintRule.newBuilder().setSession(SessionConstraintRule.newBuilder()
            // 基础约束
            .setBase(this.buildBaseConstraintRule(sessionRule))
            // 实施构建
            .build()));
      });
    }

    // 判定是否添加账号约束规则列表
    if (!CollectionUtils.isEmpty(restRequestMessage.getAccountRules())) {

      restRequestMessage.getAccountRules().forEach(accountRule -> {

        // 添加账号约束规则
        userCreateConstraintCommandBuilder.addRules(ConstraintRule.newBuilder().setAccount(AccountConstraintRule.newBuilder()
            // 基础约束
            .setBase(this.buildBaseConstraintRule(accountRule))
            // 实施构建
            .build()));
      });
    }

    // 判定是否添加数据约束规则列表
    if (!CollectionUtils.isEmpty(restRequestMessage.getDataRules())) {

      restRequestMessage.getDataRules().forEach(dataRule -> {
        // 添加数据约束规则
        userCreateConstraintCommandBuilder.addRules(ConstraintRule.newBuilder().setData(DataConstraintRule.newBuilder()
            // 基础约束
            .setBase(this.buildBaseConstraintRule(dataRule))
            // 实施构建
            .build()));
      });
    }

    return new GeneratedMessageV3[]{userCreateConstraintCommandBuilder.build()};
  }

  @Override
  public GeneratedMessageV3 process(GeneratedMessageV3[] messages) {

    // 获取创建约束命令请求参数
    final UserCreateConstraintCommand createConstraintCommand = (UserCreateConstraintCommand) messages[0];

    // 执行命令
    return cqrsGateway.executeCommand(createConstraintCommand);
  }

  @Override
  public RestResponseMessage packageResponseMessage(GeneratedMessageV3 result) {

    // 获取创建约束命令处理结果
    final UserCreateConstraintResult createConstraintResult = (UserCreateConstraintResult) result;

    return RestResponseMessage.newBuilder(HttpStatus.OK)
        // 设置响应数据
        .data(BaseRestResponseData.newBuilder().id(String.valueOf(createConstraintResult.getId())).build())
        // 执行构建
        .build();
  }

  /**
   * 构建基础约束规则
   *
   * @param base 基础约束规则
   * @return 基础约束规则
   */
  private BaseConstraintRule buildBaseConstraintRule(oceanstars.ecommerce.user.api.rest.v1.request.constraint.data.BaseConstraintRule base) {

    // 构建基础约束规则构建器
    final BaseConstraintRule.Builder baseConstraintRuleBuilder = BaseConstraintRule.newBuilder()
        // 规则执行优先级
        .setSalience(base.getSalience())
        // 规则是否启用
        .setEnabled(base.getEnabled());

    // 判定是否设置规则生效时间
    if (null != base.getEffective()) {
      // 获取时间戳
      final Instant instant = base.getEffective().atZone(ZoneId.systemDefault()).toInstant();
      // 设置规则生效时间
      baseConstraintRuleBuilder.setEffective(Timestamp.newBuilder()
          .setSeconds(instant.getEpochSecond())
          .setNanos(instant.getNano()).build());
    }

    // 判定是否设置规则失效时间
    if (null != base.getExpire()) {
      // 获取时间戳
      final Instant instant = base.getExpire().atZone(ZoneId.systemDefault()).toInstant();
      // 设置规则生效时间
      baseConstraintRuleBuilder.setExpire(Timestamp.newBuilder()
          .setSeconds(instant.getEpochSecond())
          .setNanos(instant.getNano()).build());
    }

    return baseConstraintRuleBuilder.build();
  }
}

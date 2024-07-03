package oceanstars.ecommerce.user.api.rest.v1.request.constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.data.AccountConstraintRule;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.data.DataConstraintRule;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.data.RelationConstraintRule;
import oceanstars.ecommerce.user.api.rest.v1.request.constraint.data.SessionConstraintRule;

/**
 * 创建约束接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 16:52
 */
@Schema(name = "CreateConstraintRequestMessage", description = "创建约束接口请求参数")
public class CreateConstraintRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = -1593229987366621077L;

  /**
   * 约束名称
   */
  @Schema(description = "约束名称")
  private String name;

  /**
   * 约束类型
   */
  @Schema(description = "约束类型")
  private Integer type;

  /**
   * 约束说明
   */
  @Schema(description = "约束说明")
  private String desc;

  /**
   * 约束规则列表: 关系约束规则
   */
  @Schema(description = "约束规则列表: 关系约束规则")
  private List<RelationConstraintRule> relationRules;

  /**
   * 约束规则列表: 会话约束规则
   */
  @Schema(description = "约束规则列表: 会话约束规则")
  private List<SessionConstraintRule> sessionRules;

  /**
   * 约束规则列表: 账户约束规则
   */
  @Schema(description = "约束规则列表: 账户约束规则")
  private List<AccountConstraintRule> accountRules;

  /**
   * 约束规则列表: 数据约束规则
   */
  @Schema(description = "约束规则列表: 数据约束规则")
  private List<DataConstraintRule> dataRules;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<RelationConstraintRule> getRelationRules() {
    return relationRules;
  }

  public void setRelationRules(List<RelationConstraintRule> relationRules) {
    this.relationRules = relationRules;
  }

  public List<SessionConstraintRule> getSessionRules() {
    return sessionRules;
  }

  public void setSessionRules(List<SessionConstraintRule> sessionRules) {
    this.sessionRules = sessionRules;
  }

  public List<AccountConstraintRule> getAccountRules() {
    return accountRules;
  }

  public void setAccountRules(List<AccountConstraintRule> accountRules) {
    this.accountRules = accountRules;
  }

  public List<DataConstraintRule> getDataRules() {
    return dataRules;
  }

  public void setDataRules(List<DataConstraintRule> dataRules) {
    this.dataRules = dataRules;
  }
}

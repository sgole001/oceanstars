package oceanstars.ecommerce.user.api.rest.v1.request.constraint.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import oceanstars.ecommerce.common.constant.DatePattern;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 约束规则数据: 基础约束规则
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/1 17:33
 */
@Schema(name = "BaseConstraintRule", description = "基础约束规则")
public class BaseConstraintRule {

  /**
   * 规则执行优先级(数值越大优先级越高)
   */
  @Schema(description = "规则执行优先级(数值越大优先级越高)")
  private Integer salience;

  /**
   * 规则是否启用
   */
  @Schema(description = "规则是否启用")
  private Boolean enabled;

  /**
   * 规则生效时间
   */
  @Schema(description = "规则生效时间")
  @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
  private LocalDateTime effective;

  /**
   * 规则失效时间
   */
  @Schema(description = "规则失效时间")
  @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
  private LocalDateTime expire;

  public Integer getSalience() {
    return salience;
  }

  public void setSalience(Integer salience) {
    this.salience = salience;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public LocalDateTime getEffective() {
    return effective;
  }

  public void setEffective(LocalDateTime effective) {
    this.effective = effective;
  }

  public LocalDateTime getExpire() {
    return expire;
  }

  public void setExpire(LocalDateTime expire) {
    this.expire = expire;
  }
}

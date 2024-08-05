package oceanstars.ecommerce.common.security;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.convert.DurationUnit;

/**
 * IAM令牌配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/24 14:21
 */
public class TokenProperties {

  /**
   * Token过期Duration
   * <p>单位: 毫秒</p>
   */
  @DurationUnit(ChronoUnit.MILLIS)
  private Duration exp;

  /**
   * Token盐值
   */
  private String salt;

  public Duration getExp() {
    return exp;
  }

  public void setExp(Duration exp) {
    this.exp = exp;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}

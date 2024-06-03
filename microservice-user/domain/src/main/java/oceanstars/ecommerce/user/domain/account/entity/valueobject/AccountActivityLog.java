package oceanstars.ecommerce.user.domain.account.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 账号活动日志值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/6/3 15:12
 */
public class AccountActivityLog extends ValueObject {

  @Serial
  private static final long serialVersionUID = -5708263751746379600L;

  /**
   * 活动IP
   */
  private final String activeIp;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private AccountActivityLog(Builder builder) {
    activeIp = builder.activeIp;
  }

  /**
   * 创建账号活动日志构建器
   *
   * @return 账号活动日志构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getActiveIp() {
    return activeIp;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/6/3 15:14
   */
  public static final class Builder {

    private String activeIp;

    public Builder() {
    }

    public Builder activeIp(String val) {
      activeIp = val;
      return this;
    }

    public AccountActivityLog build() {
      return new AccountActivityLog(this);
    }
  }
}

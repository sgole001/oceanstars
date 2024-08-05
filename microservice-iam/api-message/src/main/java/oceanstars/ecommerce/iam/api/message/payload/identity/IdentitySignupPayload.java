package oceanstars.ecommerce.iam.api.message.payload.identity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 领域事件业务负载: 身份注册
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/24 18:22
 */
public class IdentitySignupPayload implements Serializable {

  @Serial
  private static final long serialVersionUID = -7723250046783575447L;

  // 账号域
  private final Integer domain;

  // 账号访问认证方式
  private final String access;

  // 账号访问认证方式类型
  private final Integer accessType;

  // 账号名称
  private final String userName;

  private IdentitySignupPayload(Builder builder) {
    domain = builder.domain;
    access = builder.access;
    accessType = builder.accessType;
    userName = builder.userName;
  }

  /**
   * 创建IdentitySignupPayload实体构建器
   *
   * @return IdentitySignupPayload实体构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Integer getDomain() {
    return domain;
  }

  public String getAccess() {
    return access;
  }

  public Integer getAccessType() {
    return accessType;
  }

  public String getUserName() {
    return userName;
  }

  /**
   * 构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/25 10:41
   */
  public static final class Builder {

    private Integer domain;
    private String access;
    private Integer accessType;
    private String userName;

    public Builder() {
    }

    public Builder domain(Integer val) {
      domain = val;
      return this;
    }

    public Builder access(String val) {
      access = val;
      return this;
    }

    public Builder accessType(Integer val) {
      accessType = val;
      return this;
    }

    public Builder userName(String val) {
      userName = val;
      return this;
    }

    public IdentitySignupPayload build() {
      return new IdentitySignupPayload(this);
    }
  }
}

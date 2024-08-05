package oceanstars.ecommerce.iam.domain.identity.entity.valueobject;

import java.io.Serial;
import java.time.LocalDateTime;
import javax.crypto.spec.SecretKeySpec;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 身份令牌值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/3 17:28
 */
public final class Token extends ValueObject {

  @Serial
  private static final long serialVersionUID = -7377411445987776899L;

  /**
   * 令牌 (JWS)内容
   */
  private final String value;

  /**
   * 密钥规范 (构建令牌时使用)
   */
  private final SecretKeySpec keySpec;

  /**
   * 令牌过期时间 - 绝对时间
   */
  private final LocalDateTime expireAt;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Token(Builder builder) {
    value = builder.value;
    keySpec = builder.keySpec;
    expireAt = builder.expireAt;
  }

  /**
   * 创建Token实体构建器
   *
   * @return Token实体构建器
   */
  public static Builder newBuilder(String value) {
    return new Builder(value);
  }

  public String getValue() {
    return value;
  }

  public SecretKeySpec getKeySpec() {
    return keySpec;
  }

  public LocalDateTime getExpireAt() {
    return expireAt;
  }

  /**
   * 构建器：初始化Token实体
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/3 17:56
   */
  public static final class Builder {

    private final String value;
    private SecretKeySpec keySpec;
    private LocalDateTime expireAt;

    public Builder(String value) {
      this.value = value;
    }

    public Builder keySpec(SecretKeySpec val) {
      keySpec = val;
      return this;
    }

    public Builder expireAt(LocalDateTime val) {
      expireAt = val;
      return this;
    }

    public Token build() {
      return new Token(this);
    }
  }
}

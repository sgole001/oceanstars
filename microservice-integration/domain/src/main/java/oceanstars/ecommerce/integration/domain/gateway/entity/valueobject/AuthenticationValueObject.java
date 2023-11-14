package oceanstars.ecommerce.integration.domain.gateway.entity.valueobject;

import oceanstars.ecommerce.common.domain.ValueObject;

/**
 * 集成目标服务认证信息实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/29 10:48 上午
 */
public class AuthenticationValueObject extends ValueObject {

  /**
   * 集成目标服务认证用Key
   */
  private final String appKey;

  /**
   * 集成目标服务认证用秘钥
   */
  private final String appSecret;

  /**
   * 集成目标服务认证证书
   */
  private final byte[] x509Certificate;

  private AuthenticationValueObject(Builder builder) {
    appKey = builder.appKey;
    appSecret = builder.appSecret;
    x509Certificate = builder.x509Certificate;
  }

  /**
   * 创建集成目标服务认证信息实体构建器
   *
   * @return 集成目标服务认证信息实体构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAppKey() {
    return appKey;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public byte[] getX509Certificate() {
    return x509Certificate;
  }

  /**
   * 集成目标服务认证信息实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/12/29 12:08 下午
   */
  public static final class Builder {

    private String appKey;
    private String appSecret;
    private byte[] x509Certificate;

    public Builder appKey(String val) {
      appKey = val;
      return this;
    }

    public Builder appSecret(String val) {
      appSecret = val;
      return this;
    }

    public Builder x509Certificate(byte[] val) {
      x509Certificate = val;
      return this;
    }

    public AuthenticationValueObject build() {
      return new AuthenticationValueObject(this);
    }
  }
}

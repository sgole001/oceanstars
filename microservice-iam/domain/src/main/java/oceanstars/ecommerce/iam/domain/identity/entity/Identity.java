package oceanstars.ecommerce.iam.domain.identity.entity;

import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.iam.constant.enums.IamEnums.IdentityType;
import oceanstars.ecommerce.iam.domain.identity.entity.valueobject.MfaSettings;
import oceanstars.ecommerce.iam.domain.identity.entity.valueobject.Token;

/**
 * 身份实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 10:10
 */
public class Identity extends AggregateRoot<IdentityIdentifier> {

  /**
   * 访问令牌
   */
  private Token accessToken;

  /**
   * 刷新令牌
   */
  private Token refreshToken;

  /**
   * 多因素认证设置
   */
  private MfaSettings mfaSettings;

  /**
   * 角色ID集合
   */
  private Set<Long> roles;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private Identity(Builder builder) {
    super(new IdentityIdentifier(builder.principal, builder.type));
    accessToken = builder.accessToken;
    refreshToken = builder.refreshToken;
    mfaSettings = builder.mfaSettings;
    roles = builder.roles;
  }

  /**
   * 创建Identity实体构建器
   *
   * @param principal 身份主体
   * @param type      身份类型
   * @return Identity实体构建器
   */
  public static Builder newBuilder(String principal, IdentityType type) {
    return new Builder(principal, type);
  }

  public Token getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(Token accessToken) {
    this.accessToken = accessToken;
  }

  public Token getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(Token refreshToken) {
    this.refreshToken = refreshToken;
  }

  public MfaSettings getMfaSettings() {
    return mfaSettings;
  }

  public void setMfaSettings(MfaSettings mfaSettings) {
    this.mfaSettings = mfaSettings;
  }

  public Set<Long> getRoles() {
    return roles;
  }

  public void setRoles(Set<Long> roles) {
    this.roles = roles;
  }

  public static final class Builder {

    private final String principal;
    private final IdentityType type;
    private Token accessToken;
    private Token refreshToken;
    private MfaSettings mfaSettings;
    private Set<Long> roles;

    public Builder(String principal, IdentityType type) {
      this.principal = principal;
      this.type = type;
    }

    public Builder accessToken(Token val) {
      accessToken = val;
      return this;
    }

    public Builder refreshToken(Token val) {
      refreshToken = val;
      return this;
    }

    public Builder mfaSettings(MfaSettings val) {
      mfaSettings = val;
      return this;
    }

    public Builder roles(Set<Long> val) {
      roles = val;
      return this;
    }

    public Identity build() {
      return new Identity(this);
    }
  }
}

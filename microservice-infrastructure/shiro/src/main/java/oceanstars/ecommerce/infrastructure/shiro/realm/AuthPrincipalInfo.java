package oceanstars.ecommerce.infrastructure.shiro.realm;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户访问授权认证主体信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:35 上午
 */
public class AuthPrincipalInfo implements Serializable {

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 访问令牌
   */
  private String token;

  /**
   * 用户角色信息集合
   */
  private Set<String> roles;

  /**
   * 用户权限信息集合
   */
  private Set<String> permissions;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public Set<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<String> permissions) {
    this.permissions = permissions;
  }
}

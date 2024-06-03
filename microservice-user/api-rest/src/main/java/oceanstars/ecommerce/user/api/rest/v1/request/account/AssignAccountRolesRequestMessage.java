package oceanstars.ecommerce.user.api.rest.v1.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 分配账号角色接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/31 16:25
 */
@Schema(name = "AssignAccountRolesRequestMessage", description = "分配账号角色接口请求参数")
public class AssignAccountRolesRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 6411813375239784421L;

  /**
   * 账号ID
   */
  private Long account;

  /**
   * 角色ID列表
   */
  @Schema(description = "角色ID列表")
  private List<Long> roles;

  public Long getAccount() {
    return account;
  }

  public void setAccount(Long account) {
    this.account = account;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }
}

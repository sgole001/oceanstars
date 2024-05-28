package oceanstars.ecommerce.user.api.rest.v1.request.role;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.Set;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建角色接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/23 15:15
 */
@Schema(name = "CreateRoleRequestMessage", description = "创建角色接口请求参数")
public class CreateRoleRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 2362374569056217796L;

  /**
   * 角色名
   */
  @Schema(description = "角色名")
  private String name;

  /**
   * 角色说明
   */
  @Schema(description = "角色说明")
  private String desc;

  /**
   * 父角色(角色继承)列表
   */
  @Schema(description = "父角色(角色继承)列表")
  private Set<Long> parents;

  /**
   * 角色权限列表
   */
  @Schema(description = "角色权限列表")
  private Set<Long> permissions;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Set<Long> getParents() {
    return parents;
  }

  public void setParents(Set<Long> parents) {
    this.parents = parents;
  }

  public Set<Long> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Long> permissions) {
    this.permissions = permissions;
  }
}

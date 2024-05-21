package oceanstars.ecommerce.user.api.rest.v1.request.permission.data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 权限操作行为数据
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/17 18:47
 */
@Schema(name = "PermissionBehavior", description = "权限操作行为")
public class PermissionBehavior {

  /**
   * 权限操作资源ID
   */
  @Schema(description = "权限操作资源ID")
  private Long resource;

  /**
   * 权限操作行为：是否禁用
   */
  @Schema(description = "权限操作行为：是否禁用")
  private Boolean isForbidden;

  /**
   * 权限操作行为：是否允许创建
   */
  @Schema(description = "权限操作行为：是否允许创建")
  private Boolean canCreate;

  /**
   * 权限操作行为：是否允许读取
   */
  @Schema(description = "权限操作行为：是否允许读取")
  private Boolean canRead;

  /**
   * 权限操作行为：是否允许更新
   */
  @Schema(description = "权限操作行为：是否允许更新")
  private Boolean canUpdate;

  /**
   * 权限操作行为：是否允许删除
   */
  @Schema(description = "权限操作行为：是否允许删除")
  private Boolean canDelete;

  public Long getResource() {
    return resource;
  }

  public void setResource(Long resource) {
    this.resource = resource;
  }

  public Boolean getForbidden() {
    return isForbidden;
  }

  public void setForbidden(Boolean forbidden) {
    isForbidden = forbidden;
  }

  public Boolean getCanCreate() {
    return canCreate;
  }

  public void setCanCreate(Boolean canCreate) {
    this.canCreate = canCreate;
  }

  public Boolean getCanRead() {
    return canRead;
  }

  public void setCanRead(Boolean canRead) {
    this.canRead = canRead;
  }

  public Boolean getCanUpdate() {
    return canUpdate;
  }

  public void setCanUpdate(Boolean canUpdate) {
    this.canUpdate = canUpdate;
  }

  public Boolean getCanDelete() {
    return canDelete;
  }

  public void setCanDelete(Boolean canDelete) {
    this.canDelete = canDelete;
  }
}

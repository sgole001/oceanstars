package oceanstars.ecommerce.user.api.rest.v1.request.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import oceanstars.ecommerce.common.restful.RestRequestMessage;
import oceanstars.ecommerce.user.api.rest.v1.request.permission.data.PermissionBehavior;

/**
 * 创建权限接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/14 10:48 AM
 */
@Schema(name = "CreatePermissionRequestMessage", description = "创建权限接口请求参数")
public class CreatePermissionRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 3725281164528396432L;

  /**
   * 权限名
   */
  @Schema(description = "权限名")
  private String name;

  /**
   * 权限类型
   */
  @Schema(description = "权限类型（区分对应操作的资源）")
  private Short type;

  /**
   * 权限说明
   */
  @Schema(description = "权限说明")
  private String desc;

  /**
   * 权限操作行为列表
   */
  @Schema(description = "权限操作行为列表")
  private List<PermissionBehavior> behaviors;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<PermissionBehavior> getBehaviors() {
    return behaviors;
  }

  public void setBehaviors(List<PermissionBehavior> behaviors) {
    this.behaviors = behaviors;
  }
}

package oceanstars.ecommerce.ecm.api.rest.v1.request.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.List;
import oceanstars.ecommerce.common.restful.RestRequestMessage;

/**
 * 创建菜单接口请求参数
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/7 16:15
 */
@Schema(name = "CreateMenuRequestMessage", description = "创建菜单接口请求参数")
public class CreateMenuRequestMessage extends RestRequestMessage {

  @Serial
  private static final long serialVersionUID = 7294873732814105772L;

  /**
   * 菜单名称
   */
  @Schema(description = "菜单名称")
  private String name;

  /**
   * 菜单展示名称
   */
  @Schema(description = "菜单展示名称")
  private String displayName;

  /**
   * 菜单描述
   */
  @Schema(description = "菜单描述")
  private String description;

  /**
   * 菜单类型
   */
  @Schema(description = "菜单类型")
  private Integer type;

  /**
   * 菜单图标
   */
  @Schema(description = "菜单图标")
  private String icon;

  /**
   * 菜单功能
   */
  @Schema(description = "菜单功能")
  private Long func;

  /**
   * 菜单动作：点击菜单后的执行脚本
   */
  @Schema(description = "菜单动作：点击菜单后的执行脚本")
  private String action;

  /**
   * 菜单隶属
   */
  @Schema(description = "菜单隶属")
  private Long parent;

  /**
   * 菜单是否可见
   */
  @Schema(description = "菜单是否可见")
  private Boolean visible;

  /**
   * 内容标签
   */
  @Schema(description = "菜单标签")
  private List<Long> tags;

  /**
   * 内容分类
   */
  @Schema(description = "菜单分类")
  private List<Long> categories;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Long getFunc() {
    return func;
  }

  public void setFunc(Long func) {
    this.func = func;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Long getParent() {
    return parent;
  }

  public void setParent(Long parent) {
    this.parent = parent;
  }

  public Boolean getVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public List<Long> getTags() {
    return tags;
  }

  public void setTags(List<Long> tags) {
    this.tags = tags;
  }

  public List<Long> getCategories() {
    return categories;
  }

  public void setCategories(List<Long> categories) {
    this.categories = categories;
  }
}

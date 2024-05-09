package oceanstars.ecommerce.ecm.domain.content.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentMenuType;

/**
 * 内容菜单值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/12 10:45
 */
public class ContentMenuValueObject extends ValueObject {

  @Serial
  private static final long serialVersionUID = -8447105184676505000L;

  /**
   * 菜单类型
   */
  private final ContentMenuType type;

  /**
   * 菜单对应的功能ID
   */
  private final Long func;

  /**
   * 菜单动作：点击菜单后的执行脚本
   */
  private final String action;

  /**
   * 菜单图标
   */
  private final String icon;

  /**
   * 菜单隶属 - 通过内容ID关联隶属关系
   */
  private final Long parent;

  /**
   * 菜单是否可见
   */
  private final Boolean visible;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ContentMenuValueObject(Builder builder) {
    type = builder.type;
    func = builder.func;
    action = builder.action;
    icon = builder.icon;
    parent = builder.parent;
    visible = builder.visible;
  }

  /**
   * 创建内容功能菜单值对象构建器
   *
   * @return 内容功能菜单值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public ContentMenuType getType() {
    return type;
  }

  public Long getFunc() {
    return func;
  }

  public String getAction() {
    return action;
  }

  public String getIcon() {
    return icon;
  }

  public Long getParent() {
    return parent;
  }

  public Boolean getVisible() {
    return visible;
  }

  /**
   * 内容功能菜单值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/12 10:52
   */
  public static final class Builder {

    private ContentMenuType type;
    private Long func;
    private String action;
    private String icon;
    private Long parent;
    private Boolean visible;

    public Builder() {
    }

    public Builder type(ContentMenuType val) {
      type = val;
      return this;
    }

    public Builder func(Long val) {
      func = val;
      return this;
    }

    public Builder action(String val) {
      action = val;
      return this;
    }

    public Builder icon(String val) {
      icon = val;
      return this;
    }

    public Builder parent(Long val) {
      parent = val;
      return this;
    }

    public Builder visible(Boolean val) {
      visible = val;
      return this;
    }

    public ContentMenuValueObject build() {
      return new ContentMenuValueObject(this);
    }
  }
}

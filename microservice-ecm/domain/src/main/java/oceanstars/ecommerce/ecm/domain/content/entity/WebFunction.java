package oceanstars.ecommerce.ecm.domain.content.entity;

import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.WebFunctionType;

/**
 * Web功能实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/16 16:40
 */
public class WebFunction extends Entity<WebFunctionIdentifier> {

  /**
   * Web功能类型
   */
  private WebFunctionType type;

  /**
   * 功能跳转
   */
  private String href;

  /**
   * 功能图标
   */
  private String icon;

  /**
   * Web功能隶属 - 通过内容ID关联隶属关系
   */
  private Long parent;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private WebFunction(Builder builder) {
    super(new WebFunctionIdentifier(builder.contentId));
    type = builder.type;
    href = builder.href;
    icon = builder.icon;
    parent = builder.parent;
  }

  /**
   * 创建Web功能实体构建器
   *
   * @param contentId 内容ID
   * @return Web功能实体构建器
   */
  public static Builder newBuilder(Long contentId) {
    return new Builder(contentId);
  }

  public WebFunctionType getType() {
    return type;
  }

  public void setType(WebFunctionType type) {
    this.type = type;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Long getParent() {
    return parent;
  }

  public void setParent(Long parent) {
    this.parent = parent;
  }

  /**
   * Web功能实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/17 14:58
   */
  public static final class Builder {

    private final Long contentId;
    private WebFunctionType type;
    private String href;
    private String icon;
    private Long parent;

    public Builder(Long contentId) {
      this.contentId = contentId;
    }

    public Builder type(WebFunctionType val) {
      type = val;
      return this;
    }

    public Builder href(String val) {
      href = val;
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

    public WebFunction build() {
      return new WebFunction(this);
    }
  }
}

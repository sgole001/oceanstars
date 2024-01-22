package oceanstars.ecommerce.ecm.domain.content.entity;

import oceanstars.ecommerce.common.domain.Entity;

/**
 * 内容标签实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:07
 */
public class ContentTag extends Entity<ContentTagIdentifier> {

  /**
   * 内容标签名称
   */
  private final String name;

  /**
   * 内容标签展示名称
   */
  private String displayName;

  /**
   * 内容标签描述
   */
  private String description;

  /**
   * 内容标签图标
   */
  private String icon;

  /**
   * 内容标签链接
   */
  private String url;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 内容标签构建器
   */
  private ContentTag(Builder builder) {
    super(new ContentTagIdentifier(builder.name));
    name = builder.name;
    displayName = builder.displayName;
    description = builder.description;
    icon = builder.icon;
    url = builder.url;
  }

  /**
   * 内容标签构建器
   *
   * @param name 内容标签名称
   * @return 内容标签构建器
   */
  public static Builder newBuilder(String name) {
    return new Builder(name);
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public String getIcon() {
    return icon;
  }

  public String getUrl() {
    return url;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * 内容标签构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/11 16:22
   */
  public static final class Builder {

    private final String name;
    private String displayName;
    private String description;
    private String icon;
    private String url;

    public Builder(String name) {
      this.name = name;
    }

    public Builder displayName(String val) {
      displayName = val;
      return this;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder icon(String val) {
      icon = val;
      return this;
    }

    public Builder url(String val) {
      url = val;
      return this;
    }

    public ContentTag build() {
      return new ContentTag(this);
    }
  }
}

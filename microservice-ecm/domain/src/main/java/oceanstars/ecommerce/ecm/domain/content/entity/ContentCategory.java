package oceanstars.ecommerce.ecm.domain.content.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.Entity;

/**
 * 内容分类实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:27
 */
public class ContentCategory extends Entity<ContentCategoryIdentifier> {

  /**
   * 内容分类名称
   */
  private final String name;

  /**
   * 内容分类展示名称
   */
  private String displayName;

  /**
   * 内容分类描述
   */
  private String description;

  /**
   * 内容分类链接
   */
  private String url;

  /**
   * 父级内容分类
   */
  private List<ContentCategory> parents;

  /**
   * 子级内容分类
   */
  private List<ContentCategory> children;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 内容分类实体构造器
   */
  private ContentCategory(Builder builder) {
    super(new ContentCategoryIdentifier(builder.name));
    name = builder.name;
    displayName = builder.displayName;
    description = builder.description;
    url = builder.url;
    parents = builder.parents;
    children = builder.children;
  }

  /**
   * 创建内容分类实体构造器
   *
   * @param name 内容分类名称
   * @return 内容分类实体构造器
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

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<ContentCategory> getParents() {
    return parents;
  }

  public void setParents(List<ContentCategory> parents) {
    this.parents = parents;
  }

  public List<ContentCategory> getChildren() {
    return children;
  }

  public void setChildren(List<ContentCategory> children) {
    this.children = children;
  }

  /**
   * 内容分类实体构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/14 13:13
   */
  public static final class Builder {

    private final String name;
    private String displayName;
    private String description;
    private String url;
    private List<ContentCategory> parents;
    private List<ContentCategory> children;

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

    public Builder url(String val) {
      url = val;
      return this;
    }

    public Builder parents(List<ContentCategory> val) {
      parents = val;
      return this;
    }

    public Builder children(List<ContentCategory> val) {
      children = val;
      return this;
    }

    public ContentCategory build() {
      return new ContentCategory(this);
    }
  }
}

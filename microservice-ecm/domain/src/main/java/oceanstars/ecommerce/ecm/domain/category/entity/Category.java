package oceanstars.ecommerce.ecm.domain.category.entity;

import java.util.Set;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;

/**
 * 分类实体
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 16:27
 */
public class Category extends AggregateRoot<CategoryIdentifier> {

  /**
   * 分类展示名称
   */
  private String displayName;

  /**
   * 分类描述
   */
  private String description;

  /**
   * 分类链接
   */
  private String url;

  /**
   * 父级分类
   */
  private Set<Long> parents;

  /**
   * 审核流程状态
   */
  private AuditProcessStatus status;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 分类实体构造器
   */
  private Category(Builder builder) {
    super(new CategoryIdentifier(builder.name));
    displayName = builder.displayName;
    description = builder.description;
    url = builder.url;
    parents = builder.parents;
    status = builder.status;
  }

  /**
   * 创建分类实体构造器
   *
   * @param name 分类名称
   * @return 分类实体构造器
   */
  public static Builder newBuilder(String name) {
    return new Builder(name);
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

  public Set<Long> getParents() {
    return parents;
  }

  public void setParents(Set<Long> parents) {
    this.parents = parents;
  }

  public AuditProcessStatus getStatus() {
    return status;
  }

  public void setStatus(AuditProcessStatus status) {
    this.status = status;
  }

  /**
   * 分类实体构造器
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
    private Set<Long> parents;
    private AuditProcessStatus status;

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

    public Builder parents(Set<Long> val) {
      parents = val;
      return this;
    }

    public Builder status(AuditProcessStatus val) {
      status = val;
      return this;
    }

    public Category build() {
      return new Category(this);
    }
  }
}

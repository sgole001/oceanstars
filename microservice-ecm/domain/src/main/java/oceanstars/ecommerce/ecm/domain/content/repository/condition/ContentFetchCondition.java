package oceanstars.ecommerce.ecm.domain.content.repository.condition;

import java.util.Set;
import oceanstars.ecommerce.common.domain.repository.condition.BaseCondition;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;

/**
 * 内容查询条件
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/8 10:13
 */
public class ContentFetchCondition extends BaseCondition {

  /**
   * 内容名称
   */
  private final String name;

  /**
   * 内容类型
   */
  private final ContentType type;

  /**
   * 内容展示名称
   */
  private final String displayName;

  /**
   * 内容状态
   */
  private final AuditProcessStatus status;

  /**
   * 内容标签
   */
  private final Set<Long> tags;

  /**
   * 内容分类
   */
  private final Set<Long> categories;

  /**
   * 构造函数
   *
   * @param builder 构造器
   */
  protected ContentFetchCondition(Builder builder) {
    super(builder);
    this.name = builder.name;
    this.type = builder.type;
    this.displayName = builder.displayName;
    this.status = builder.status;
    this.tags = builder.tags;
    this.categories = builder.categories;
  }

  /**
   * 创建构建器
   *
   * @return 构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public ContentType getType() {
    return type;
  }

  public String getDisplayName() {
    return displayName;
  }

  public AuditProcessStatus getStatus() {
    return status;
  }

  public Set<Long> getTags() {
    return tags;
  }

  public Set<Long> getCategories() {
    return categories;
  }

  /**
   * 构造器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/8 10:18
   */
  public static class Builder extends BaseCondition.Builder<ContentFetchCondition, ContentFetchCondition.Builder> {

    private String name;
    private ContentType type;
    private String displayName;
    private AuditProcessStatus status;
    private Set<Long> tags;
    private Set<Long> categories;

    public Builder() {
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder type(ContentType val) {
      type = val;
      return this;
    }

    public Builder displayName(String val) {
      displayName = val;
      return this;
    }

    public Builder status(AuditProcessStatus val) {
      status = val;
      return this;
    }

    public Builder tags(Set<Long> val) {
      tags = val;
      return this;
    }

    public Builder categories(Set<Long> val) {
      categories = val;
      return this;
    }
  }
}

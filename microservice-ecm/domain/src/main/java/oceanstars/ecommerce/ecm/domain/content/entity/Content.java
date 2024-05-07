package oceanstars.ecommerce.ecm.domain.content.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.entity.AggregateRoot;
import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.AuditProcessStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentStatisticsValueObject;

/**
 * 内容聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 13:56
 */
public class Content extends AggregateRoot<ContentIdentifier> {

  /**
   * 内容展示名称
   */
  private String displayName;

  /**
   * 内容描述
   */
  private String description;

  /**
   * 内容标签
   */
  private List<Long> tags;

  /**
   * 内容分类
   */
  private List<Long> categories;

  /**
   * 内容统计值对象
   */
  private ContentStatisticsValueObject statistics;

  /**
   * 审核流程状态
   */
  private AuditProcessStatus status;

  /**
   * 内容原始信息
   */
  private ValueObject raw;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 内容构建器
   */
  private Content(Builder builder) {
    super(new ContentIdentifier(builder.type, builder.name));
    this.displayName = builder.displayName;
    this.description = builder.description;
    this.tags = builder.tags;
    this.categories = builder.categories;
    this.statistics = builder.statistics;
    this.status = builder.status;
    this.raw = builder.raw;
  }

  /**
   * 创建内容构建器
   *
   * @param type 内容类型
   * @param name 内容名称
   * @return 内容构建器
   */
  public static Builder newBuilder(ContentType type, String name) {
    return new Builder(type, name);
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

  public ContentStatisticsValueObject getStatistics() {
    return statistics;
  }

  public void setStatistics(ContentStatisticsValueObject statistics) {
    this.statistics = statistics;
  }

  public AuditProcessStatus getStatus() {
    return status;
  }

  public void setStatus(AuditProcessStatus status) {
    this.status = status;
  }

  public ValueObject getRaw() {
    return raw;
  }

  public void setRaw(ValueObject raw) {
    this.raw = raw;
  }

  /**
   * 内容实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/1/15 12:06
   */
  public static final class Builder {

    private final ContentType type;
    private final String name;

    private String displayName;
    private String description;
    private List<Long> tags;
    private List<Long> categories;
    private ContentStatisticsValueObject statistics;
    private AuditProcessStatus status;
    private ValueObject raw;

    public Builder(ContentType type, String name) {
      this.type = type;
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

    public Builder tags(List<Long> val) {
      tags = val;
      return this;
    }

    public Builder categories(List<Long> val) {
      categories = val;
      return this;
    }

    public Builder statistics(ContentStatisticsValueObject val) {
      statistics = val;
      return this;
    }

    public Builder status(AuditProcessStatus val) {
      status = val;
      return this;
    }

    public Builder raw(ValueObject val) {
      raw = val;
      return this;
    }

    public Content build() {
      return new Content(this);
    }
  }
}

package oceanstars.ecommerce.ecm.domain.content.entity;

import java.util.List;
import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.common.domain.Entity;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentStatus;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.domain.content.entity.valueobject.ContentStatisticsValueObject;
import oceanstars.ecommerce.ecm.repository.content.IContentRepository;

/**
 * 内容聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/11 13:56
 */
public class ContentEntity extends AggregateRoot<ContentIdentifier> {

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
  private String displayName;

  /**
   * 内容描述
   */
  private String description;

  /**
   * 内容标签
   */
  private List<ContentTag> tags;

  /**
   * 内容分类
   */
  private List<ContentCategory> categories;

  /**
   * 内容统计值对象
   */
  private ContentStatisticsValueObject statistics;

  /**
   * 内容状态
   */
  private ContentStatus status;

  /**
   * 原生内容实体
   */
  private Entity<?> raw;

  /**
   * 内容仓储
   */
  private final IContentRepository contentRepository;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 内容构建器
   */
  private ContentEntity(Builder builder) {
    super(new ContentIdentifier(builder.type, builder.name));
    name = builder.name;
    type = builder.type;
    displayName = builder.displayName;
    description = builder.description;
    tags = builder.tags;
    categories = builder.categories;
    statistics = builder.statistics;
    status = builder.status;
    raw = builder.raw;
    contentRepository = ApplicationContextProvider.getApplicationContext().getBean(IContentRepository.class);
  }

  /**
   * 创建内容构建器
   *
   * @param name 内容名称
   * @param type 内容类型
   * @return 内容构建器
   */
  public static Builder newBuilder(String name, ContentType type) {
    return new Builder(name, type);
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

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<ContentTag> getTags() {
    return tags;
  }

  public void setTags(List<ContentTag> tags) {
    this.tags = tags;
  }

  public List<ContentCategory> getCategories() {
    return categories;
  }

  public void setCategories(List<ContentCategory> categories) {
    this.categories = categories;
  }

  public ContentStatisticsValueObject getStatistics() {
    return statistics;
  }

  public void setStatistics(ContentStatisticsValueObject statistics) {
    this.statistics = statistics;
  }

  public ContentStatus getStatus() {
    return status;
  }

  public void setStatus(ContentStatus status) {
    this.status = status;
  }

  public Entity<?> getRaw() {
    return raw;
  }

  public void setRaw(Entity<?> raw) {
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

    private final String name;
    private final ContentType type;
    private String displayName;
    private String description;
    private List<ContentTag> tags;
    private List<ContentCategory> categories;
    private ContentStatisticsValueObject statistics;
    private ContentStatus status;
    private Entity<?> raw;

    public Builder(String name, ContentType type) {
      this.name = name;
      this.type = type;
    }

    public Builder displayName(String val) {
      displayName = val;
      return this;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder tags(List<ContentTag> val) {
      tags = val;
      return this;
    }

    public Builder categories(List<ContentCategory> val) {
      categories = val;
      return this;
    }

    public Builder statistics(ContentStatisticsValueObject val) {
      statistics = val;
      return this;
    }

    public Builder status(ContentStatus val) {
      status = val;
      return this;
    }

    public Builder raw(Entity<?> val) {
      raw = val;
      return this;
    }

    public ContentEntity build() {
      return new ContentEntity(this);
    }
  }
}

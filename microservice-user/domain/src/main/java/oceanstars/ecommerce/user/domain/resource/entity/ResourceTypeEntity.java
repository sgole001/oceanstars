package oceanstars.ecommerce.user.domain.resource.entity;

import oceanstars.ecommerce.common.domain.AggregateRoot;
import oceanstars.ecommerce.user.domain.resource.entity.valueobject.ResourceLinkValueObject;

/**
 * 资源实体：聚合根
 *
 * @author Clover
 * @version 1.0.0
 * @since 2022/1/6 3:51 PM
 */
public class ResourceTypeEntity extends AggregateRoot<ResourceTypeIdentifier> {

  /**
   * 资源类型名
   */
  private final String name;

  /**
   * 资源类型功能说明
   */
  private String desc;

  /**
   * 操作逻辑有效标志位
   */
  private Boolean enabled;

  /**
   * 资源数据链接
   */
  private ResourceLinkValueObject link;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private ResourceTypeEntity(Builder builder) {
    super(new ResourceTypeIdentifier(builder.name));
    name = builder.name;
    desc = builder.desc;
    enabled = builder.enabled;
    link = builder.link;
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public ResourceLinkValueObject getLink() {
    return link;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setLink(ResourceLinkValueObject link) {
    this.link = link;
  }

  /**
   * 创建资源实体构建器
   *
   * @param name 资源类型名
   * @return 资源实体构建器
   */
  public static Builder newBuilder(final String name) {
    return new Builder(name);
  }

  /**
   * 资源实体构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2022/1/6 4:26 PM
   */
  public static final class Builder {

    private final String name;
    private String desc;
    private Boolean enabled;
    private ResourceLinkValueObject link;

    public Builder(String name) {
      this.name = name;
    }

    public Builder desc(String val) {
      desc = val;
      return this;
    }

    public Builder enabled(Boolean val) {
      enabled = val;
      return this;
    }

    public Builder link(ResourceLinkValueObject val) {
      link = val;
      return this;
    }

    public ResourceTypeEntity build() {
      return new ResourceTypeEntity(this);
    }
  }
}

package oceanstars.ecommerce.ecm.domain.asset.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 资产 - 知识产权 : 功能列表值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/4/6 13:32
 */
public class FunctionValueObject extends ValueObject {

  @Serial
  private static final long serialVersionUID = 917435651447223805L;

  /**
   * 功能隶属
   */
  private final Long parent;

  /**
   * 构造函数：初始化成员变量
   *
   * @param builder 构建器
   */
  private FunctionValueObject(Builder builder) {
    super();
    parent = builder.parent;
  }

  /**
   * 创建功能列表值对象构建器
   *
   * @return 功能列表值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Long getParent() {
    return parent;
  }

  /**
   * 创建功能列表值对象构建器
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/4/6 13:58
   */
  public static final class Builder {

    private Long parent;

    public Builder() {
    }

    public Builder parent(Long val) {
      parent = val;
      return this;
    }

    public FunctionValueObject build() {
      return new FunctionValueObject(this);
    }
  }
}

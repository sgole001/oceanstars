package oceanstars.ecommerce.common.domain.entity;

import io.protostuff.Exclude;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import oceanstars.ecommerce.common.tools.ReflectUtil;

/**
 * 领域模型值对象基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/2 2:59 下午
 */
public class ValueObject implements Serializable {

  @Serial
  private static final long serialVersionUID = -5962345425833999918L;

  /**
   * 领域值对象委托者
   */
  @Exclude
  private ValueObjectDelegator delegator;

  /**
   * 获取委托者值对象
   *
   * @return 委托者值对象
   */
  public ValueObjectDelegator getDelegator() {
    return delegator;
  }

  /**
   * 委托值对象
   *
   * @param tablePojo 委托者信息
   */
  public void delegate(Object tablePojo) {

    if (tablePojo instanceof ValueObjectDelegator valueObjectDelegator) {
      this.delegator = valueObjectDelegator;
    } else {
      // 反射持久层数据对象，获取委托者数据
      final Long id = (Long) ReflectUtil.getFieldValue(tablePojo, "id");
      final String createBy = (String) ReflectUtil.getFieldValue(tablePojo, "createBy");
      final LocalDateTime createAt = (LocalDateTime) ReflectUtil.getFieldValue(tablePojo, "createAt");

      this.delegator = ValueObjectDelegator.newBuilder(id, createAt, createBy).build();
    }
  }
}

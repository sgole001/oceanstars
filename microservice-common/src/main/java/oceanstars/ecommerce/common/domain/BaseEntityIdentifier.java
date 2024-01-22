package oceanstars.ecommerce.common.domain;

import java.io.Serial;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import oceanstars.ecommerce.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * 实体唯一标识符值对象基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/15 2:18 下午
 */
public abstract class BaseEntityIdentifier<T> extends ValueObject implements IEntityIdentifier<T> {

  @Serial
  private static final long serialVersionUID = -292957966844673891L;
  /**
   * 实体唯一识别符（自然键）
   */
  private T id;

  public BaseEntityIdentifier(T identifier) {
    this.id = identifier;
  }

  @Override
  public T getIdentifier() {
    return this.id;
  }

  /**
   * 根据指定字符串获取UUID各部分数组
   *
   * @param name UUID字符串对象
   * @return UUID各部分数组
   */
  protected String[] uuid(String name) {

    // 根据指定字符串获取UUID
    final String uuid = UUID.nameUUIDFromBytes(name.getBytes()).toString().toUpperCase(Locale.ROOT);

    return uuid.split(CommonConstant.SEPARATOR_HYPHEN);
  }

  /**
   * 获取实体唯一识别符（自然键）
   *
   * @return 实体唯一识别符（自然键）
   */
  @Override
  public String toString() {

    if (null == this.id || (this.id instanceof String && StringUtils.isBlank((String) this.id))) {
      this.id = this.generateIdentifier();
    }

    return (String) this.id;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    // 如果比较对象类型不同
    if (this.getClass() != obj.getClass()) {
      return false;
    }

    return this.toString().equals(obj.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getClass() + "#" + this);
  }
}

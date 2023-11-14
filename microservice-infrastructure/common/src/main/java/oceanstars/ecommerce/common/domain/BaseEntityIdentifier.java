package oceanstars.ecommerce.common.domain;

import java.util.Locale;
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
public abstract class BaseEntityIdentifier extends ValueObject implements IEntityIdentifier {

  /**
   * 实体唯一识别符（自然键）
   */
  private String id;

  /**
   * 获取实体唯一识别符（自然键）
   *
   * @return 实体唯一识别符（自然键）
   */
  @Override
  public String toString() {

    if (StringUtils.isBlank(this.id)) {
      this.id = this.generateIdentifier();
    }

    return this.id;
  }

  @Override
  public String generateIdentifier() {
    // 默认实体唯一识别符
    return UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
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
}

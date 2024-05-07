package oceanstars.ecommerce.common.domain;

import oceanstars.ecommerce.common.constant.IEnum;

/**
 * 领域枚举
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/12/15 4:37 下午
 */
public class DomainEnum {

  /**
   * 领域对象查询：字段条件类型
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/3 15:03
   */
  public enum FieldConditionType implements IEnum<Integer, String, FieldConditionType> {

    /**
     * 等于
     */
    EQ(0, "equal"),

    /**
     * 不等于
     */
    NE(1, "not equal"),

    /**
     * 大于
     */
    GT(2, "greater than"),

    /**
     * 大于等于
     */
    GE(3, "greater than or equal to"),

    /**
     * 小于
     */
    LT(4, "less than"),

    /**
     * 小于等于
     */
    LE(5, "less than or equal to"),

    /**
     * 包含(in)
     */
    IN(6, "in"),

    /**
     * 不包含(not in
     */
    N_IN(7, "not in"),

    /**
     * 包含(like)
     */
    LIKE(8, "like"),

    /**
     * 不包含(not like)
     */
    N_LIKE(9, "not like"),

    /**
     * 包含(is null)
     */
    IS_NULL(10, "is null"),

    /**
     * 不包含(is not null)
     */
    IS_NOTNULL(11, "is not null"),

    /**
     * 包含(between)
     */
    BETWEEN(12, "between"),

    /**
     * 不包含(not between)
     */
    N_BETWEEN(13, "not between");

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    FieldConditionType(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param code 枚举项KEY
     * @return 枚举对象
     */
    public static FieldConditionType of(Integer code) {
      for (FieldConditionType value : FieldConditionType.values()) {
        if (code.equals(value.key())) {
          return value;
        }
      }
      return null;
    }

    @Override
    public FieldConditionType get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }

  /**
   * 领域对象查询：条件关系
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/5/3 15:17
   */
  public enum ConditionRelation implements IEnum<Integer, String, ConditionRelation> {

    /**
     * 与
     */
    AND(0, "and"),

    /**
     * 或
     */
    OR(1, "or"),

    /**
     * 异或
     */
    XOR(2, "xor"),

    /**
     * 非
     */
    NOT(3, "not"),

    /**
     * 与非
     */
    AND_NOT(4, "and not"),

    /**
     * 或非
     */
    OR_NOT(5, "or not"),
    ;

    /**
     * 枚举编号
     */
    private final Integer code;

    /**
     * 枚举显示名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param code 枚举编号
     * @param name 枚举显示名
     */
    ConditionRelation(Integer code, String name) {
      this.code = code;
      this.name = name;
    }

    /**
     * 根据枚举KEY获取枚举对象
     *
     * @param code 枚举项KEY
     * @return 枚举对象
     */
    public static ConditionRelation of(Integer code) {
      for (ConditionRelation value : ConditionRelation.values()) {
        if (code.equals(value.key())) {
          return value;
        }
      }
      return null;
    }

    @Override
    public ConditionRelation get() {
      return this;
    }

    @Override
    public Integer key() {
      return this.code;
    }

    @Override
    public String value() {
      return this.name;
    }
  }
}

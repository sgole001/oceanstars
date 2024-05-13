package oceanstars.ecommerce.common.domain.repository.condition;

/**
 * 领域查询条件封装类，区分NULL场景下查询条件是否需要IS NULL判断
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/11 14:56
 */
public class NullCondition<T> {

  /**
   * 查询条件值
   */
  private final T value;

  /**
   * 查询条件值为NULL的情况下，是否需要IS NULL判断
   */
  private final Boolean checkNull;

  /**
   * 构造函数
   *
   * @param value 查询条件值
   */
  public NullCondition(T value) {
    this.value = value;
    this.checkNull = Boolean.FALSE;
  }

  /**
   * 构造函数
   *
   * @param checkNull 查询条件值为NULL的情况下，是否需要IS NULL判断
   */
  public NullCondition(Boolean checkNull) {
    this.value = null;
    this.checkNull = checkNull;
  }

  public T getValue() {
    return value;
  }

  public Boolean checkNull() {
    return checkNull;
  }
}

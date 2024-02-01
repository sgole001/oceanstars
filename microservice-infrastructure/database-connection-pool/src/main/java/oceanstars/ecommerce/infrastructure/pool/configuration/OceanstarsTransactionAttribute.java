package oceanstars.ecommerce.infrastructure.pool.configuration;

import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;

/**
 * 扩展Spring事务属性，支持多数据源事务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/30 15:55
 */
public class OceanstarsTransactionAttribute extends RuleBasedTransactionAttribute {

  /**
   * 数据源名称
   */
  private String datasource;

  public OceanstarsTransactionAttribute() {
  }

  public OceanstarsTransactionAttribute(OceanstarsTransactionAttribute other) {
    super(other);
    this.datasource = other.datasource;
  }

  public String getDataSource() {
    return datasource;
  }

  public void setDataSource(String datasource) {
    this.datasource = datasource;
  }

  @Override
  public String toString() {

    // 获取事务属性描述
    StringBuilder result = this.getAttributeDescription();

    // 重写toString方法，将数据源名称也打印出来
    if (this.datasource != null) {
      result.append(',').append(result);
    }

    return result.toString();
  }
}

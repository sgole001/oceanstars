package oceanstars.ecommerce.infrastructure.pool.configuration;

import java.io.Serial;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import javax.sql.DataSource;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;

/**
 * 扩展Spring事务拦截器，支持多数据源事务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/30 15:28
 */
public class OceanstarsTransactionInterceptor extends TransactionInterceptor {

  @Serial
  private static final long serialVersionUID = 7420333048837372097L;
  private final ConcurrentMap<DataSource, TransactionManager> jooqTransactionManagerCache = new ConcurrentReferenceHashMap<>(4);

  @Override
  protected TransactionManager determineTransactionManager(@Nullable TransactionAttribute txAttr) {

    // 获取数据源
    final DataSource dataSource = this.determineDataSource(txAttr);

    if (null == dataSource) {
      return super.determineTransactionManager(txAttr);
    }

    return this.determineDataSourcedTransactionManager(dataSource);
  }

  @Override
  protected void clearTransactionManagerCache() {
    super.clearTransactionManagerCache();
    this.jooqTransactionManagerCache.clear();
  }

  /**
   * 确定数据源
   *
   * @param txAttr 事务属性
   * @return 数据源
   */
  private DataSource determineDataSource(final TransactionAttribute txAttr) {

    // 创建确认数据源对象
    DataSource targetDataSource;

    // 获取数据源配置信息
    final OceanstarsDataSource dataSources = ApplicationContextProvider.getBean("dataSources", OceanstarsDataSource.class);

    // 单数据源的情况下，直接返回数据源
    targetDataSource = dataSources.getSource();
    if (null != targetDataSource) {
      return targetDataSource;
    }

    // 获取注解上的数据源名称
    String dataSource = ((OceanstarsTransactionAttribute) txAttr).getDataSource();
    if (!StringUtils.hasText(dataSource)) {
      // 如果注解上的数据源名称为空，则使用默认数据源
      dataSource = OceanstarsDataSource.DEFAULT_DATASOURCE_NAME;
    }
    // 获取多数据源集合映射
    final Map<String, DataSource> sources = dataSources.getSources();
    // 多数据源的情况下，根据注解上的数据源名称获取数据源
    targetDataSource = sources.get(dataSource);

    return targetDataSource;
  }

  /**
   * 确定数据源事务管理器
   *
   * @param dataSource 数据源
   * @return 数据源事务管理器
   */
  private TransactionManager determineDataSourcedTransactionManager(DataSource dataSource) {

    // 缓存中获取事务管理器
    TransactionManager txManager = this.jooqTransactionManagerCache.get(dataSource);
    // 如果缓存中没有事务管理器，则创建一个事务管理器
    if (txManager == null) {
      // 创建事务管理器
      txManager = new DataSourceTransactionManager(dataSource);
      // 将事务管理器放入缓存中
      this.jooqTransactionManagerCache.putIfAbsent(dataSource, txManager);
    }

    return txManager;
  }
}

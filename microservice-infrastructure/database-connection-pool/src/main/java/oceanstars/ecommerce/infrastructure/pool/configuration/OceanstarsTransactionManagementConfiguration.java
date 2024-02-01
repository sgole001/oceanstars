package oceanstars.ecommerce.infrastructure.pool.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 扩展Spring事务管理配置，支持多数据源事务
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/30 12:10
 */
@AutoConfiguration
public class OceanstarsTransactionManagementConfiguration extends ProxyTransactionManagementConfiguration {

  @Override
  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public TransactionAttributeSource transactionAttributeSource() {
    return new AnnotationTransactionAttributeSource(new OceanstarsTransactionAnnotationParser());
  }

  @Override
  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  public TransactionInterceptor transactionInterceptor(TransactionAttributeSource transactionAttributeSource) {
    // 创建事务拦截器
    OceanstarsTransactionInterceptor interceptor = new OceanstarsTransactionInterceptor();
    // 设置事务属性源
    interceptor.setTransactionAttributeSource(transactionAttributeSource);
    // 设置事务管理器
    if (this.txManager != null) {
      interceptor.setTransactionManager(this.txManager);
    }

    return interceptor;
  }
}

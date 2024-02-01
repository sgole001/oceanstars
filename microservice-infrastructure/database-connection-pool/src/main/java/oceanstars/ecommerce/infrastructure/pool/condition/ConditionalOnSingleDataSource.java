package oceanstars.ecommerce.infrastructure.pool.condition;

import javax.sql.DataSource;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsDataSource;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 条件注解：当存在单数据源时，才会加载该配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/31 12:17
 */
public class ConditionalOnSingleDataSource implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

    // 从Spring上下文中获取Bean工厂
    final ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    // 判断Bean工厂是否存在
    if (null == beanFactory) {
      return false;
    }

    // 从Spring上下文中获取数据源对象
    final OceanstarsDataSource dataSources = beanFactory.getBean("dataSources", OceanstarsDataSource.class);
    // 获取单数据源
    final DataSource source = dataSources.getSource();

    return null != source;
  }
}

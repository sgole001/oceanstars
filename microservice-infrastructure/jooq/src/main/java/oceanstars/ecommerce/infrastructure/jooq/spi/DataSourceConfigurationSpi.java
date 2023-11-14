package oceanstars.ecommerce.infrastructure.jooq.spi;

import java.util.Map;
import org.jooq.Configuration;
import org.jooq.impl.DefaultDSLContext;

/**
 * 获取数据源配置SPI
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/1/27 15:06
 */
public interface DataSourceConfigurationSpi {

  /**
   * 根据Jooq配置报名获取数据源配置信息
   *
   * @param pkg Jooq代码生成报名配置
   * @param dsl 多数据源DSL上下文信息映射
   * @return 数据源配置信息
   */
  Configuration getConfiguration(final String pkg, final Map<String, DefaultDSLContext> dsl);
}

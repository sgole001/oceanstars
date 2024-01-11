package oceanstars.ecommerce.infrastructure.jooq.configuration;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import oceanstars.ecommerce.infrastructure.jooq.listener.AuditProducer;
import oceanstars.ecommerce.infrastructure.jooq.listener.ExceptionTranslator;
import oceanstars.ecommerce.infrastructure.jooq.listener.PkProducer;
import oceanstars.ecommerce.infrastructure.jooq.page.AdvancedHandlerMethodArgumentResolver;
import oceanstars.ecommerce.infrastructure.jooq.provider.JsonConverterProvider;
import oceanstars.ecommerce.infrastructure.jooq.spi.DataSourceConfigurationSpi;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanstarsDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Jooq配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 2:51 下午
 */
@Configuration(proxyBeanMethods = false)
public class JooqConfig extends SpringDataWebConfiguration {

  /**
   * 多数据源配置信息
   */
  private final OceanstarsDataSource dataSources;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(JooqConfig.class.getName());

  public JooqConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService,
      ObjectProvider<OceanstarsDataSource> dataSources) {
    super(context, conversionService);
    logger.info("Jooq Config...");
    this.dataSources = dataSources.getIfUnique();
  }

  /**
   * 新增参数解析处理
   *
   * @param argumentResolvers 参数解析处理列表
   */
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    // 新增分页参数请求自定义处理
    AdvancedHandlerMethodArgumentResolver resolver = new AdvancedHandlerMethodArgumentResolver();
    argumentResolvers.add(resolver);
    super.addArgumentResolvers(argumentResolvers);
  }

  /**
   * 配置单数据源数据库操作服务上下文
   *
   * @return 单数据源数据库操作服务上下文
   */
  @Bean
  public DefaultDSLContext dsl() {

    // 获取单数据源信息
    final DataSource dataSource = this.dataSources.getSource();

    if (null != dataSource) {

      logger.info("Single Jooq DSL Init Start...");

      final DefaultDSLContext dsl = new DefaultDSLContext(configuration(dataSource));

      logger.info("Single Jooq DSL Init Completed...");

      return dsl;
    }

    return null;
  }

  /**
   * 配置多数据源数据库操作服务上下文
   *
   * @return 多数据源数据库操作服务上下文
   */
  @Bean
  public Map<String, DefaultDSLContext> multiDsl() {

    if (null != this.dataSources) {

      logger.info("Multi Jooq DSL Init Start...");

      final Map<String, DefaultDSLContext> dslContexts = HashMap.newHashMap(this.dataSources.getSources().size());

      // 遍历数据源信息，初始化构建数据库链接上下文对象
      for (Entry<String, ? extends DataSource> dataSource : dataSources.getSources().entrySet()) {
        // 构建数据库链接上下文对象
        dslContexts.put(dataSource.getKey(), new DefaultDSLContext(this.configuration(dataSource.getValue())));
      }

      logger.info("Multi Jooq DSL Init Completed...");

      return dslContexts;
    }

    return Collections.emptyMap();
  }

  @Bean
  @Primary
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public DefaultConfiguration configuration(final ObjectProvider<DefaultDSLContext> dslObjectProvider,
      ObjectProvider<Map<String, DefaultDSLContext>> multiDslObjectProvider, InjectionPoint injectionPoint) {

    // 单数据源的场合下
    final DefaultDSLContext dsl = dslObjectProvider.getIfAvailable();
    if (null != dsl) {
      return (DefaultConfiguration) dsl.configuration();
    }

    // 多数据源的场合下
    final Map<String, DefaultDSLContext> multiDsl = multiDslObjectProvider.getIfAvailable();
    if (null != multiDsl) {

      // 根据Configuration自动注入点获取对应元素
      final AnnotatedElement annotatedElement = injectionPoint.getAnnotatedElement();

      // 构造函数为自动注入点
      if (Constructor.class.isAssignableFrom(annotatedElement.getClass())) {

        // 获取构造函数所在类
        final Class<?> declaringClass = ((Constructor<?>) annotatedElement).getDeclaringClass();
        // 获取类包名
        final String packageName = declaringClass.getPackage().getName();

        // 获取数据源配置SPI接口信息
        final List<DataSourceConfigurationSpi> configurationSpiList = SpringFactoriesLoader.loadFactories(DataSourceConfigurationSpi.class,
            Thread.currentThread().getContextClassLoader());

        return (DefaultConfiguration) configurationSpiList.get(0).getConfiguration(packageName, multiDsl);
      }
    }

    throw new NoSuchBeanDefinitionException("No Configuration for Jooq.");
  }

  /**
   * Jooq配置内容设定
   *
   * @return Jooq配置内容
   */
  private DefaultConfiguration configuration(final DataSource dataSource) {

    final DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

    // 数据库连接配置
    jooqConfiguration.set(connectionProvider(dataSource));
    // 数据库方言
    jooqConfiguration.set(SQLDialect.MYSQL);
    // 数据库操作异常监听配置
    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
    /*
     * 激活乐观锁（数据库包含字段：version INTEGER DEFAULT 0）
     *  Jooq生成器插件配置
     * <configuration xmlns="http://www.jooq.org/xsd/jooq-codegen-3.17.0.xsd">
     *   <generator>
     *     <database>
     *       <recordVersionFields>version</recordVersionFields>
     *     </database>
     *   </generator>
     * </configuration>
     */
    jooqConfiguration.set(new Settings().withExecuteWithOptimisticLocking(true));
    // 数据集监听配置
    jooqConfiguration.set(
        // 监听数据插入操作，自动注入PK值
        new PkProducer(),
        // 监听数据插入更新操作，自动注入审计字段
        new AuditProducer());
    // 自定义数据类型转换SPI
    jooqConfiguration.set(new JsonConverterProvider());

    return jooqConfiguration;
  }

  /**
   * 配置数据库连接提供子
   *
   * @return 数据库连接提供子
   */
  private DataSourceConnectionProvider connectionProvider(final DataSource dataSource) {
    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  }

  /**
   * 数据库操作异常监听配置
   *
   * @return 数据库操作异常监听器
   */
  private ExceptionTranslator exceptionTransformer() {
    return new ExceptionTranslator();
  }

//  /***
//   * 配置事务管理器
//   */
//  @Bean(name = "transactionManager")
//  public DataSourceTransactionManager transactionManager() {
//    return new DataSourceTransactionManager(this.connectionProvider().dataSource());
//  }
}

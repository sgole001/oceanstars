package oceanstars.ecommerce.infrastructure.jooq.configuration;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import oceanstars.ecommerce.infrastructure.jooq.listener.AuditProducer;
import oceanstars.ecommerce.infrastructure.jooq.listener.ExceptionTranslator;
import oceanstars.ecommerce.infrastructure.jooq.listener.PkProducer;
import oceanstars.ecommerce.infrastructure.jooq.page.AdvancedHandlerMethodArgumentResolver;
import oceanstars.ecommerce.infrastructure.jooq.provider.JsonConverterProvider;
import oceanstars.ecommerce.infrastructure.jooq.spi.DataSourceConfigurationSpi;
import oceanstars.ecommerce.infrastructure.pool.configuration.OceanStarsDataSourceAutoConfig;
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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Jooq配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/25 2:51 下午
 */
@AutoConfiguration(after = {OceanStarsDataSourceAutoConfig.class})
public class JooqConfig extends SpringDataWebConfiguration {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(JooqConfig.class.getName());

  public JooqConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
    super(context, conversionService);
    logger.info("Jooq Config...");
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
   * 配置多数据源数据库操作服务上下文
   *
   * @return 多数据源数据库操作服务上下文
   */
  @Bean
  public CompositeDSLContext dslContexts(OceanstarsDataSource dataSources) {

    logger.info("Jooq DSLs Init Start...");

    // 创建多数据源数据库操作服务上下文对象
    final Map<String, DefaultDSLContext> dslContexts = HashMap.newHashMap(1);

    // 获取单数据源
    final DataSource source = dataSources.getSource();

    // 单数据源的场合下，使用单数据源
    if (null != source) {
      dslContexts.put(OceanstarsDataSource.DEFAULT_DATASOURCE_NAME, new DefaultDSLContext(this.configuration(source)));
    }

    // 获取多数据源信息
    final Map<String, DataSource> sources = dataSources.getSources();

    // 多数据源的场合下，使用多数据源
    if (!CollectionUtils.isEmpty(sources)) {
      // 遍历数据源信息，初始化构建数据库链接上下文对象
      sources.forEach((key, value) -> {
        dslContexts.put(key, new DefaultDSLContext(this.configuration(value)));
      });
    }

    logger.info("Jooq DSLs Contexts Init Completed...");

    return new CompositeDSLContext(dslContexts);
  }

  /**
   * 配置默认数据源数据库操作服务上下文
   *
   * @param dslContexts 多数据源数据库操作服务上下文
   * @return 默认据源数据库操作服务上下文
   */
  @Bean
  public DefaultDSLContext dsl(CompositeDSLContext dslContexts) {

    // 单数据源的场合下，使用单数据源
    return dslContexts.dslContexts().get(OceanstarsDataSource.DEFAULT_DATASOURCE_NAME);
  }

//  /**
//   * 配置事务管理器 - 多数据源事务处理@See {@link OceanstarsTransactionManagementConfiguration}
//   */
//  @ConditionalOnBean(OceanstarsDataSource.class)
//  @Bean
//  public DataSourceTransactionManager transactionManager(OceanstarsDataSource dataSources) {
//    return new DataSourceTransactionManager(dataSources.getSource());
//  }

  @Bean
  @Primary
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public DefaultConfiguration configuration(CompositeDSLContext dslContexts, InjectionPoint injectionPoint) {

    // 获取多数据源操作上下文
    final Map<String, DefaultDSLContext> dslMap = dslContexts.dslContexts();

    // 数据源为空的场合下，抛出异常
    if (CollectionUtils.isEmpty(dslMap)) {
      throw new NoSuchBeanDefinitionException("No Configuration for Jooq.");
    }

    // 单数据源的场合下，直接返回默认数据源配置
    if (dslMap.size() == 1) {
      return (DefaultConfiguration) dslMap.get(OceanstarsDataSource.DEFAULT_DATASOURCE_NAME).configuration();
    }

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

      return (DefaultConfiguration) configurationSpiList.getFirst().getConfiguration(packageName, dslMap);
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
    // 事务管理器
    jooqConfiguration.set(new SpringTransactionProvider(new DataSourceTransactionManager(dataSource)));
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
}

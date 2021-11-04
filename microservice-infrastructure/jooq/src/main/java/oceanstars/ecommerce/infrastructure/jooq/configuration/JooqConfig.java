package oceanstars.ecommerce.infrastructure.jooq.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;
import oceanstars.ecommerce.common.data.databind.jackson.LocalDateTimeDeserializer;
import oceanstars.ecommerce.common.data.databind.jackson.LocalDateTimeSerializer;
import oceanstars.ecommerce.common.tools.ReflectUtil;
import oceanstars.ecommerce.infrastructure.jooq.listener.AuditProducer;
import oceanstars.ecommerce.infrastructure.jooq.listener.ExceptionTranslator;
import oceanstars.ecommerce.infrastructure.jooq.listener.PkProducer;
import oceanstars.ecommerce.infrastructure.jooq.page.AdvancedHandlerMethodArgumentResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.tools.Convert;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Jooq配置
 *
 * @author lingliyi
 * @version 1.0
 * @since 2020/10/25 10:42 下午
 */
@Configuration
public class JooqConfig extends SpringDataWebConfiguration {

  @Resource(name = "dataSource")
  private DataSource dataSource;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(JooqConfig.class.getName());

  public JooqConfig(ApplicationContext context, ObjectFactory<ConversionService> conversionService) {
    super(context, conversionService);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    AdvancedHandlerMethodArgumentResolver resolver = new AdvancedHandlerMethodArgumentResolver();
    argumentResolvers.add(resolver);
    super.addArgumentResolvers(argumentResolvers);
  }

  /**
   * 配置数据库连接提供子
   *
   * @return 数据库连接提供子
   */
  @Bean
  public DataSourceConnectionProvider connectionProvider() {
    return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
  }

  /**
   * 配置数据库操作服务上下文
   *
   * @return 数据库操作服务上下文
   */
  @Bean
  public DefaultDSLContext dsl() {
    return new DefaultDSLContext(configuration());
  }

  /**
   * Jooq配置内容设定
   *
   * @return Jooq配置内容
   */
  @Bean
  public DefaultConfiguration configuration() {

    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

    // 数据库连接配置
    jooqConfiguration.set(connectionProvider());
    // 数据库方言
    jooqConfiguration.set(SQLDialect.MYSQL);
    // 数据库操作异常监听配置
    jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTransformer()));
    /*
     * 激活乐观锁（数据库包含字段：version INTEGER DEFAULT 0）
     *  Jooq生成器插件配置
     * <configuration xmlns="http://www.jooq.org/xsd/jooq-codegen-3.12.0.xsd">
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

    // 配置Jooq初始化的Jackson ObjectMapper
    this.buildJsonMapper();

    return jooqConfiguration;
  }

  /**
   * 数据库操作异常监听配置
   *
   * @return 数据库操作异常监听器
   */
  @Bean
  public ExceptionTranslator exceptionTransformer() {
    return new ExceptionTranslator();
  }

  /***
   * 配置事务管理器
   */
  @Bean(name = "transactionManager")
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(this.connectionProvider().dataSource());
  }

  /**
   * 配置Jooq初始化的Jackson ObjectMapper(默认无法实现LocalDateTime的JSON反序列化)
   */
  private void buildJsonMapper() {

    // Jooq在Convert静态字段中初始化Jackson ObjectMapper, 无法通过全局配置进行修改，只能通过反射进行修改
    final Field field = ReflectUtil.getField(Convert.class, "JSON_MAPPER");
    field.setAccessible(true);
    try {
      ObjectMapper jsonMapper = (ObjectMapper) field.get(null);
      JavaTimeModule module = new JavaTimeModule();
      module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
      module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
      jsonMapper.registerModule(module);

    } catch (IllegalAccessException e) {
      logger.error(e.getMessage());
    }
  }
}

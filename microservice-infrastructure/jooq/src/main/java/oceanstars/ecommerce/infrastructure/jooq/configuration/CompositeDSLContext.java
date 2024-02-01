package oceanstars.ecommerce.infrastructure.jooq.configuration;

import java.util.Map;
import org.jooq.impl.DefaultDSLContext;

/**
 * DSL上下文复合对象，支持多数据源
 *
 * @param dslContexts 多数据源DSL上下文集合
 * @author Clover
 * @version 1.0.0
 * @since 2024/2/1 19:40
 */
public record CompositeDSLContext(Map<String, DefaultDSLContext> dslContexts) {

  /**
   * 构造函数
   *
   * @param dslContexts 多数据源DSL上下文集合
   */
  public CompositeDSLContext {
  }

  /**
   * 获取DSL上下文
   *
   * @param name 数据源名称
   * @return DSL上下文
   */
  public DefaultDSLContext getDslContext(String name) {
    return dslContexts.get(name);
  }
}

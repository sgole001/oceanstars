package oceanstars.ecommerce.infrastructure.grpc.config.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * gRPC客户端连接通道属性配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 11:00 下午
 */
@ConfigurationProperties("grpc")
public class GrpcChannelsProperties {

  /**
   * 将用于全局属性的键值
   */
  public static final String GLOBAL_PROPERTIES_KEY = "GLOBAL";

  /**
   * 每个客户端的通道配置。
   */
  private final Map<String, GrpcChannelProperties> client = new ConcurrentHashMap<>();

  /**
   * 默认Scheme。如果客户端没有设定scheme/address，会使用此默认值。
   */
  private String defaultScheme;

  public Map<String, GrpcChannelProperties> getClient() {
    return client;
  }

  /**
   * 获取指定通道的属性。如果指定的通道不存在，则会自动创建，属性值将集成全局的属性配置
   *
   * @param name 客户端通道名
   * @return 指定通道的属性
   */
  public GrpcChannelProperties getChannel(final String name) {
    // 获取指定通道属性
    final GrpcChannelProperties properties = this.getRawChannel(name);
    // 默认设定使用全局属性覆盖
    properties.copyDefaultsFrom(this.getGlobalChannel());

    return properties;
  }

  /**
   * 获取通道全局属性
   *
   * @return 通道全局属性
   */
  public final GrpcChannelProperties getGlobalChannel() {
    return this.getRawChannel(GLOBAL_PROPERTIES_KEY);
  }

  /**
   * 获取客户端指定通道原生属性
   *
   * @param name 通道名
   * @return 客户端指定通道原生属性
   */
  private GrpcChannelProperties getRawChannel(final String name) {
    return this.client.computeIfAbsent(name, key -> new GrpcChannelProperties());
  }

  public String getDefaultScheme() {
    return defaultScheme;
  }

  public void setDefaultScheme(String defaultScheme) {
    this.defaultScheme = defaultScheme;
  }
}

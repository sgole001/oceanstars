package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import static java.util.Objects.requireNonNull;

import io.grpc.inprocess.InProcessServerBuilder;
import java.util.Collections;
import java.util.List;
import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerProperties;

/**
 * gRPC in-process 服务器工厂类(基本用于测试环境，JUnit等相同JVM环境的情况)
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 4:40 下午
 */
public class InProcessGrpcServerFactory extends AbstractGrpcServerFactory<InProcessServerBuilder> {

  /**
   * gRPC服务器名
   */
  private final String name;

  /**
   * 构造函数
   *
   * @param properties gRPC服务器属性配置
   */
  public InProcessGrpcServerFactory(final GrpcServerProperties properties) {
    this(properties.getInProcessName(), properties);
  }

  /**
   * 构造函数
   *
   * @param properties        gRPC服务器属性配置
   * @param serverConfigurers gRPC服务器配置列表
   */
  public InProcessGrpcServerFactory(final GrpcServerProperties properties, final List<GrpcServerConfigurer> serverConfigurers) {
    this(properties.getInProcessName(), properties, serverConfigurers);
  }

  /**
   * 构造函数
   *
   * @param name       gRPC服务器名
   * @param properties gRPC服务器属性配置
   */
  public InProcessGrpcServerFactory(final String name, final GrpcServerProperties properties) {
    this(name, properties, Collections.emptyList());
  }

  /**
   * 构造函数
   *
   * @param name              gRPC服务器名
   * @param properties        gRPC服务器属性配置
   * @param serverConfigurers gRPC服务器配置列表
   */
  public InProcessGrpcServerFactory(final String name, final GrpcServerProperties properties, final List<GrpcServerConfigurer> serverConfigurers) {
    super(properties, serverConfigurers);
    this.name = requireNonNull(name, "name");
  }

  @Override
  protected InProcessServerBuilder newServerBuilder() {
    return InProcessServerBuilder.forName(this.name);
  }

  @Override
  protected void configureSecurity(final InProcessServerBuilder builder) {
  }

  @Override
  public String getAddress() {
    return "in-process:" + this.name;
  }

  @Override
  public int getPort() {
    return -1;
  }
}

package oceanstars.ecommerce.user.configuration;

import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerSecurityConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * User领域gRPC服务安全配置
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/5/21 18:35
 */
@AutoConfiguration(before = {GrpcServerSecurityConfiguration.class})
public class UserGrpcServerSecurityConfiguration {

//  @Bean
//  AuthenticationManager authenticationManager() {
//    final List<AuthenticationProvider> providers = new ArrayList<>();
//    providers.add(new AnonymousAuthenticationProvider("USER"));
//    return new ProviderManager(providers);
//  }
//
//  @Bean
//  GrpcAuthenticationReader authenticationReader() {
//    final List<GrpcAuthenticationReader> readers = new ArrayList<>();
//    readers.add(new BasicGrpcAuthenticationReader());
//    return new CompositeGrpcAuthenticationReader(readers);
//  }
//
//  @Bean
//  GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
//    final DescriptorGrpcSecurityMetadataSource source = new DescriptorGrpcSecurityMetadataSource();
//    source.set(EcmContentAppServiceGrpc.getCreateContentMethod(), AccessPredicate.authenticated());
//    source.setDefault(AccessPredicate.denyAll());
//    return source;
//  }
}

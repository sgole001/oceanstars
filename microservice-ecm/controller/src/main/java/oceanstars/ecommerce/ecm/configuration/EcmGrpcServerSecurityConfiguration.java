package oceanstars.ecommerce.ecm.configuration;

import oceanstars.ecommerce.infrastructure.grpc.config.server.GrpcServerSecurityConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;

/**
 * <此类的功能说明>
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/22 11:45
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(GrpcServerSecurityConfiguration.class)
public class EcmGrpcServerSecurityConfiguration {

//  @Bean
//  AuthenticationManager authenticationManager() {
//    final List<AuthenticationProvider> providers = new ArrayList<>();
//    providers.add(new AnonymousAuthenticationProvider("ECM"));
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

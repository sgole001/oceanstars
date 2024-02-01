package oceanstars.ecommerce.infrastructure.elasticsearch.configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.ssl.SslOptions;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration.MaybeSecureClientConfigurationBuilder;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

/**
 * Elasticsearch配置信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/12/20 16:21
 */
@AutoConfiguration(
    after = {SslAutoConfiguration.class}
)
@EnableConfigurationProperties({ElasticsearchProperties.class})
public class ElasticsearchConfig extends ElasticsearchConfiguration {

  /**
   * Elasticsearch配置信息
   */
  private final ElasticsearchProperties elasticsearchProperties;

  /**
   * SSL配置
   */
  private final SslBundles sslBundles;

  public ElasticsearchConfig(ElasticsearchProperties elasticsearchProperties, ObjectProvider<SslBundles> sslBundles) {
    this.elasticsearchProperties = elasticsearchProperties;
    this.sslBundles = sslBundles.getObject();
  }

  @Override
  public ClientConfiguration clientConfiguration() {

    // 构建Elasticsearch客户端配置
    MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder()
        // ES集群地址
        .connectedTo(this.elasticsearchProperties.getUris().toArray(new String[0]));
    // ES连接Basic Authentication
    if (null != this.elasticsearchProperties.getUsername() && null != this.elasticsearchProperties.getPassword()) {
      builder.withBasicAuth(this.elasticsearchProperties.getUsername(), this.elasticsearchProperties.getPassword());
    }
    // 连接超时
    if (null != this.elasticsearchProperties.getConnectionTimeout()) {
      builder.withConnectTimeout(this.elasticsearchProperties.getConnectionTimeout());
    }
    // Socket超时
    if (null != this.elasticsearchProperties.getSocketTimeout()) {
      builder.withSocketTimeout(this.elasticsearchProperties.getSocketTimeout());
    }
    // 可选配置：路径前缀，主要用于不同集群背后的一些反向代理
    if (StringUtils.isNotBlank(this.elasticsearchProperties.getPathPrefix())) {
      builder.withPathPrefix(this.elasticsearchProperties.getPathPrefix());
    }
    // SSL配置
    if (null != this.elasticsearchProperties.getRestclient() && null != this.elasticsearchProperties.getRestclient().getSsl()) {
      // 获取SSL绑定信息
      final String sslBundleName = this.elasticsearchProperties.getRestclient().getSsl().getBundle();
      if (StringUtils.isNotBlank(sslBundleName)) {

        builder.usingSsl().withClientConfigurer(ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback.from(httpAsyncClientBuilder -> {
          // 绑定HttpAsyncClient的SSL配置
          final SslBundle sslBundle = this.sslBundles.getBundle(sslBundleName);
          if (null != sslBundle) {
            this.configureSsl(httpAsyncClientBuilder, sslBundle);
          }

          return httpAsyncClientBuilder;
        }));
      }
    }

    return builder.build();
  }

  /**
   * 配置SSL
   *
   * @param httpClientBuilder HTTP客户端构建器
   * @param sslBundle         SSL绑定配置
   */
  private void configureSsl(HttpAsyncClientBuilder httpClientBuilder, SslBundle sslBundle) {
    SSLContext sslcontext = sslBundle.createSslContext();
    SslOptions sslOptions = sslBundle.getOptions();
    httpClientBuilder.setSSLStrategy(
        new SSLIOSessionStrategy(sslcontext, sslOptions.getEnabledProtocols(), sslOptions.getCiphers(), (HostnameVerifier) null));
  }
}

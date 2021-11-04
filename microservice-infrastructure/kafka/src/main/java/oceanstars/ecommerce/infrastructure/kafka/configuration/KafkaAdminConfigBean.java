package oceanstars.ecommerce.infrastructure.kafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka Admin配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 12:25 上午
 */
@Configuration(value = "kafkaAdminConfig")
public class KafkaAdminConfigBean {

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>重要参数(Importance: high)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Value("${kafka.admin.bootstrap.servers:}")
  private String bootstrapServers;

  @Value("${kafka.admin.ssl.key.password:}")
  private String sslKeyPassword;

  @Value("${kafka.admin.ssl.keystore.certificate.chain:}")
  private String sslKeystoreCertificateChain;

  @Value("${kafka.admin.ssl.keystore.key:}")
  private String sslKeystoreKey;

  @Value("${kafka.admin.ssl.keystore.location:}")
  private String sslKeystoreLocation;

  @Value("${kafka.admin.ssl.keystore.password:}")
  private String sslKeystorePassword;

  @Value("${kafka.admin.ssl.truststore.certificates:}")
  private String sslTruststoreCertificates;

  @Value("${kafka.admin.ssl.truststore.location:}")
  private String sslTruststoreLocation;

  @Value("${kafka.admin.ssl.truststore.password:}")
  private String sslTruststorePassword;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>中等参数(Importance: medium)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Value("${kafka.admin.client.dns.lookup:use_all_dns_ips}")
  private String clientDnsLookup;

  @Value("${kafka.admin.client.id:}")
  private String clientId;

  @Value("${kafka.admin.connections.max.idle.ms:300000}")
  private Long connectionsMaxIdleMs;

  @Value("${kafka.admin.default.api.timeout.ms:60000}")
  private Integer defaultApiTimeoutMs;

  @Value("${kafka.admin.receive.buffer.bytes:65536}")
  private Integer receiveBufferBytes;

  @Value("${kafka.admin.request.timeout.ms:30000}")
  private Integer requestTimeoutMs;

  @Value("${kafka.admin.sasl.client.callback.handler.class:}")
  private String saslClientCallbackHandlerClass;

  @Value("${kafka.admin.sasl.jaas.config:}")
  private String saslJaasConfig;

  @Value("${kafka.admin.sasl.kerberos.service.name:}")
  private String saslKerberosServiceName;

  @Value("${kafka.admin.sasl.login.callback.handler.class:}")
  private String saslLoginCallbackHandlerClass;

  @Value("${kafka.admin.sasl.login.class:}")
  private String saslLoginClass;

  @Value("${kafka.admin.sasl.mechanism:GSSAPI}")
  private String saslMechanism;

  @Value("${kafka.admin.security.protocol:PLAINTEXT}")
  private String securityProtocol;

  @Value("${kafka.admin.send.buffer.bytes:131072}")
  private Integer sendBufferBytes;

  @Value("${kafka.admin.socket.connection.setup.timeout.max.ms:127000}")
  private Long socketConnectionSetupTimeoutMaxMs;

  @Value("${kafka.admin.socket.connection.setup.timeout.ms:10000}")
  private Long socketConnectionSetupTimeoutMs;

  @Value("${kafka.admin.ssl.enabled.protocols:TLSv1.2}")
  private String sslEnabledProtocols;

  @Value("${kafka.admin.ssl.keystore.type:JKS}")
  private String sslKeystoreType;

  @Value("${kafka.admin.ssl.protocol:TLSv1.2}")
  private String sslProtocol;

  @Value("${kafka.admin.ssl.provider:}")
  private String sslProvider;

  @Value("${kafka.admin.ssl.truststore.type:JKS}")
  private String sslTruststoreType;

  @Value("${kafka.admin.metadata.max.age.ms:300000}")
  private Long metadataMaxAgeMs;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>次要参数(Importance: low)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> See
   * https://kafka.apache.org/documentation/#adminclientconfigs for more properties
   */
  @Value("${kafka.admin.metric.reporters:}")
  private String metricReporters;

  @Value("${kafka.admin.metrics.num.samples:2}")
  private Integer metricsNumSamples;

  @Value("${kafka.admin.metrics.recording.level:INFO}")
  private String metricsRecordingLevel;

  @Value("${kafka.admin.metrics.sample.window.ms:30000}")
  private Long metricsSampleWindowMs;

  @Value("${kafka.admin.reconnect.backoff.max.ms:1000}")
  private Long reconnectBackoffMaxMs;

  @Value("${kafka.admin.reconnect.backoff.ms:50}")
  private Long reconnectBackoffMs;

  @Value("${kafka.admin.retries:2147483647}")
  private Integer retries;

  @Value("${kafka.admin.retry.backoff.ms:100}")
  private Long retryBackoffMs;

  @Value("${kafka.admin.sasl.kerberos.kinit.cmd:/usr/bin/kinit}")
  private String saslKerberosKinitCmd;

  @Value("${kafka.admin.sasl.kerberos.min.time.before.relogin:60000}")
  private Long saslKerberosMinTimeBeforeRelogin;

  @Value("${kafka.admin.sasl.kerberos.ticket.renew.jitter:0.05}")
  private Double saslKerberosTicketRenewJitter;

  @Value("${kafka.admin.sasl.kerberos.ticket.renew.window.factor:0.8}")
  private Double saslKerberosTicketRenewWindowFactor;

  @Value("${kafka.admin.sasl.login.refresh.buffer.seconds:300}")
  private Short saslLoginRefreshBufferSeconds;

  @Value("${kafka.admin.sasl.login.refresh.min.period.seconds:60}")
  private Short saslLoginRefreshMinPeriodSeconds;

  @Value("${kafka.admin.sasl.login.refresh.window.factor:0.8}")
  private Double saslLoginRefreshWindowFactor;

  @Value("${kafka.admin.sasl.login.refresh.window.jitter:0.05}")
  private Double sasLoginRefreshWindowJitter;

  @Value("${kafka.admin.security.providers:}")
  private String securityProviders;

  @Value("${kafka.admin.ssl.cipher.suites:}")
  private String sslCipherSuites;

  @Value("${kafka.admin.ssl.endpoint.identification.algorithm:https}")
  private String sslEndpointIdentificationAlgorithm;

  @Value("${kafka.admin.ssl.engine.factory.class:}")
  private String sslEngineFactoryClass;

  @Value("${kafka.admin.ssl.keymanager.algorithm:SunX509}")
  private String sslKeymanagerAlgorithm;

  @Value("${kafka.admin.ssl.secure.random.implementation:}")
  private String sslSecureRandomImplementation;

  @Value("${kafka.admin.ssl.trustmanager.algorithm:PKIX}")
  private String sslTrustmanagerAlgorithm;

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public String getSslKeyPassword() {
    return sslKeyPassword;
  }

  public void setSslKeyPassword(String sslKeyPassword) {
    this.sslKeyPassword = sslKeyPassword;
  }

  public String getSslKeystoreCertificateChain() {
    return sslKeystoreCertificateChain;
  }

  public void setSslKeystoreCertificateChain(String sslKeystoreCertificateChain) {
    this.sslKeystoreCertificateChain = sslKeystoreCertificateChain;
  }

  public String getSslKeystoreKey() {
    return sslKeystoreKey;
  }

  public void setSslKeystoreKey(String sslKeystoreKey) {
    this.sslKeystoreKey = sslKeystoreKey;
  }

  public String getSslKeystoreLocation() {
    return sslKeystoreLocation;
  }

  public void setSslKeystoreLocation(String sslKeystoreLocation) {
    this.sslKeystoreLocation = sslKeystoreLocation;
  }

  public String getSslKeystorePassword() {
    return sslKeystorePassword;
  }

  public void setSslKeystorePassword(String sslKeystorePassword) {
    this.sslKeystorePassword = sslKeystorePassword;
  }

  public String getSslTruststoreCertificates() {
    return sslTruststoreCertificates;
  }

  public void setSslTruststoreCertificates(String sslTruststoreCertificates) {
    this.sslTruststoreCertificates = sslTruststoreCertificates;
  }

  public String getSslTruststoreLocation() {
    return sslTruststoreLocation;
  }

  public void setSslTruststoreLocation(String sslTruststoreLocation) {
    this.sslTruststoreLocation = sslTruststoreLocation;
  }

  public String getSslTruststorePassword() {
    return sslTruststorePassword;
  }

  public void setSslTruststorePassword(String sslTruststorePassword) {
    this.sslTruststorePassword = sslTruststorePassword;
  }

  public String getClientDnsLookup() {
    return clientDnsLookup;
  }

  public void setClientDnsLookup(String clientDnsLookup) {
    this.clientDnsLookup = clientDnsLookup;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Long getConnectionsMaxIdleMs() {
    return connectionsMaxIdleMs;
  }

  public void setConnectionsMaxIdleMs(Long connectionsMaxIdleMs) {
    this.connectionsMaxIdleMs = connectionsMaxIdleMs;
  }

  public Integer getDefaultApiTimeoutMs() {
    return defaultApiTimeoutMs;
  }

  public void setDefaultApiTimeoutMs(Integer defaultApiTimeoutMs) {
    this.defaultApiTimeoutMs = defaultApiTimeoutMs;
  }

  public Integer getReceiveBufferBytes() {
    return receiveBufferBytes;
  }

  public void setReceiveBufferBytes(Integer receiveBufferBytes) {
    this.receiveBufferBytes = receiveBufferBytes;
  }

  public Integer getRequestTimeoutMs() {
    return requestTimeoutMs;
  }

  public void setRequestTimeoutMs(Integer requestTimeoutMs) {
    this.requestTimeoutMs = requestTimeoutMs;
  }

  public String getSaslClientCallbackHandlerClass() {
    return saslClientCallbackHandlerClass;
  }

  public void setSaslClientCallbackHandlerClass(String saslClientCallbackHandlerClass) {
    this.saslClientCallbackHandlerClass = saslClientCallbackHandlerClass;
  }

  public String getSaslJaasConfig() {
    return saslJaasConfig;
  }

  public void setSaslJaasConfig(String saslJaasConfig) {
    this.saslJaasConfig = saslJaasConfig;
  }

  public String getSaslKerberosServiceName() {
    return saslKerberosServiceName;
  }

  public void setSaslKerberosServiceName(String saslKerberosServiceName) {
    this.saslKerberosServiceName = saslKerberosServiceName;
  }

  public String getSaslLoginCallbackHandlerClass() {
    return saslLoginCallbackHandlerClass;
  }

  public void setSaslLoginCallbackHandlerClass(String saslLoginCallbackHandlerClass) {
    this.saslLoginCallbackHandlerClass = saslLoginCallbackHandlerClass;
  }

  public String getSaslLoginClass() {
    return saslLoginClass;
  }

  public void setSaslLoginClass(String saslLoginClass) {
    this.saslLoginClass = saslLoginClass;
  }

  public String getSaslMechanism() {
    return saslMechanism;
  }

  public void setSaslMechanism(String saslMechanism) {
    this.saslMechanism = saslMechanism;
  }

  public String getSecurityProtocol() {
    return securityProtocol;
  }

  public void setSecurityProtocol(String securityProtocol) {
    this.securityProtocol = securityProtocol;
  }

  public Integer getSendBufferBytes() {
    return sendBufferBytes;
  }

  public void setSendBufferBytes(Integer sendBufferBytes) {
    this.sendBufferBytes = sendBufferBytes;
  }

  public Long getSocketConnectionSetupTimeoutMaxMs() {
    return socketConnectionSetupTimeoutMaxMs;
  }

  public void setSocketConnectionSetupTimeoutMaxMs(Long socketConnectionSetupTimeoutMaxMs) {
    this.socketConnectionSetupTimeoutMaxMs = socketConnectionSetupTimeoutMaxMs;
  }

  public Long getSocketConnectionSetupTimeoutMs() {
    return socketConnectionSetupTimeoutMs;
  }

  public void setSocketConnectionSetupTimeoutMs(Long socketConnectionSetupTimeoutMs) {
    this.socketConnectionSetupTimeoutMs = socketConnectionSetupTimeoutMs;
  }

  public String getSslEnabledProtocols() {
    return sslEnabledProtocols;
  }

  public void setSslEnabledProtocols(String sslEnabledProtocols) {
    this.sslEnabledProtocols = sslEnabledProtocols;
  }

  public String getSslKeystoreType() {
    return sslKeystoreType;
  }

  public void setSslKeystoreType(String sslKeystoreType) {
    this.sslKeystoreType = sslKeystoreType;
  }

  public String getSslProtocol() {
    return sslProtocol;
  }

  public void setSslProtocol(String sslProtocol) {
    this.sslProtocol = sslProtocol;
  }

  public String getSslProvider() {
    return sslProvider;
  }

  public void setSslProvider(String sslProvider) {
    this.sslProvider = sslProvider;
  }

  public String getSslTruststoreType() {
    return sslTruststoreType;
  }

  public void setSslTruststoreType(String sslTruststoreType) {
    this.sslTruststoreType = sslTruststoreType;
  }

  public Long getMetadataMaxAgeMs() {
    return metadataMaxAgeMs;
  }

  public void setMetadataMaxAgeMs(Long metadataMaxAgeMs) {
    this.metadataMaxAgeMs = metadataMaxAgeMs;
  }

  public String getMetricReporters() {
    return metricReporters;
  }

  public void setMetricReporters(String metricReporters) {
    this.metricReporters = metricReporters;
  }

  public Integer getMetricsNumSamples() {
    return metricsNumSamples;
  }

  public void setMetricsNumSamples(Integer metricsNumSamples) {
    this.metricsNumSamples = metricsNumSamples;
  }

  public String getMetricsRecordingLevel() {
    return metricsRecordingLevel;
  }

  public void setMetricsRecordingLevel(String metricsRecordingLevel) {
    this.metricsRecordingLevel = metricsRecordingLevel;
  }

  public Long getMetricsSampleWindowMs() {
    return metricsSampleWindowMs;
  }

  public void setMetricsSampleWindowMs(Long metricsSampleWindowMs) {
    this.metricsSampleWindowMs = metricsSampleWindowMs;
  }

  public Long getReconnectBackoffMaxMs() {
    return reconnectBackoffMaxMs;
  }

  public void setReconnectBackoffMaxMs(Long reconnectBackoffMaxMs) {
    this.reconnectBackoffMaxMs = reconnectBackoffMaxMs;
  }

  public Long getReconnectBackoffMs() {
    return reconnectBackoffMs;
  }

  public void setReconnectBackoffMs(Long reconnectBackoffMs) {
    this.reconnectBackoffMs = reconnectBackoffMs;
  }

  public Integer getRetries() {
    return retries;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  public Long getRetryBackoffMs() {
    return retryBackoffMs;
  }

  public void setRetryBackoffMs(Long retryBackoffMs) {
    this.retryBackoffMs = retryBackoffMs;
  }

  public String getSaslKerberosKinitCmd() {
    return saslKerberosKinitCmd;
  }

  public void setSaslKerberosKinitCmd(String saslKerberosKinitCmd) {
    this.saslKerberosKinitCmd = saslKerberosKinitCmd;
  }

  public Long getSaslKerberosMinTimeBeforeRelogin() {
    return saslKerberosMinTimeBeforeRelogin;
  }

  public void setSaslKerberosMinTimeBeforeRelogin(Long saslKerberosMinTimeBeforeRelogin) {
    this.saslKerberosMinTimeBeforeRelogin = saslKerberosMinTimeBeforeRelogin;
  }

  public Double getSaslKerberosTicketRenewJitter() {
    return saslKerberosTicketRenewJitter;
  }

  public void setSaslKerberosTicketRenewJitter(Double saslKerberosTicketRenewJitter) {
    this.saslKerberosTicketRenewJitter = saslKerberosTicketRenewJitter;
  }

  public Double getSaslKerberosTicketRenewWindowFactor() {
    return saslKerberosTicketRenewWindowFactor;
  }

  public void setSaslKerberosTicketRenewWindowFactor(Double saslKerberosTicketRenewWindowFactor) {
    this.saslKerberosTicketRenewWindowFactor = saslKerberosTicketRenewWindowFactor;
  }

  public Short getSaslLoginRefreshBufferSeconds() {
    return saslLoginRefreshBufferSeconds;
  }

  public void setSaslLoginRefreshBufferSeconds(Short saslLoginRefreshBufferSeconds) {
    this.saslLoginRefreshBufferSeconds = saslLoginRefreshBufferSeconds;
  }

  public Short getSaslLoginRefreshMinPeriodSeconds() {
    return saslLoginRefreshMinPeriodSeconds;
  }

  public void setSaslLoginRefreshMinPeriodSeconds(Short saslLoginRefreshMinPeriodSeconds) {
    this.saslLoginRefreshMinPeriodSeconds = saslLoginRefreshMinPeriodSeconds;
  }

  public Double getSaslLoginRefreshWindowFactor() {
    return saslLoginRefreshWindowFactor;
  }

  public void setSaslLoginRefreshWindowFactor(Double saslLoginRefreshWindowFactor) {
    this.saslLoginRefreshWindowFactor = saslLoginRefreshWindowFactor;
  }

  public Double getSasLoginRefreshWindowJitter() {
    return sasLoginRefreshWindowJitter;
  }

  public void setSasLoginRefreshWindowJitter(Double sasLoginRefreshWindowJitter) {
    this.sasLoginRefreshWindowJitter = sasLoginRefreshWindowJitter;
  }

  public String getSecurityProviders() {
    return securityProviders;
  }

  public void setSecurityProviders(String securityProviders) {
    this.securityProviders = securityProviders;
  }

  public String getSslCipherSuites() {
    return sslCipherSuites;
  }

  public void setSslCipherSuites(String sslCipherSuites) {
    this.sslCipherSuites = sslCipherSuites;
  }

  public String getSslEndpointIdentificationAlgorithm() {
    return sslEndpointIdentificationAlgorithm;
  }

  public void setSslEndpointIdentificationAlgorithm(String sslEndpointIdentificationAlgorithm) {
    this.sslEndpointIdentificationAlgorithm = sslEndpointIdentificationAlgorithm;
  }

  public String getSslEngineFactoryClass() {
    return sslEngineFactoryClass;
  }

  public void setSslEngineFactoryClass(String sslEngineFactoryClass) {
    this.sslEngineFactoryClass = sslEngineFactoryClass;
  }

  public String getSslKeymanagerAlgorithm() {
    return sslKeymanagerAlgorithm;
  }

  public void setSslKeymanagerAlgorithm(String sslKeymanagerAlgorithm) {
    this.sslKeymanagerAlgorithm = sslKeymanagerAlgorithm;
  }

  public String getSslSecureRandomImplementation() {
    return sslSecureRandomImplementation;
  }

  public void setSslSecureRandomImplementation(String sslSecureRandomImplementation) {
    this.sslSecureRandomImplementation = sslSecureRandomImplementation;
  }

  public String getSslTrustmanagerAlgorithm() {
    return sslTrustmanagerAlgorithm;
  }

  public void setSslTrustmanagerAlgorithm(String sslTrustmanagerAlgorithm) {
    this.sslTrustmanagerAlgorithm = sslTrustmanagerAlgorithm;
  }
}

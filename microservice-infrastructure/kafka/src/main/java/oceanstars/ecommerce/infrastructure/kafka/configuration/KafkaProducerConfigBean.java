package oceanstars.ecommerce.infrastructure.kafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka Producer配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 12:29 上午
 */
@Configuration(value = "kafkaProducerConfig")
public class KafkaProducerConfigBean {

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>重要参数(Importance: high)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Value("${kafka.producer.key.serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String keySerializer;

  @Value("${kafka.producer.value.serializer:org.apache.kafka.common.serialization.StringSerializer}")
  private String valueSerializer;

  @Value("${kafka.producer.acks:1}")
  private String acks;

  @Value("${kafka.producer.bootstrap.servers:}")
  private String bootstrapServers;

  @Value("${kafka.producer.buffer.memory:33554432}")
  private Long bufferMemory;

  @Value("${kafka.producer.compression.type:none}")
  private String compressionType;

  @Value("${kafka.producer.retries:2147483647}")
  private Integer retries;

  @Value("${kafka.producer.ssl.key.password:#{null}}")
  private String sslKeyPassword;

  @Value("${kafka.producer.ssl.keystore.location:#{null}}")
  private String sslKeystoreLocation;

  @Value("${kafka.producer.ssl.keystore.password:#{null}}")
  private String sslKeystorePassword;

  @Value("${kafka.producer.ssl.truststore.location:#{null}}")
  private String sslTruststoreLocation;

  @Value("${kafka.producer.ssl.truststore.password:#{null}}")
  private String sslTruststorePassword;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>中等参数(Importance: medium)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Value("${kafka.producer.batch.size:16384}")
  private Integer batchSize;

  @Value("${kafka.producer.client.dns.lookup:use_all_dns_ips}")
  private String clientDnsLookup;

  @Value("${kafka.producer.client.id:}")
  private String clientId;

  @Value("${kafka.producer.connections.max.idle.ms:540000}")
  private Long connectionsMaxIdleMs;

  @Value("${kafka.producer.delivery.timeout.ms:120000}")
  private Integer deliveryTimeoutMs;

  @Value("${kafka.producer.linger.ms:0}")
  private Long lingerMs;

  @Value("${kafka.producer.max.block.ms:60000}")
  private Long maxBlockMs;

  @Value("${kafka.producer.max.request.size:1048576}")
  private Integer maxRequestSize;

  @Value("${kafka.producer.partitioner.class:org.apache.kafka.clients.producer.internals.DefaultPartitioner}")
  private String partitionerClass;

  @Value("${kafka.producer.receive.buffer.bytes:32768}")
  private Integer receiveBufferBytes;

  @Value("${kafka.producer.request.timeout.ms:30000}")
  private Integer requestTimeoutMs;

  @Value("${kafka.producer.sasl.client.callback.handler.class:#{null}}")
  private String saslClientCallbackHandlerClass;

  @Value("${kafka.producer.sasl.jaas.config:#{null}}")
  private String saslJaasConfig;

  @Value("${kafka.producer.sasl.kerberos.service.name:#{null}}")
  private String saslKerberosServiceName;

  @Value("${kafka.producer.sasl.login.callback.handler.class:#{null}}")
  private String saslLoginCallbackHandlerClass;

  @Value("${kafka.producer.sasl.login.class:#{null}}")
  private String saslLoginClass;

  @Value("${kafka.producer.sasl.mechanism:GSSAPI}")
  private String saslMechanism;

  @Value("${kafka.producer.security.protocol:PLAINTEXT}")
  private String securityProtocol;

  @Value("${kafka.producer.send.buffer.bytes:131072}")
  private Integer sendBufferBytes;

  @Value("${kafka.producer.ssl.enabled.protocols:TLSv1.2}")
  private String sslEnabledProtocols;

  @Value("${kafka.producer.ssl.keystore.type:JKS}")
  private String sslKeystoreType;

  @Value("${kafka.producer.ssl.protocol:TLSv1.2}")
  private String sslProtocol;

  @Value("${kafka.producer.ssl.provider:#{null}}")
  private String sslProvider;

  @Value("${kafka.producer.ssl.truststore.type:JKS}")
  private String sslTruststoreType;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>次要参数(Importance: low)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> See
   * https://kafka.apache.org/documentation/#producerconfigs for more properties
   */
  @Value("${kafka.producer.enable.idempotence:false}")
  private Boolean enableIdempotence;

  @Value("${kafka.producer.interceptor.classes:}")
  private String interceptorClasses;

  @Value("${kafka.producer.max.in.flight.requests.per.connection:5}")
  private Integer maxInFlightRequestsPerConnection;

  @Value("${kafka.producer.metadata.max.age.ms:300000}")
  private Long metadataMaxAgeMs;

  @Value("${kafka.producer.metadata.max.idle.ms:300000}")
  private Long metadataMaxIdleMs;

  @Value("${kafka.producer.metric.reporters:}")
  private String metricReporters;

  @Value("${kafka.producer.metrics.num.samples:2}")
  private Integer metricsNumSamples;

  @Value("${kafka.producer.metrics.recording.level:INFO}")
  private String metricsRecordingLevel;

  @Value("${kafka.producer.metrics.sample.window.ms:30000}")
  private Long metricsSampleWindowMs;

  @Value("${kafka.producer.reconnect.backoff.max.ms:1000}")
  private Long reconnectBackoffMaxMs;

  @Value("${kafka.producer.reconnect.backoff.ms:50}")
  private Long reconnectBackoffMs;

  @Value("${kafka.producer.retry.backoff.ms:100}")
  private Long retryBackoffMs;

  @Value("${kafka.producer.sasl.kerberos.kinit.cmd:/usr/bin/kinit}")
  private String saslKerberosKinitCmd;

  @Value("${kafka.producer.sasl.kerberos.min.time.before.relogin:60000}")
  private Long saslKerberosMinTimeBeforeRelogin;

  @Value("${kafka.producer.sasl.kerberos.ticket.renew.jitter:0.05}")
  private Double saslKerberosTicketRenewJitter;

  @Value("${kafka.producer.sasl.kerberos.ticket.renew.window.factor:0.8}")
  private Double saslKerberosTicketRenewWindowFactor;

  @Value("${kafka.producer.sasl.login.refresh.buffer.seconds:300}")
  private Short saslLoginRefreshBufferSeconds;

  @Value("${kafka.producer.sasl.login.refresh.min.period.seconds:60}")
  private Short saslLoginRefreshMinPeriodSeconds;

  @Value("${kafka.producer.sasl.login.refresh.window.factor:0.8}")
  private Double saslLoginRefreshWindowFactor;

  @Value("${kafka.producer.sasl.login.refresh.window.jitter:0.05}")
  private Double saslLoginRefreshWindowJitter;

  @Value("${kafka.producer.security.providers:#{null}}")
  private String securityProviders;

  @Value("${kafka.producer.ssl.cipher.suites:#{null}}")
  private String sslCipherSuites;

  @Value("${kafka.producer.ssl.endpoint.identification.algorithm:https}")
  private String sslEndpointIdentificationAlgorithm;

  @Value("${kafka.producer.ssl.engine.factory.class:#{null}}")
  private String sslEngineFactoryClass;

  @Value("${kafka.producer.ssl.keymanager.algorithm:SunX509}")
  private String sslKeymanagerAlgorithm;

  @Value("${kafka.producer.ssl.secure.random.implementation:#{null}}")
  private String sslSecureRandomImplementation;

  @Value("${kafka.producer.ssl.trustmanager.algorithm:PKIX}")
  private String sslTrustmanagerAlgorithm;

  @Value("${kafka.producer.transaction.timeout.ms:60000}")
  private Integer transactionTimeoutMs;

  @Value("${kafka.producer.transactional.id:#{null}}")
  private String transactionalId;

  public String getKeySerializer() {
    return keySerializer;
  }

  public void setKeySerializer(String keySerializer) {
    this.keySerializer = keySerializer;
  }

  public String getValueSerializer() {
    return valueSerializer;
  }

  public void setValueSerializer(String valueSerializer) {
    this.valueSerializer = valueSerializer;
  }

  public String getAcks() {
    return acks;
  }

  public void setAcks(String acks) {
    this.acks = acks;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public Long getBufferMemory() {
    return bufferMemory;
  }

  public void setBufferMemory(Long bufferMemory) {
    this.bufferMemory = bufferMemory;
  }

  public String getCompressionType() {
    return compressionType;
  }

  public void setCompressionType(String compressionType) {
    this.compressionType = compressionType;
  }

  public Integer getRetries() {
    return retries;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  public String getSslKeyPassword() {
    return sslKeyPassword;
  }

  public void setSslKeyPassword(String sslKeyPassword) {
    this.sslKeyPassword = sslKeyPassword;
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

  public Integer getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(Integer batchSize) {
    this.batchSize = batchSize;
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

  public Integer getDeliveryTimeoutMs() {
    return deliveryTimeoutMs;
  }

  public void setDeliveryTimeoutMs(Integer deliveryTimeoutMs) {
    this.deliveryTimeoutMs = deliveryTimeoutMs;
  }

  public Long getLingerMs() {
    return lingerMs;
  }

  public void setLingerMs(Long lingerMs) {
    this.lingerMs = lingerMs;
  }

  public Long getMaxBlockMs() {
    return maxBlockMs;
  }

  public void setMaxBlockMs(Long maxBlockMs) {
    this.maxBlockMs = maxBlockMs;
  }

  public Integer getMaxRequestSize() {
    return maxRequestSize;
  }

  public void setMaxRequestSize(Integer maxRequestSize) {
    this.maxRequestSize = maxRequestSize;
  }

  public String getPartitionerClass() {
    return partitionerClass;
  }

  public void setPartitionerClass(String partitionerClass) {
    this.partitionerClass = partitionerClass;
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

  public Boolean getEnableIdempotence() {
    return enableIdempotence;
  }

  public void setEnableIdempotence(Boolean enableIdempotence) {
    this.enableIdempotence = enableIdempotence;
  }

  public String getInterceptorClasses() {
    return interceptorClasses;
  }

  public void setInterceptorClasses(String interceptorClasses) {
    this.interceptorClasses = interceptorClasses;
  }

  public Integer getMaxInFlightRequestsPerConnection() {
    return maxInFlightRequestsPerConnection;
  }

  public void setMaxInFlightRequestsPerConnection(Integer maxInFlightRequestsPerConnection) {
    this.maxInFlightRequestsPerConnection = maxInFlightRequestsPerConnection;
  }

  public Long getMetadataMaxAgeMs() {
    return metadataMaxAgeMs;
  }

  public void setMetadataMaxAgeMs(Long metadataMaxAgeMs) {
    this.metadataMaxAgeMs = metadataMaxAgeMs;
  }

  public Long getMetadataMaxIdleMs() {
    return metadataMaxIdleMs;
  }

  public void setMetadataMaxIdleMs(Long metadataMaxIdleMs) {
    this.metadataMaxIdleMs = metadataMaxIdleMs;
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

  public Double getSaslLoginRefreshWindowJitter() {
    return saslLoginRefreshWindowJitter;
  }

  public void setSaslLoginRefreshWindowJitter(Double saslLoginRefreshWindowJitter) {
    this.saslLoginRefreshWindowJitter = saslLoginRefreshWindowJitter;
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

  public Integer getTransactionTimeoutMs() {
    return transactionTimeoutMs;
  }

  public void setTransactionTimeoutMs(Integer transactionTimeoutMs) {
    this.transactionTimeoutMs = transactionTimeoutMs;
  }

  public String getTransactionalId() {
    return transactionalId;
  }

  public void setTransactionalId(String transactionalId) {
    this.transactionalId = transactionalId;
  }
}

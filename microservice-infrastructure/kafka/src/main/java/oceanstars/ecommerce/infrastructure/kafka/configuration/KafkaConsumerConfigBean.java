package oceanstars.ecommerce.infrastructure.kafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka Consumer配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 12:25 上午
 */
@Configuration(value = "kafkaConsumerConfig")
public class KafkaConsumerConfigBean {

  @Value("${kafka.consumer.concurrency:1}")
  private Integer concurrency;

  @Value("${kafka.consumer.poll.timeout:5000}")
  private Long pollTimeout;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>重要参数(Importance: high)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Value("${kafka.consumer.key.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
  private String keyDeserializer;

  @Value("${kafka.consumer.value.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
  private String valueDeserializer;

  @Value("${kafka.consumer.bootstrap.servers:}")
  private String bootstrapServers;

  @Value("${kafka.consumer.fetch.min.byte:1}")
  private Integer fetchMinBytes;

  @Value("${kafka.consumer.group.id:#{null}}")
  private String groupId;

  @Value("${kafka.consumer.heartbeat.interval.ms:3000}")
  private Integer heartbeatIntervalMs;

  @Value("${kafka.consumer.max.partition.fetch.bytes:1048576}")
  private Integer maxPartitionFetchBytes;

  @Value("${kafka.consumer.session.timeout.ms:10000}")
  private Integer sessionTimeoutMs;

  @Value("${kafka.consumer.ssl.key.password:#{null}}")
  private String sslKeyPassword;

  @Value("${kafka.consumer.ssl.keystore.location:#{null}}")
  private String sslKeystoreLocation;

  @Value("${kafka.consumer.ssl.keystore.password:#{null}}")
  private String sslKeystorePassword;

  @Value("${kafka.consumer.ssl.truststore.location:#{null}}")
  private String sslTruststoreLocation;

  @Value("${kafka.consumer.ssl.truststore.password:#{null}}")
  private String sslTruststorePassword;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>中等参数(Importance: medium)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Value("${kafka.consumer.allow.auto.create.topics:true}")
  private Boolean allowAutoCreateTopics;

  @Value("${kafka.consumer.auto.offset.reset:latest}")
  private String autoOffsetReset;

  @Value("${kafka.consumer.client.dns.lookup:use_all_dns_ips}")
  private String clientDnsLookup;

  @Value("${kafka.consumer.connections.max.idle.ms:540000}")
  private Long connectionsMaxIdleMs;

  @Value("${kafka.consumer.default.api.timeout.ms:60000}")
  private Integer defaultApiTimeoutMs;

  @Value("${kafka.consumer.enable.auto.commit:true}")
  private Boolean enableAutoCommit;

  @Value("${kafka.consumer.exclude.internal.topics:true}")
  private Boolean excludeInternalTopics;

  @Value("${kafka.consumer.fetch.max.bytes:52428800}")
  private Integer fetchMaxBytes;

  @Value("${kafka.consumer.group.instance.id:#{null}}")
  private String groupInstanceId;

  @Value("${kafka.consumer.isolation.level:read_uncommitted}")
  private String isolationLevel;

  @Value("${kafka.consumer.max.poll.interval.ms:300000}")
  private Integer maxPollIntervalMs;

  @Value("${kafka.consumer.max.poll.records:500}")
  private Integer maxPollRecords;

  @Value("${kafka.consumer.partition.assignment.strategy:org.apache.kafka.clients.consumer.RangeAssignor}")
  private String partitionAssignmentStrategy;

  @Value("${kafka.consumer.receive.buffer.bytes:65536}")
  private Integer receiveBufferBytes;

  @Value("${kafka.consumer.request.timeout.ms:30000}")
  private Integer requestTimeoutMs;

  @Value("${kafka.consumer.sasl.client.callback.handler.class:#{null}}")
  private String saslClientCallbackHandlerClass;

  @Value("${kafka.consumer.sasl.jaas.config:#{null}}")
  private String saslJaasConfig;

  @Value("${kafka.consumer.sasl.kerberos.service.name:#{null}}")
  private String saslKerberosServiceName;

  @Value("${kafka.consumer.sasl.login.callback.handler.class:#{null}}")
  private String saslLoginCallbackHandlerClass;

  @Value("${kafka.consumer.sasl.login .class:#{null}}")
  private String saslLoginClass;

  @Value("${kafka.consumer.sasl.mechanism:GSSAPI}")
  private String saslMechanism;

  @Value("${kafka.consumer.security.protocol:PLAINTEXT}")
  private String securityProtocol;

  @Value("${kafka.consumer.send.buffer.bytes:131072}")
  private Integer sendBufferBytes;

  @Value("${kafka.consumer.ssl.enabled.protocols:TLSv1.2}")
  private String sslEnabledProtocols;

  @Value("${kafka.consumer.ssl.keystore.type:JKS}")
  private String sslKeystoreType;

  @Value("${kafka.consumer.ssl.protocol:TLSv1.2}")
  private String sslProtocol;

  @Value("${kafka.consumer.ssl.provider:#{null}}")
  private String sslProvider;

  @Value("${kafka.consumer.ssl.truststore.type:JKS}")
  private String sslTruststoreType;

  /**
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>次要参数(Importance: low)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> See
   * https://kafka.apache.org/documentation/#onsumerconfigs for more properties
   */
  @Value("${kafka.consumer.auto.commit.interval.ms:5000}")
  private Integer autoCommitIntervalMs;

  @Value("${kafka.consumer.check.crcs:true}")
  private Boolean checkCrcs;

  @Value("${kafka.consumer.client.id:}")
  private String clientId;

  @Value("${kafka.consumer.client.rack:}")
  private String clientRack;

  @Value("${kafka.consumer.fetch.max.wait.ms:500}")
  private Integer fetchMaxWaitMs;

  @Value("${kafka.consumer.interceptorClasse:}")
  private String interceptorClasses;

  @Value("${kafka.consumer.metadata.max.age.ms:300000}")
  private Long metadataMaxAgeMs;

  @Value("${kafka.consumer.metric.reporters:}")
  private String metricReporters;

  @Value("${kafka.consumer.metrics.num.samples:2}")
  private Integer metricsNumSamples;

  @Value("${kafka.consumer.metrics.recording.level:INFO}")
  private String metricsRecordingLevel;

  @Value("${kafka.consumer.metrics.sample.window.ms:30000}")
  private Long metricsSampleWindowMs;

  @Value("${kafka.consumer.reconnect.backoff.max.ms:1000}")
  private Long reconnectBackoffMaxMs;

  @Value("${kafka.consumer.reconnect.backoff.ms:50}")
  private Long reconnectBackoffMs;

  @Value("${kafka.consumer.retry.backoff.ms:100}")
  private Long retryBackoffMs;

  @Value("${kafka.consumer.sasl.kerberos.kinit.cmd:/usr/bin/kinit}")
  private String saslKerberosKinitCmd;

  @Value("${kafka.consumer.sasl.kerberos.min.time.before.relogin:60000}")
  private Long saslKerberosMinTimeBeforeRelogin;

  @Value("${kafka.consumer.sasl.kerberos.ticket.renew.jitter:0.05}")
  private Double saslKerberosTicketRenewJitter;

  @Value("${kafka.consumer.sasl.kerberos.ticket.renew.window.factor:0.8}")
  private Double saslKerberosTicketRenewWindowFactor;

  @Value("${kafka.consumer.sasl.login.refresh.buffer.seconds:300}")
  private Short saslLoginRefreshBufferSeconds;

  @Value("${kafka.consumer.sasl.login.refresh.min.period.seconds:60}")
  private Short saslLoginRefreshMinPeriodSeconds;

  @Value("${kafka.consumer.sasl.login.refresh.window.factor:0.8}")
  private Double saslLoginRefreshWindowFactor;

  @Value("${kafka.consumer.sasl.login.refresh.window.jitter:0.05}")
  private Double saslLoginRefreshWindowJitter;

  @Value("${kafka.consumer.security.providers:#{null}}")
  private String securityProviders;

  @Value("${kafka.consumer.ssl.cipher.suites:#{null}}")
  private String sslCipherSuites;

  @Value("${kafka.consumer.ssl.endpoint.identification.algorithm:https}")
  private String sslEndpointIdentificationAlgorithm;

  @Value("${kafka.consumer.ssl.engine.factory.class:#{null}}")
  private String sslEngineFactoryClass;

  @Value("${kafka.consumer.ssl.keymanager.algorithm:SunX509}")
  private String sslKeymanagerAlgorithm;

  @Value("${kafka.consumer.ssl.secure.random.implementation:#{null}}")
  private String sslSecureRandomImplementation;

  @Value("${kafka.consumer.ssl.trustmanager.algorithm:PKIX}")
  private String sslTrustmanagerAlgorithm;

  public Integer getConcurrency() {
    return concurrency;
  }

  public void setConcurrency(Integer concurrency) {
    this.concurrency = concurrency;
  }

  public Long getPollTimeout() {
    return pollTimeout;
  }

  public void setPollTimeout(Long pollTimeout) {
    this.pollTimeout = pollTimeout;
  }

  public String getKeyDeserializer() {
    return keyDeserializer;
  }

  public void setKeyDeserializer(String keyDeserializer) {
    this.keyDeserializer = keyDeserializer;
  }

  public String getValueDeserializer() {
    return valueDeserializer;
  }

  public void setValueDeserializer(String valueDeserializer) {
    this.valueDeserializer = valueDeserializer;
  }

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  public Integer getFetchMinBytes() {
    return fetchMinBytes;
  }

  public void setFetchMinBytes(Integer fetchMinBytes) {
    this.fetchMinBytes = fetchMinBytes;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public Integer getHeartbeatIntervalMs() {
    return heartbeatIntervalMs;
  }

  public void setHeartbeatIntervalMs(Integer heartbeatIntervalMs) {
    this.heartbeatIntervalMs = heartbeatIntervalMs;
  }

  public Integer getMaxPartitionFetchBytes() {
    return maxPartitionFetchBytes;
  }

  public void setMaxPartitionFetchBytes(Integer maxPartitionFetchBytes) {
    this.maxPartitionFetchBytes = maxPartitionFetchBytes;
  }

  public Integer getSessionTimeoutMs() {
    return sessionTimeoutMs;
  }

  public void setSessionTimeoutMs(Integer sessionTimeoutMs) {
    this.sessionTimeoutMs = sessionTimeoutMs;
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

  public Boolean getAllowAutoCreateTopics() {
    return allowAutoCreateTopics;
  }

  public void setAllowAutoCreateTopics(Boolean allowAutoCreateTopics) {
    this.allowAutoCreateTopics = allowAutoCreateTopics;
  }

  public String getAutoOffsetReset() {
    return autoOffsetReset;
  }

  public void setAutoOffsetReset(String autoOffsetReset) {
    this.autoOffsetReset = autoOffsetReset;
  }

  public String getClientDnsLookup() {
    return clientDnsLookup;
  }

  public void setClientDnsLookup(String clientDnsLookup) {
    this.clientDnsLookup = clientDnsLookup;
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

  public Boolean getEnableAutoCommit() {
    return enableAutoCommit;
  }

  public void setEnableAutoCommit(Boolean enableAutoCommit) {
    this.enableAutoCommit = enableAutoCommit;
  }

  public Boolean getExcludeInternalTopics() {
    return excludeInternalTopics;
  }

  public void setExcludeInternalTopics(Boolean excludeInternalTopics) {
    this.excludeInternalTopics = excludeInternalTopics;
  }

  public Integer getFetchMaxBytes() {
    return fetchMaxBytes;
  }

  public void setFetchMaxBytes(Integer fetchMaxBytes) {
    this.fetchMaxBytes = fetchMaxBytes;
  }

  public String getGroupInstanceId() {
    return groupInstanceId;
  }

  public void setGroupInstanceId(String groupInstanceId) {
    this.groupInstanceId = groupInstanceId;
  }

  public String getIsolationLevel() {
    return isolationLevel;
  }

  public void setIsolationLevel(String isolationLevel) {
    this.isolationLevel = isolationLevel;
  }

  public Integer getMaxPollIntervalMs() {
    return maxPollIntervalMs;
  }

  public void setMaxPollIntervalMs(Integer maxPollIntervalMs) {
    this.maxPollIntervalMs = maxPollIntervalMs;
  }

  public Integer getMaxPollRecords() {
    return maxPollRecords;
  }

  public void setMaxPollRecords(Integer maxPollRecords) {
    this.maxPollRecords = maxPollRecords;
  }

  public String getPartitionAssignmentStrategy() {
    return partitionAssignmentStrategy;
  }

  public void setPartitionAssignmentStrategy(String partitionAssignmentStrategy) {
    this.partitionAssignmentStrategy = partitionAssignmentStrategy;
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

  public Integer getAutoCommitIntervalMs() {
    return autoCommitIntervalMs;
  }

  public void setAutoCommitIntervalMs(Integer autoCommitIntervalMs) {
    this.autoCommitIntervalMs = autoCommitIntervalMs;
  }

  public Boolean getCheckCrcs() {
    return checkCrcs;
  }

  public void setCheckCrcs(Boolean checkCrcs) {
    this.checkCrcs = checkCrcs;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getClientRack() {
    return clientRack;
  }

  public void setClientRack(String clientRack) {
    this.clientRack = clientRack;
  }

  public Integer getFetchMaxWaitMs() {
    return fetchMaxWaitMs;
  }

  public void setFetchMaxWaitMs(Integer fetchMaxWaitMs) {
    this.fetchMaxWaitMs = fetchMaxWaitMs;
  }

  public String getInterceptorClasses() {
    return interceptorClasses;
  }

  public void setInterceptorClasses(String interceptorClasses) {
    this.interceptorClasses = interceptorClasses;
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
}

package oceanstars.ecommerce.infrastructure.grpc.factory.client.channel;

import static java.util.Comparator.comparingLong;
import static java.util.Objects.requireNonNull;

import com.google.common.collect.Lists;
import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelProperties;
import oceanstars.ecommerce.infrastructure.grpc.config.client.GrpcChannelsProperties;
import oceanstars.ecommerce.infrastructure.grpc.interceptor.client.GlobalClientInterceptorRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.unit.DataSize;

/**
 * gRPC客户端连接通道虚拟工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/15 9:48 上午
 */
public abstract class AbstractChannelFactory<T extends ManagedChannelBuilder<T>> implements GrpcChannelFactory, AutoCloseable {

  /**
   * gRPC客户端连接通道属性配置
   */
  private final GrpcChannelsProperties properties;

  /**
   * 全局客户端拦截器注册类
   */
  protected final GlobalClientInterceptorRegistry globalClientInterceptorRegistry;

  /**
   * gRPC客户端连接通道配置列表
   */
  protected final List<GrpcChannelConfigurer> channelConfigurers;

  /**
   * gRPC客户端连接通道映射集合（因为需要满足gRPC客户端中的线程安全性，应该重用{@link ManagedChannel}以允许连接重用）
   */
  @GuardedBy("this")
  private final Map<String, ManagedChannel> channels = new ConcurrentHashMap<>();

  /**
   * gRPC客户端连接通道状态映射集合
   */
  private final Map<String, ConnectivityState> channelStates = new ConcurrentHashMap<>();

  /**
   * gRPC客户端连接通道关闭标志位
   */
  private boolean shutdown = false;

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(AbstractChannelFactory.class.getName());

  /**
   * 构造函数
   *
   * @param properties                      gRPC客户端连接通道属性配置
   * @param globalClientInterceptorRegistry 全局客户端拦截器注册类
   * @param channelConfigurers              gRPC客户端连接通道配置列表
   */
  protected AbstractChannelFactory(final GrpcChannelsProperties properties,
      final GlobalClientInterceptorRegistry globalClientInterceptorRegistry,
      final List<GrpcChannelConfigurer> channelConfigurers) {
    this.properties = requireNonNull(properties, "properties");
    this.globalClientInterceptorRegistry = requireNonNull(globalClientInterceptorRegistry, "globalClientInterceptorRegistry");
    this.channelConfigurers = requireNonNull(channelConfigurers, "channelConfigurers");
  }

  @Override
  public Channel createChannel(String name, List<ClientInterceptor> customInterceptors, boolean sortInterceptors) {

    // gRPC连接通道
    final Channel channel;
    // 线程安全
    synchronized (this) {
      // 假如gRPC连接通道工厂已经关闭，异常抛出
      if (this.shutdown) {
        throw new IllegalStateException("gRPC连接通道工厂已经关闭!");
      }
      // 获取指定gRPC客户端的连接通道
      channel = this.channels.computeIfAbsent(name, this::newManagedChannel);
    }

    // 获取gRPC客户端全局拦截器列表
    final List<ClientInterceptor> interceptors = Lists.newArrayList(this.globalClientInterceptorRegistry.getClientInterceptors());
    // 追加指定gRPC客户端连接通道的自定义拦截器
    interceptors.addAll(customInterceptors);
    // 执行拦截器排序
    if (sortInterceptors) {
      this.globalClientInterceptorRegistry.sortInterceptors(interceptors);
    }

    // 返回拦截器设定后的gRPC客户端连接通道
    return ClientInterceptors.interceptForward(channel, interceptors);
  }

  @Override
  public Map<String, ConnectivityState> getConnectivityState() {
    return Collections.unmodifiableMap(this.channelStates);
  }


  @Override
  public void close() {

    // 如果gRPC客户端连接通道工厂已经关闭，直接返回
    if (this.shutdown) {
      return;
    }

    // 关闭状态谁定
    this.shutdown = true;

    // 初始化需要关闭的gRPC客户端连接通道列表
    final List<ShutdownRecord> shutdownEntries = new ArrayList<>();

    // 遍历gRPC客户端连接通道映射集合
    for (final Entry<String, ManagedChannel> entry : this.channels.entrySet()) {

      // 获取当前gRPC客户端连接通道
      final ManagedChannel channel = entry.getValue();

      // 执行关闭处理
      channel.shutdown();

      // 获取gRPC客户端连接通道关闭宽限期
      final long gracePeriod = this.properties.getChannel(entry.getKey()).getShutdownGracePeriod().toMillis();

      // 添加需要关闭的gRPC客户端连接通道的实例记录
      shutdownEntries.add(new ShutdownRecord(entry.getKey(), channel, gracePeriod));
    }

    try {

      // 获取当前时间
      final long start = System.currentTimeMillis();

      // 根据gRPC客户端连接通道关闭宽限期顺序排序需要关闭的连接通道记录（后续遍历时间不会超过最长宽限期）
      shutdownEntries.sort(comparingLong(ShutdownRecord::getGracePeriod));

      // 遍历需要关闭的连接通道记录列表
      for (final ShutdownRecord entry : shutdownEntries) {

        // 判定连接通道是否还未关闭，如果超过宽限期，则直接立刻关闭
        if (!entry.channel.isTerminated()) {

          // 计算已经等待的时间
          final long waitedTime = System.currentTimeMillis() - start;
          // 计算离开宽限期还有多少时间
          final long waitTime = entry.gracePeriod - waitedTime;

          // 宽限期内，等待中止
          if (waitTime > 0) {
            entry.channel.awaitTermination(waitTime, TimeUnit.MILLISECONDS);
          }

          // 超时则立即关闭
          entry.channel.shutdownNow();
        }
      }
    } catch (final InterruptedException e) {
      // 中断当前线程，加速连接通道关闭处理
      Thread.currentThread().interrupt();
    } finally {
      for (final ManagedChannel channel : this.channels.values()) {
        // 最后连接通道强制关闭
        if (!channel.isTerminated()) {
          channel.shutdownNow();
        }
      }
    }

    // 释放资源
    this.channels.clear();
    this.channelStates.clear();
  }

  /**
   * 为指定的gRPC客户端创建{@link ManagedChannelBuilder}
   *
   * @param name gRPC客户端名
   * @return 新创建的gRPC客户端连接通道构建器
   */
  protected abstract T newChannelBuilder(String name);

  /**
   * 为指定的gRPC客户端创建连接通道{@link ManagedChannel}
   *
   * @param name gRPC客户端名
   * @return 新创建的gRPC客户端创建连接通道
   */
  protected ManagedChannel newManagedChannel(final String name) {

    // 根据gRPC客户端创建连接通道构建器
    final T builder = newChannelBuilder(name);
    // 配置连接通道属性
    configure(builder, name);
    // 构建gRPC客户端连接通道
    final ManagedChannel channel = builder.build();

    // gRPC启动时通道连接
    this.connectOnStartup(name, channel);
    //
    this.watchConnectivityState(name, channel);

    return channel;
  }

  /**
   * 通过给定的gRPC客户端连接通道构建器配置通道属性
   *
   * @param builder gRPC客户端连接通道构建器
   * @param name    gRPC客户端名
   */
  protected void configure(final T builder, final String name) {

    // Keep Alive相关属性配置
    this.configureKeepAlive(builder, name);

    // 限制相关配置
    this.configureLimits(builder, name);

    // 解编码相关配置
    this.configureCompression(builder, name);

    for (final GrpcChannelConfigurer channelConfigurer : this.channelConfigurers) {
      channelConfigurer.accept(builder, name);
    }
  }

  /**
   * 配置gRPC客户端连接通道Keep Alive相关属性
   *
   * @param builder gRPC客户端连接通道构建器
   * @param name    gRPC客户端名
   */
  protected void configureKeepAlive(final T builder, final String name) {

    // 根据指定gRPC客户端名获取对应的连接通道属性设定
    final GrpcChannelProperties grpcChannelProperties = this.properties.getChannel(name);

    // 如果开启Keep Alive
    if (grpcChannelProperties.isEnableKeepAlive()) {
      builder.keepAliveTime(grpcChannelProperties.getKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
          .keepAliveTimeout(grpcChannelProperties.getKeepAliveTimeout().toNanos(), TimeUnit.NANOSECONDS)
          .keepAliveWithoutCalls(grpcChannelProperties.isKeepAliveWithoutCalls());
    }
  }

  /**
   * 配置gRPC客户端连接通道限制相关属性
   *
   * @param builder gRPC客户端连接通道构建器
   * @param name    gRPC客户端名
   */
  protected void configureLimits(final T builder, final String name) {

    // 根据指定gRPC客户端名获取对应的连接通道属性设定
    final GrpcChannelProperties grpcChannelProperties = this.properties.getChannel(name);

    // 通道允许接收的最大消息大小
    final DataSize maxInboundMessageSize = grpcChannelProperties.getMaxInboundMessageSize();
    if (maxInboundMessageSize != null) {
      builder.maxInboundMessageSize((int) maxInboundMessageSize.toBytes());
    }
  }

  /**
   * 配置gRPC客户端连接通道解编码相关属性
   *
   * @param builder gRPC客户端连接通道构建器
   * @param name    gRPC客户端名
   */
  protected void configureCompression(final T builder, final String name) {

    // 根据指定gRPC客户端名获取对应的连接通道属性设定
    final GrpcChannelProperties grpcChannelProperties = this.properties.getChannel(name);

    // 是否应启用入站流的全流解压
    if (grpcChannelProperties.isFullStreamDecompression()) {
      builder.enableFullStreamDecompression();
    }
  }

  /**
   * 根据指定gRPC客户端名获取对应的连接通道属性设定
   *
   * @param name gRPC客户端名
   * @return 指定gRPC客户端名对应的连接通道属性设定
   */
  protected final GrpcChannelProperties getPropertiesFor(final String name) {
    return this.properties.getChannel(name);
  }

  /**
   * gRPC启动时通道连接
   *
   * @param name    gRPC客户端名
   * @param channel gRPC客户端连接通道管理实例
   */
  private void connectOnStartup(final String name, final ManagedChannel channel) {

    // 获取应用程序启动时的连接超时
    final Duration timeout = this.properties.getChannel(name).getImmediateConnectTimeout();

    if (timeout.isZero()) {
      return;
    }

    // 加入当前连接通道状态为IDLE，则尝试创建连接
    channel.getState(true);

    // 创建线程同步计数器
    final CountDownLatch readyLatch = new CountDownLatch(1);
    // 递归监听等待连接状态就绪
    this.waitForReady(channel, readyLatch);

    // 在给定的超时时间内判定是否连接
    boolean connected;
    try {
      connected = readyLatch.await(timeout.toMillis(), TimeUnit.MILLISECONDS);
    } catch (final InterruptedException e) {
      // 中断当前线程
      Thread.currentThread().interrupt();
      connected = false;
    }

    if (!connected) {
      throw new IllegalStateException("不能连通连接通道" + name);
    }

    logger.info("成功连接通道{}", name);
  }

  /**
   * 等待就绪状态
   *
   * @param channel     gRPC客户端连接通道
   * @param readySignal 就绪信号
   */
  private void waitForReady(final ManagedChannel channel, final CountDownLatch readySignal) {

    // 获取连接通道的连接状态
    final ConnectivityState state = channel.getState(false);

    // 假如连接通道的连接状态已经就绪
    if (state == ConnectivityState.READY) {
      // 执行线程同步计数器
      readySignal.countDown();
    } else {
      // 递归注册一次性回调监听连接状态的改变(ManagedChannel.getState返回)
      channel.notifyWhenStateChanged(state, () -> waitForReady(channel, readySignal));
    }
  }

  /**
   * 监控指定gRPC客户端的通道连接状态
   *
   * @param name    gRPC客户端名
   * @param channel gRPC客户端连接通道
   */
  protected void watchConnectivityState(final String name, final ManagedChannel channel) {

    // 获取连接通道的连接状态
    final ConnectivityState state = channel.getState(false);

    // 递归映射指定gRPC客户端对应的非关机状态的连接状态。
    this.channelStates.put(name, state);
    if (state != ConnectivityState.SHUTDOWN) {
      channel.notifyWhenStateChanged(state, () -> watchConnectivityState(name, channel));
    }
  }

  /**
   * <此类的功能说明>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2021/11/15 2:36 下午
   */
  private record ShutdownRecord(String name, ManagedChannel channel, long gracePeriod) {

    /**
     * 构造函数
     *
     * @param name        gRPC客户端名
     * @param channel     gRPC客户端连接通道实例
     * @param gracePeriod gRPC连接关闭宽限期
     */
    private ShutdownRecord(final String name, final ManagedChannel channel, final long gracePeriod) {
      this.name = name;
      this.channel = channel;
      this.gracePeriod = gracePeriod < 0 ? Long.MAX_VALUE : gracePeriod;
    }

    /**
     * 获取gRPC连接关闭宽限期
     *
     * @return gRPC连接关闭宽限期
     */
    long getGracePeriod() {
      return this.gracePeriod;
    }
  }
}

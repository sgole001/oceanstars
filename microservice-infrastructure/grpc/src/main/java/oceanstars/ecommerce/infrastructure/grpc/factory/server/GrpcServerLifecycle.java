package oceanstars.ecommerce.infrastructure.grpc.factory.server;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import io.grpc.Server;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.SmartLifecycle;

/**
 * gRPC服务器生命周期
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/11 4:01 下午
 */
public class GrpcServerLifecycle implements SmartLifecycle {

  /**
   * gRPC服务器工厂接口
   */
  private final GrpcServerFactory factory;

  /**
   * grpc服务优雅关闭时间
   */
  private final Duration shutdownGracePeriod;

  /**
   * grpc服务接口
   */
  private Server server;

  /**
   * 服务器计数器
   */
  private static final AtomicInteger SERVER_COUNTER = new AtomicInteger(-1);

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(GrpcServerLifecycle.class.getName());

  /**
   * 构造函数
   *
   * @param factory             gRPC服务器工厂接口
   * @param shutdownGracePeriod grpc服务优雅关闭时间
   */
  public GrpcServerLifecycle(final GrpcServerFactory factory, final Duration shutdownGracePeriod) {
    this.factory = requireNonNull(factory, "factory");
    this.shutdownGracePeriod = requireNonNull(shutdownGracePeriod, "shutdownGracePeriod");
  }

  @Override
  public void start() {
    this.createAndStartGrpcServer();
  }

  @Override
  public void stop() {
    this.stopAndReleaseGrpcServer();
  }

  @Override
  public void stop(Runnable callback) {
    this.stop();
    callback.run();
  }

  @Override
  public boolean isRunning() {
    return this.server != null && !this.server.isShutdown();
  }

  @Override
  public boolean isAutoStartup() {
    return true;
  }

  @Override
  public int getPhase() {
    return Integer.MAX_VALUE;
  }

  /**
   * 创建并启动gRPC服务器
   */
  protected void createAndStartGrpcServer() {

    try {

      if (this.server == null) {

        // 创建gRPC服务器实例
        this.server = this.factory.createServer();
        // 启动gRPC服务器
        this.server.start();

        // 记录启动成功日志
        logger.log(Level.INFO,
            () -> MessageFormat.format("*** gRPC服务器启动完成，正在监听:{0}:{1}", this.factory.getAddress(), this.factory.getPort()));

        Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {

            // 设置线程名
            this.setName("grpc-server-container-" + SERVER_COUNTER.incrementAndGet());
            // 线程不是守护线程
            this.setDaemon(false);

            logger.error("*** 由于JVM正在关闭，关闭gRPC服务器。");
            // 关闭gRPC服务
            GrpcServerLifecycle.this.stop();
            logger.error("*** gRPC服务器关闭。");
          }
        });
      }
    } catch (Exception e) {
      throw new IllegalStateException("*** gRPC服务启动失败。", e);
    }
  }

  /**
   * 关闭并释放gRPC服务器
   */
  protected void stopAndReleaseGrpcServer() {

    if (this.server != null) {

      // 时间转换为milliseconds
      final long millis = this.shutdownGracePeriod.toMillis();

      logger.debug("*** 准备gRPC服务器关闭。");

      // 关闭gRPC服务器
      this.server.shutdown();

      try {

        if (millis > 0) {
          this.server.awaitTermination(millis, MILLISECONDS);
        } else if (millis < 0) {
          // 无限等待
          this.server.awaitTermination();
        }

      } catch (final InterruptedException e) {
        // 阻断当前线程
        Thread.currentThread().interrupt();
      } finally {
        // 立即关闭gRPC服务器
        this.server.shutdownNow();
        // 释放资源
        this.server = null;
      }

      logger.info("*** 完成gRPC服务器关闭。");
    }
  }
}

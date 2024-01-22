package oceanstars.ecommerce.infrastructure.grpc.interceptor.server;

import io.grpc.Context;
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.ServerCall.Listener;

/**
 * gRPC服务端调用认证处理监听器抽象类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/21 15:38
 */
public abstract class AbstractAuthenticatingServerCallListener<ReqT> extends SimpleForwardingServerCallListener<ReqT> {

  /**
   * gRPC调用上下文
   */
  private final Context context;

  /**
   * 构造函数
   *
   * @param delegate 委托对象
   * @param context  gRPC调用上下文
   */
  protected AbstractAuthenticatingServerCallListener(Listener<ReqT> delegate, final Context context) {
    super(delegate);
    this.context = context;
  }

  protected final Context context() {
    return this.context;
  }

  /**
   * 在实际调用之前附加身份验证上下文
   */
  protected abstract void attachAuthenticationContext();

  /**
   * 在实际调用之后分离身份验证上下文
   */
  protected abstract void detachAuthenticationContext();

  @Override
  public void onMessage(final ReqT message) {
    final Context previous = this.context.attach();
    try {
      // 在接收到客户端发送的消息时，附加身份验证上下文
      this.attachAuthenticationContext();
      super.onMessage(message);
    } finally {
      // 分离身份验证上下文
      this.detachAuthenticationContext();
      this.context.detach(previous);
    }
  }

  @Override
  public void onHalfClose() {
    final Context previous = this.context.attach();
    try {
      // 在客户端完成消息发送（但仍可接收服务端响应）时，附加身份验证上下文
      this.attachAuthenticationContext();
      super.onHalfClose();
    } finally {
      // 分离身份验证上下文
      this.detachAuthenticationContext();
      this.context.detach(previous);
    }
  }

  @Override
  public void onCancel() {
    final Context previous = this.context.attach();
    try {
      // 在客户端取消调用时，附加身份验证上下文
      this.attachAuthenticationContext();
      super.onCancel();
    } finally {
      // 分离身份验证上下文
      this.detachAuthenticationContext();
      this.context.detach(previous);
    }
  }

  @Override
  public void onComplete() {
    final Context previous = this.context.attach();
    try {
      // 在调用完成时，附加身份验证上下文
      this.attachAuthenticationContext();
      super.onComplete();
    } finally {
      // 分离身份验证上下文
      this.detachAuthenticationContext();
      this.context.detach(previous);
    }
  }

  @Override
  public void onReady() {
    final Context previous = this.context.attach();
    try {
      // 在流可以处理更多消息时，附加身份验证上下文
      this.attachAuthenticationContext();
      super.onReady();
    } finally {
      this.detachAuthenticationContext();
      this.context.detach(previous);
    }
  }
}

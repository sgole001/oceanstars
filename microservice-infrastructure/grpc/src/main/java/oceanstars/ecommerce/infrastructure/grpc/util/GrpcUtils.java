package oceanstars.ecommerce.infrastructure.grpc.util;

import io.grpc.MethodDescriptor;

/**
 * 实用程序类：从 grpc 类中提取一些信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/9 6:14 下午
 */
public final class GrpcUtils {

  /**
   * 常量：Unix域套接字地址scheme
   */
  public static final String DOMAIN_SOCKET_ADDRESS_SCHEME = "unix";

  /**
   * 常量：Unix域套接字地址前缀
   */
  public static final String DOMAIN_SOCKET_ADDRESS_PREFIX = DOMAIN_SOCKET_ADDRESS_SCHEME + ":";

  /**
   * 常量：grpc 服务器端口，-1 表示不启动进程间服务器
   */
  public static final int INTER_PROCESS_DISABLE = -1;

  /**
   * 从给定的完整地址中提取域套接字地址特定路径（地址必须满足https://grpc.github.io/grpc/cpp/md_doc_naming.html指定的要求）
   *
   * @param address 提取用地址
   * @return 域套接字地址特定路径
   * @throws IllegalArgumentException 无效地址异常
   */
  public static String extractDomainSocketAddressPath(final String address) {

    // 地址非Unix域套接字地址前缀开头，作为无效异常处理
    if (!address.startsWith(DOMAIN_SOCKET_ADDRESS_PREFIX)) {
      throw new IllegalArgumentException(address + " 不是有效的域套接字地址");
    }

    // 获取Unix域套接字地址前缀后的地址信息
    String path = address.substring(DOMAIN_SOCKET_ADDRESS_PREFIX.length());

    // 去除"//"前缀
    if (path.startsWith("//")) {
      path = path.substring(2);
    }

    return path;
  }

  /**
   * 提取gRPC服务名
   *
   * @param method gRPC服务方法信息
   * @return gRPC服务名
   */
  public static String extractServiceName(final MethodDescriptor<?, ?> method) {
    return MethodDescriptor.extractFullServiceName(method.getFullMethodName());
  }

  /**
   * 提取gRPC服务方法名
   *
   * @param method gRPC服务方法信息
   * @return gRPC服务方法名
   */
  public static String extractMethodName(final MethodDescriptor<?, ?> method) {

    final String fullMethodName = method.getFullMethodName();
    final int index = fullMethodName.lastIndexOf('/');
    if (index == -1) {
      return fullMethodName;
    }
    return fullMethodName.substring(index + 1);
  }

  private GrpcUtils() {
  }
}

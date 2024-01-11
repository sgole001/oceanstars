package oceanstars.ecommerce.common.tools;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;
import javax.net.ServerSocketFactory;
import org.springframework.util.Assert;

/**
 * Socket工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/11/23 14:55
 */
public class SocketUtils {

  static final int PORT_RANGE_MIN = 1024;
  static final int PORT_RANGE_MAX = 65535;
  private static final int PORT_RANGE_PLUS_ONE = 64512;
  private static final int MAX_ATTEMPTS = 1000;
  private static final Random RANDOM = new Random(System.nanoTime());
  private static final SocketUtils INSTANCE = new SocketUtils();

  /**
   * 构造函数
   */
  public SocketUtils() {
    // ...
  }

  /**
   * 获取一个可用的TCP端口
   *
   * @return 可用的TCP端口
   */
  public static int findAvailableTcpPort() {
    return INSTANCE.findAvailableTcpPortInternal();
  }

  /**
   * 获取一个可用的TCP端口
   *
   * @return 可用的TCP端口
   */
  int findAvailableTcpPortInternal() {
    int searchCounter = 0;

    int candidatePort;
    do {
      ++searchCounter;
      Assert.state(searchCounter <= MAX_ATTEMPTS,
          () -> String.format("Could not find an available TCP port in the range [%d, %d] after %d attempts", PORT_RANGE_MIN, PORT_RANGE_MAX,
              MAX_ATTEMPTS));
      candidatePort = PORT_RANGE_MIN + RANDOM.nextInt(PORT_RANGE_PLUS_ONE);
    } while (!this.isPortAvailable(candidatePort));

    return candidatePort;
  }

  /**
   * 判断端口是否可用
   *
   * @param port 端口
   * @return true：可用；false：不可用
   */
  boolean isPortAvailable(int port) {
    try {
      ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"));
      serverSocket.close();
      return true;
    } catch (Exception var3) {
      return false;
    }
  }
}

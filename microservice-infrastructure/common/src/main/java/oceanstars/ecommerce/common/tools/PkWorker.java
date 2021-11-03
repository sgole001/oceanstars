package oceanstars.ecommerce.common.tools;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.constant.CommonMessageConstant;
import oceanstars.ecommerce.common.exception.SystemException;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 分布式物理主键工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 6:55 下午
 */
@SuppressWarnings("all")
public class PkWorker {

  /**
   * 设置一个时间初始值
   */
  private final static long twepoch = 1603641600000L;

  /**
   * 机器id所占的位数(根据ip分配策略对应不同的位数：当前POD网段CIDR:XXX.XXX.0.0./16)
   */
  private final static long workerIdBits = 16L;

  /**
   * 支持的最大机器id，结果是65535 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
   */
  private final static long maxWorkerId = ~(-1L << workerIdBits);

  /**
   * 序列在id中占的位数(为保证机器ID不重复需要位数保证，需要牺牲并发处理能力)
   */
  private final static long sequenceBits = 6L;

  /**
   * 机器ID向左移6位
   */
  private final static long workerIdShift = sequenceBits;

  /**
   * 时间截向左移22位(16+6)
   */
  private final static long timestampLeftShift = sequenceBits + workerIdBits;

  /**
   * 生成序列的掩码，这里为63 (0b111111=0x77=63)
   */
  private final static long sequenceMask = ~(-1L << sequenceBits);

  /**
   * 工作机器ID(0~65535)
   */
  private long workerId;

  /**
   * 毫秒内序列(0~63)
   */
  private long sequence = 0L;

  /**
   * 上次生成ID的时间截
   */
  private Long lastTimestamp = -1L;

  /**
   * 日志管理器
   */
  private static Logger logger = LogManager.getLogger(PkWorker.class.getName());

  /**
   * 单例模式对象
   */
  private volatile static PkWorker pkWorker;

  private PkWorker() {
  }

  /**
   * 双检锁/双重校验锁（DCL，即 double-checked locking）
   *
   * @return 单例模式对象
   */
  public static PkWorker build() {

    if (pkWorker == null) {
      synchronized (PkWorker.class) {
        if (pkWorker == null) {
          pkWorker = new PkWorker();
          // 构建工作ID
          pkWorker.workerId = pkWorker.generateWorkerId();
        }
      }
    }

    return pkWorker;
  }

  /**
   * 设定工作ID
   *
   * @param workerId 工作ID
   * @return PK生成器
   */
  public PkWorker workerId(long workerId) {

    if (workerId > maxWorkerId || workerId < 0) {
      throw new SystemException(CommonMessageConstant.MSG_COM_00002, maxWorkerId);
    }
    pkWorker.workerId = workerId;
    return pkWorker;
  }

  /**
   * 获取PK值（线程安全）
   *
   * @return PK值
   */
  public synchronized long nextId() {

    // 获取当前时间戳
    long timestamp = this.timeGen();

    // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (timestamp < lastTimestamp) {
      throw new SystemException(CommonMessageConstant.MSG_COM_00003, lastTimestamp - timestamp);
    }

    // 如果是同一时间生成的，则进行毫秒内序列
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      // 毫秒内序列溢出
      if (sequence == 0) {
        // 阻塞到下一个毫秒,获得新的时间戳
        timestamp = this.tilNextMillis(lastTimestamp);
      }
    }
    // 时间戳改变，毫秒内序列重置
    else {
      sequence = 0L;
    }

    // 上次生成ID的时间截
    lastTimestamp = timestamp;

    // 移位并通过或运算拼到一起组成64位的ID
    return ((timestamp - twepoch) << timestampLeftShift)
        | (workerId << workerIdShift)
        | sequence;
  }

  /**
   * 生产工作ID
   *
   * @return 工作ID
   * @throws UnknownHostException
   */
  public static long generateWorkerId() {

    try {

      // 获取POD IP
      String hostAddress = System.getenv(CommonConstant.ENV_POD_IP);

      if (StringUtils.isBlank(hostAddress)) {
        // 获取本地服务器IP
        hostAddress = Inet4Address.getLocalHost().getHostAddress();
      }

      // 分割IP信息
      final String[] ips = StringUtils.split(hostAddress, ".");

      return (Long.parseLong(ips[0]) << 24
          | Long.parseLong(ips[1]) << 16
          | Long.parseLong(ips[2]) << 8
          | Long.parseLong(ips[3]))
          & maxWorkerId;

//      return Arrays.stream(ips).sum() % (maxWorkerId + 1);

    } catch (UnknownHostException e) {

      // 警告：生成WorkerId过程中，获取IP失败
      logger.warn(MessageUtil.ACCESSOR.getMessage(CommonMessageConstant.MSG_COM_00004));

      return RandomUtils.nextLong(0, maxWorkerId);
    }
  }

  /**
   * 阻塞到下一个毫秒，直到获得新的时间戳
   *
   * @param lastTimestamp 上次生成ID的时间截
   * @return 当前时间戳
   */
  protected long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  /**
   * 返回以毫秒为单位的当前时间
   *
   * @return 当前时间(毫秒)
   */
  protected long timeGen() {
    return System.currentTimeMillis();
  }
}

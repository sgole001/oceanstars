package oceanstars.ecommerce.common.constant;

import java.time.ZoneId;

/**
 * 公共常量接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 3:30 下午
 */
public interface CommonConstant {

  /**
   * 成功状态
   */
  Integer SUCCESS = 1;

  /**
   * 失败状态
   */
  Integer FAILURE = 0;

  /**
   * 属性值_服务名
   */
  String PROPERTY_KEY_APPLICATION_NAME = "spring.application.name";

  /**
   * Squid默认设置forwarded_for项默认是为on，如果 forwarded_for设成了 off　则：X-Forwarded-For： unknown
   */
  String SQUID_OFF_IP_ADDRESS = "unknown";

  /**
   * 回送IP地址
   */
  String LOOPBACK_IP_ADDRESS = "127.0.0.1";

  /**
   * JWT HTTP Header键名
   */
  String KEY_JWT_HTTP_HEADER = "token";

  /**
   * 调用链追踪ID
   */
  String KEY_TRACE = "traceId";

  /**
   * JWT刷新用Token RedisKey后缀名
   */
  String KEY_SUFFIX_JWT_REFRESH_TOKEN = "_Refresh_Token";

  /**
   * 逗号分隔符
   */
  String SEPARATOR_COMMA = ",";

  /**
   * 连字符分隔符
   */
  String SEPARATOR_HYPHEN = "-";

  /**
   * 箭头分隔符
   */
  String SEPARATOR_ARROW = "->";

  /**
   * 前中括号
   */
  String PREFIX_BRACKETS = "[";

  /**
   * 后中括号
   */
  String SUFFIX_BRACKETS = "]";

  /**
   * 环境变量: POD映射IP地址
   */
  String ENV_POD_IP = "POD_IP";

  /**
   * 默认时区：东八区
   */
  ZoneId DEFAULT_ZONE = ZoneId.of("UTC+8");
}

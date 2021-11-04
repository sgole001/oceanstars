package oceanstars.ecommerce.infrastructure.kafka.interceptor;

import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.session.SessionThreadLocal;
import oceanstars.ecommerce.common.session.Sessions;
import oceanstars.ecommerce.common.tools.JsonUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.lang.NonNull;

/**
 * 消费端拦截：解析头部session信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:38 下午
 */
public class Consumer4SessionInterceptor<K, V> implements RecordInterceptor<K, V> {

  @Override
  public ConsumerRecord<K, V> intercept(@NonNull ConsumerRecord<K, V> consumerRecord) {
    return consumerRecord;
  }

  @Override
  public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> consumerRecord, @NonNull Consumer<K, V> consumer) {

    // 获取头部Session信息
    final Iterable<Header> headerIterable = consumerRecord.headers().headers(Sessions.SESSION_KEY);

    if (null == headerIterable) {
      return consumerRecord;
    }

    // 获取Session Json字符串信息
    final String sessionsValue = new String(headerIterable.iterator().next().value());
    // Session对象Json转换
    final Sessions sessions = JsonUtil.parse(sessionsValue, Sessions.class);

    // kafka多线程消费，线程池使用每次线程消费覆盖，由于线程数有限，不手动回收内存。
    SessionThreadLocal.setSessions(sessions);
    // 日志全局加入跟踪ID
    ThreadContext.put(CommonConstant.KEY_TRACE, sessions.getTraceId());

    return consumerRecord;
  }
}

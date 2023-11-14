package oceanstars.ecommerce.infrastructure.kafka.interceptor;

import java.util.Iterator;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.session.SessionThreadLocal;
import oceanstars.ecommerce.common.session.Sessions;
import oceanstars.ecommerce.common.tools.SerializeUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.lang.NonNull;

/**
 * 消息监听器容器拦截：解析头部session信息
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 11:38 下午
 */
public class Record4SessionInterceptor<K, V> implements RecordInterceptor<K, V> {

  @Override
  public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> consumerRecord, @NonNull Consumer<K, V> consumer) {

    // 获取头部Session信息
    final Iterator<Header> headers = consumerRecord.headers().headers(Sessions.SESSION_KEY).iterator();

    if (headers.hasNext()) {

      // 反序列化获取Session对象
      final Sessions sessions = SerializeUtil.deserialize(headers.next().value(), Sessions.class);

      // kafka多线程消费，线程池使用每次线程消费覆盖，由于线程数有限，不手动回收内存。
      SessionThreadLocal.setSessions(sessions);
      // 日志全局加入跟踪ID
      ThreadContext.put(CommonConstant.KEY_TRACE, sessions.getTraceId());
    }

    return consumerRecord;
  }
}

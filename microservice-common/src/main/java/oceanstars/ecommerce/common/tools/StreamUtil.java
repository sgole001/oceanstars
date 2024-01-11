package oceanstars.ecommerce.common.tools;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 流工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:32 下午
 */
public class StreamUtil {

  /**
   * 构造函数：私有化，显式表示静态工具类
   */
  private StreamUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * 实体对象去重过滤
   *
   * @param keyExtractor 去重判定Key
   * @param <T>          去重对象类型
   * @return 判定结果
   */
  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> {
      if (null != keyExtractor.apply(t)) {
        return seen.add(keyExtractor.apply(t));
      }
      return false;
    };
  }
}

package oceanstars.ecommerce.common.tools;

import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import java.util.List;
import oceanstars.ecommerce.common.data.databind.gson.LocalDateTimeSerializer;

/**
 * JSON数据解析与转换工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 5:58 下午
 */
public class JsonUtil {

  /**
   * Add a private constructor to hide the implicit public one.
   */
  private JsonUtil() {
  }

  /**
   * 解析JSON字符串转换为POJO对象
   *
   * @param jsonStr JSON字符串
   * @param type    POJO对象类型
   * @param <T>     对象类型范型
   * @return JSON转换后POJO对象
   */
  public static <T> T parse(final String jsonStr, final Class<T> type) {

    // 解析Json字符串转换为POJO
    // PS: 使用Ali的fastjson，性能考虑优与Gson
    return JSON.parseObject(jsonStr, type);
  }

  /**
   * 解析JSON字符串转换为POJO对象数组
   *
   * @param jsonStr JSON字符串
   * @param type    POJO对象类型
   * @param <T>     对象类型范型
   * @return JSON转换后POJO对象数组
   */
  public static <T> List<T> parseArray(final String jsonStr, final Class<T> type) {

    // 解析Json字符串转换为POJO
    // PS: 使用Ali的fastjson，性能考虑优与Gson
    return JSON.parseArray(jsonStr, type);
  }

  /**
   * POJO对象转换成JSON字符串
   *
   * @param object POJO对象
   * @param <T>    对象类型范型
   * @return 转换后JSON字符串
   */
  public static <T> String toString(final T object) {
    // POJO对象转换成JSON字符串
    return toString(object, new GsonBuilder());
  }

  /**
   * POJO对象转换成JSON字符串(包含LocalDateTime类型)
   *
   * @param object POJO对象
   * @param <T>    对象类型范型
   * @return 转换后JSON字符串
   */
  public static <T> String toStringWithLocalDateTime(final T object) {

    // 初始化Gson构建器
    final GsonBuilder gsonBuilder = new GsonBuilder();
    // 注册LocalDateTime类型序列化接口
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

    // POJO对象转换成JSON字符串
    return toString(object, gsonBuilder);
  }

  /**
   * POJO对象转换成JSON字符串
   *
   * @param object      POJO对象
   * @param <T>         对象类型范型
   * @param gsonBuilder Gson构建器
   * @return 转换后JSON字符串
   */
  public static <T> String toString(final T object, final GsonBuilder gsonBuilder) {

    // 构建Google工具类
    final Gson gson = gsonBuilder.create();

    // POJO对象转换成JSON字符串
    return gson.toJson(object);
  }

  /**
   * POJO对象转换成JSON字符串
   *
   * @param object POJO对象
   * @param <T>    对象类型范型
   * @return 转换后JSON字符串
   */
  public static <T> String toStringDisableHtmlEscaping(final T object) {

    // Google工具类
    final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    // POJO对象转换成JSON字符串
    return gson.toJson(object);
  }
}

package oceanstars.ecommerce.common.tools;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

/**
 * 属性配置工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:03 下午
 */
public class PropertyUtil {

  /**
   * 属性绑定封装类
   */
  public static final Binder BINDER = Binder.get(ApplicationContextProvider.getBean(Environment.class));

  /**
   * 根据K,V字符串构建Properties对象
   *
   * @param props K,V字符串集合
   * @return Properties对象
   */
  public static Properties buildPropertiesFromStr(final List<String> props) {

    if (CollectionUtils.isEmpty(props)) {
      return (Properties) Collections.emptyMap();
    }

    // 属性对象
    Properties properties = new Properties();

    // 遍历属性信息
    for (String prop : props) {

      // 获取属性分割符索引
      int splitIndex = prop.indexOf("=");

      if (splitIndex > 0) {
        // 添加属性
        properties.put(prop.substring(0, splitIndex), prop.substring(splitIndex + 1));
      }
    }

    if (properties.size() == 0) {
      return (Properties) Collections.emptyMap();
    }

    return properties;
  }
}

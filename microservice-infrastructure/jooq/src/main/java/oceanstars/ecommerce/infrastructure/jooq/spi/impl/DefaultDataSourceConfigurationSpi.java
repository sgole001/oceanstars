package oceanstars.ecommerce.infrastructure.jooq.spi.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import oceanstars.ecommerce.common.exception.SystemException;
import oceanstars.ecommerce.infrastructure.jooq.constant.JooqMessageConstant;
import oceanstars.ecommerce.infrastructure.jooq.spi.DataSourceConfigurationSpi;
import org.jooq.Configuration;
import org.jooq.impl.DefaultDSLContext;

/**
 * 获取数据源配置SPI默认实现
 *
 * @author Clover
 * @version 1.0.0
 * @since 2023/1/27 15:26
 */
public class DefaultDataSourceConfigurationSpi implements DataSourceConfigurationSpi {

  @Override
  public Configuration getConfiguration(String pkg, Map<String, DefaultDSLContext> dsl) {

    if (null != dsl) {

      // 默认Jooq生成代码包名正则表达式
      String pattern = null;

      for (Entry<String, DefaultDSLContext> dslContextEntry : dsl.entrySet()) {
        // 构建包名正则表达式
        pattern = ".*\\." + dslContextEntry.getKey() + ".repository.generate\\..*";

        if (Pattern.matches(pattern, pkg)) {
          return dslContextEntry.getValue().configuration();
        }
      }
    }

    // 未匹配到[{0}]对应的数据源配置信息，系统异常处理。
    throw new SystemException(JooqMessageConstant.MSG_JOOQ_SYS_00000, pkg);
  }
}

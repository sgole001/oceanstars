package oceanstars.ecommerce.common.messages;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.CollectionUtils;

/**
 * 国际化配置类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:56 下午
 */
@Configuration(proxyBeanMethods = false)
public class MessageSourceConfig {

  /**
   * 资源文件路径匹配规则
   */
  private static final Pattern PATTERN = Pattern.compile("^.*(/i18n/.*/messages)");

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(MessageSourceConfig.class.getName());

  @Primary
  @Bean(name = "advancedMessageSource")
  public MessageSource messageSourceConfig() {

    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setCacheSeconds(-1);
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    this.buildBaseNames(messageSource);
    return messageSource;
  }

  @Bean(name = "messageAccessor")
  public MessageSourceAccessor messageAccessor(ReloadableResourceBundleMessageSource advancedMessageSource) {
    advancedMessageSource.setResourceLoader(new PathMatchingResourcePatternResolver());
    return new MessageSourceAccessor(advancedMessageSource, LocaleContextHolder.getLocale());
  }

  /**
   * 构建消息配置BaseName信息
   *
   * @param messageSource 消息管理对象
   */
  private void buildBaseNames(ReloadableResourceBundleMessageSource messageSource) {

    // 路径匹配资源加载器
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();

    // BaseName列表
    Set<String> baseNames = new HashSet<>();

    try {
      // 资源加载
      Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/i18n/**/messages**");
      // 资源路径
      String resourcePath;

      for (Resource resource : resources) {
        // 获取资源路径
        resourcePath = resource.getURI().toString();

        if (StringUtils.isBlank(resourcePath)) {
          resourcePath = resource.getURL().toString();
        }

        // 非properties文件剔除
        if (StringUtils.isBlank(resourcePath) || !resourcePath.endsWith(".properties")) {
          continue;
        }

        // 文件路径解析
        Matcher matcher = PATTERN.matcher(resourcePath);

        if (matcher.find()) {

          // 文件路径解析（file:....）
          String baseName = matcher.group();

          // 提取公共目录
          baseNames.add(baseName);
        }
      }

      if (CollectionUtils.isEmpty(baseNames)) {
        // 默认BaseName
        messageSource.setBasenames("classpath:/i18n/message");
      } else {
        messageSource.setBasenames(baseNames.toArray(new String[0]));
      }

    } catch (Exception ex) {
      // ...
      logger.error(ex);
    }
  }
}

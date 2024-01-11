package oceanstars.ecommerce.common.tools;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

/**
 * YAML配置文件读取工厂类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 7:33 下午
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

  @Override
  @NonNull
  public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {

    // 获取资源名
    String resourceName = Optional.ofNullable(name).orElse(encodedResource.getResource().getFilename());

    // Yaml资源加载器
    YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
    // 获取Yaml的合法扩展名
    String[] fileExtensions = yamlPropertySourceLoader.getFileExtensions();

    // 获取Yaml资源
    for (String fileExt : fileExtensions) {
      if (resourceName.endsWith(fileExt)) {
        List<PropertySource<?>> yamlSources = new YamlPropertySourceLoader().load(resourceName, encodedResource.getResource());
        return yamlSources.get(0);
      }
    }

    // 返回空的Properties
    return new PropertiesPropertySource(resourceName, new Properties());
  }
}

package oceanstars.ecommerce.infrastructure.jooq.strategy;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * 代码生成策略
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 1:48 下午
 */
public class OceanStarsGeneratorStrategy extends DefaultGeneratorStrategy {

  @Override
  public String getJavaClassName(Definition definition, Mode mode) {

    String result = super.getJavaClassName(definition, mode);

    if (mode == Mode.POJO) {
      result = result.concat("Pojo");
    }
    return result;
  }
}

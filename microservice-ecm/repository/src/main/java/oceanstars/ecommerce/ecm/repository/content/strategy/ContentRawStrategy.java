package oceanstars.ecommerce.ecm.repository.content.strategy;

import oceanstars.ecommerce.common.domain.entity.ValueObject;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

/**
 * 内容原生信息策略接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/23 15:33
 */
public interface ContentRawStrategy {

  /**
   * 保存内容原生信息
   *
   * @param raw 内容原始信息
   */
  void save(final ValueObject raw);

  /**
   * 构建内容原始信息值对象
   *
   * @param raw 原始信息
   * @return 内容原始信息值对象
   */
  ValueObject buildRawValueObject(final Object raw);

  /**
   * 确定原始数据表
   *
   * @return 原始数据表
   */
  TableImpl<?> determineRawTable();

  /**
   * Jooq数据库记录对象转换为POJO
   *
   * @param record 记录
   * @return POJO
   */
  Object recordIntoPojo(final Record record);
}

package oceanstars.ecommerce.ecm.repository.content.strategy.impl;

import oceanstars.ecommerce.common.domain.entity.ValueObject;
import oceanstars.ecommerce.ecm.constant.enums.EcmEnums.ContentType;
import oceanstars.ecommerce.ecm.repository.content.strategy.ContentRawStrategy;
import org.jooq.Record;
import org.jooq.impl.TableImpl;

/**
 * 内容原始数据处理策略上下文
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/1/17 17:51
 */
public class ContentRawStrategyContext {

  /**
   * 内容原生实体策略接口
   */
  private ContentRawStrategy strategy;

  /**
   * 构造函数: 根据内容类型选择不同的策略
   *
   * @param contentType 内容类型
   */
  public ContentRawStrategyContext(final ContentType contentType) {

    if (null == contentType) {
      throw new IllegalArgumentException("不支持的内容类型");
    }

    switch (contentType) {
      case FUNCTION_MENU:
        this.strategy = new FunctionMenuStrategy();
        break;
    }
  }

  /**
   * 保存内容原始数据
   *
   * @param raw 内容原始数据值对象
   */
  public void executeSave(final ValueObject raw) {
    this.strategy.save(raw);
  }

  /**
   * 构建内容原始数据值对象
   *
   * @param raw 原始数据
   * @return 内容原始数据值对象
   */
  public ValueObject executeBuildRawValueObject(final Object raw) {
    return this.strategy.buildRawValueObject(raw);
  }

  /**
   * 确定原始数据表
   *
   * @return 原始数据表
   */
  public TableImpl<?> executeDetermineRawTable() {
    return this.strategy.determineRawTable();
  }

  /**
   * 转换Jooq数据库记录对象为POJO对象
   *
   * @param record 记录
   * @return POJO
   */
  public Object executeRecordIntoPojo(final Record record) {
    return this.strategy.recordIntoPojo(record);
  }
}

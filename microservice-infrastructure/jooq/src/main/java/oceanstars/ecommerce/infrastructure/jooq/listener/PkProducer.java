package oceanstars.ecommerce.infrastructure.jooq.listener;

import oceanstars.ecommerce.common.tools.PkWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.RecordType;

/**
 * 监听数据插入，自动注入PK
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 2:08 下午
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class PkProducer implements RecordListener {

  /**
   * 日志管理器
   */
  private static Logger logger = LogManager.getLogger(PkProducer.class.getName());

  /**
   * PK的列名
   */
  private static String COLUMN_NAME_PK = "id";

  @Override
  public void insertStart(RecordContext recordContext) {

    // 获取数据类型
    RecordType<?> recordType = recordContext.recordType();

    // 获取PK字段
    Field<Long> fieldId = (Field<Long>) recordType.field(COLUMN_NAME_PK);

//    // 获取WorkerId
//    final long workerId = PkWorker.generateWorkerId();

    // 获取数据集
    Record[] records = recordContext.batchRecords();

    // 构建分布式物理主键工具类
    final PkWorker pkWorker = PkWorker.build();

    for (Record record : records) {

      if (record.get(fieldId) != null) {
        continue;
      }

      // 生成PK
      final long pk = pkWorker.nextId();

      record.with(fieldId, pk);
    }
  }
}
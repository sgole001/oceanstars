package oceanstars.ecommerce.infrastructure.jooq.listener;

import java.time.LocalDateTime;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.tools.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordType;
import org.jooq.impl.DefaultRecordListener;

/**
 * 监听数据插入更新，自动注入审计字段
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 2:00 下午
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AuditProducer extends DefaultRecordListener {

  /**
   * 日志管理器
   */
  private static Logger logger = LogManager.getLogger(AuditProducer.class.getName());

  /**
   * 审计字段创建时间列名
   */
  private static String COLUMN_NAME_AUDIT_CREATE_AT = "create_at";

  /**
   * 审计字段创建者列名
   */
  private static String COLUMN_NAME_AUDIT_CREATE_BY = "create_by";

  /**
   * 审计字段更新时间列名
   */
  private static String COLUMN_NAME_AUDIT_UPDATE_AT = "update_at";

  /**
   * 审计字段更新者列名
   */
  private static String COLUMN_NAME_AUDIT_UPDATE_BY = "update_by";

  @Override
  public void insertStart(RecordContext recordContext) {

    // 获取数据类型
    RecordType<?> recordType = recordContext.recordType();

    // 获取审计字段创建时间字段
    Field<LocalDateTime> fieldCreateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_CREATE_AT);
    // 获取审计字段创建者字段
    Field<String> fieldCreateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_CREATE_BY);
    // 获取审计字段更新时间字段
    Field<LocalDateTime> fieldUpdateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_AT);
    // 获取审计字段更新者字段
    Field<String> fieldUpdateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_BY);

    // 获取数据集
    Record[] records = recordContext.batchRecords();

    // 获取当前系统时间
    final LocalDateTime now = LocalDateTime.now(CommonConstant.DEFAULT_ZONE);

    // 获取当前用户ID
    String userId;
    // 用户为授权登录时，使用跟踪ID
    if (null == SessionUtil.getSessions().getUserId()) {
      userId = SessionUtil.getSessions().getTraceId();
    } else {
      userId = SessionUtil.getSessions().getUserId().toString();
    }

    for (Record record : records) {

      record.with(fieldCreateAt, now);
      record.with(fieldCreateBy, userId);
      record.with(fieldUpdateAt, now);
      record.with(fieldUpdateBy, userId);
    }
  }

  @Override
  public void updateStart(RecordContext recordContext) {

    // 获取数据类型
    RecordType<?> recordType = recordContext.recordType();

    // 获取审计字段更新时间字段
    Field<LocalDateTime> fieldUpdateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_AT);
    // 获取审计字段更新者字段
    Field<String> fieldUpdateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_BY);

    // 获取数据集
    Record[] records = recordContext.batchRecords();

    // 获取当前系统时间
    final LocalDateTime now = LocalDateTime.now(CommonConstant.DEFAULT_ZONE);

    // 获取当前用户ID
    String userId;
    // 用户为授权登录时，使用跟踪ID
    if (null == SessionUtil.getSessions().getUserId()) {
      userId = SessionUtil.getSessions().getTraceId();
    } else {
      userId = SessionUtil.getSessions().getUserId().toString();
    }

    for (Record record : records) {

      record.with(fieldUpdateAt, now);
      record.with(fieldUpdateBy, userId);
    }
  }
}

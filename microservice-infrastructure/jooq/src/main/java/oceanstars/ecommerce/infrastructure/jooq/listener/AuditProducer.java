package oceanstars.ecommerce.infrastructure.jooq.listener;

import java.time.LocalDateTime;
import oceanstars.ecommerce.common.constant.CommonConstant;
import oceanstars.ecommerce.common.session.BaseSessionAttribute;
import oceanstars.ecommerce.common.tools.SessionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.RecordType;

/**
 * 监听数据插入更新，自动注入审计字段
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 2:00 下午
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AuditProducer implements RecordListener {

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
    final RecordType<?> recordType = recordContext.recordType();

    // 获取审计字段创建时间字段
    final Field<LocalDateTime> fieldCreateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_CREATE_AT);
    // 获取审计字段创建者字段
    final Field<String> fieldCreateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_CREATE_BY);
    // 获取审计字段更新时间字段
    final Field<LocalDateTime> fieldUpdateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_AT);
    // 获取审计字段更新者字段
    final Field<String> fieldUpdateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_BY);

    // 获取数据集
    final Record[] records = recordContext.batchRecords();

    // 获取当前系统时间
    final LocalDateTime now = LocalDateTime.now(CommonConstant.DEFAULT_ZONE);

    // 获取审计ID
    String auditId;
    // 获取Session信息
    final BaseSessionAttribute sessionAttribute = SessionUtil.getSessionAttribute();
    // 用户为授权登录时，使用跟踪ID
    if (null == sessionAttribute.getUserId()) {
      auditId = sessionAttribute.getTraceId();
    } else {
      auditId = sessionAttribute.getUserId();
    }

    for (Record record : records) {
      record.with(fieldCreateAt, now);
      record.with(fieldCreateBy, auditId);
      record.with(fieldUpdateAt, now);
      record.with(fieldUpdateBy, auditId);
    }
  }

  @Override
  public void updateStart(RecordContext recordContext) {

    // 获取数据类型
    final RecordType<?> recordType = recordContext.recordType();

    // 获取审计字段更新时间字段
    final Field<LocalDateTime> fieldUpdateAt = (Field<LocalDateTime>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_AT);
    // 获取审计字段更新者字段
    final Field<String> fieldUpdateBy = (Field<String>) recordType.field(COLUMN_NAME_AUDIT_UPDATE_BY);

    // 获取数据集
    final Record[] records = recordContext.batchRecords();

    // 获取当前系统时间
    final LocalDateTime now = LocalDateTime.now(CommonConstant.DEFAULT_ZONE);

    // 获取审计ID
    String auditId;
    // 获取Session信息
    final BaseSessionAttribute sessionAttribute = SessionUtil.getSessionAttribute();
    // 用户为授权登录时，使用跟踪ID
    if (null == sessionAttribute.getUserId()) {
      auditId = sessionAttribute.getTraceId();
    } else {
      auditId = sessionAttribute.getUserId();
    }

    for (Record record : records) {
      record.with(fieldUpdateAt, now);
      record.with(fieldUpdateBy, auditId);
    }
  }
}

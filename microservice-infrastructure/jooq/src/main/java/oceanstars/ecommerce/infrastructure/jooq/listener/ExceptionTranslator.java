package oceanstars.ecommerce.infrastructure.jooq.listener;

import java.io.Serial;
import java.util.Objects;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

/**
 * 数据库操作异常监听
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 2:01 下午
 */
public class ExceptionTranslator implements ExecuteListener {

  @Serial
  private static final long serialVersionUID = 4370839189184446301L;

  private static final String TRANSLATE_TASK = "使用JOOQ连接数据库";

  @Override
  public void exception(ExecuteContext context) {
    SQLDialect dialect = context.configuration().dialect();
    SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
    context.exception(translator.translate(TRANSLATE_TASK, context.sql(), Objects.requireNonNull(context.sqlException())));
  }
}

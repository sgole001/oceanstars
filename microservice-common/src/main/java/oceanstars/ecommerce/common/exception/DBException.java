package oceanstars.ecommerce.common.exception;

import java.io.Serial;
import oceanstars.ecommerce.common.constant.IEnum;

/**
 * 数据库异常类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:49 下午
 */
public class DBException extends BaseException {

  @Serial
  private static final long serialVersionUID = -3358973638956163044L;

  public DBException(String errorCode, Object... params) {
    super(errorCode, params);
  }

  public DBException(String errorCode, Throwable cause, Object... params) {
    super(errorCode, cause, params);
  }

  public DBException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public DBException(String errorCode) {
    super(errorCode);
  }

  public DBException(IEnum<String, String, ?> errorCode, Object... params) {
    super(errorCode.key(), params);
  }

  public DBException(IEnum<String, String, ?> errorCode, Throwable cause, Object... params) {
    super(errorCode.key(), cause, params);
  }

  public DBException(IEnum<String, String, ?> errorCode, Throwable cause) {
    super(errorCode.key(), cause);
  }

  public DBException(IEnum<String, String, ?> errorCode) {
    super(errorCode.key());
  }

  public DBException(Throwable cause) {
    super(cause);
  }
}

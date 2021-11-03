package oceanstars.ecommerce.common.exception;

import java.io.Serial;

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

  public DBException(Throwable cause) {
    super(cause);
  }
}

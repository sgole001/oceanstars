package oceanstars.ecommerce.common.exception;

import java.io.Serial;

/**
 * 系统异常类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:49 下午
 */
public class SystemException extends BaseException {

  @Serial
  private static final long serialVersionUID = 2980776018448530342L;

  public SystemException(String errorCode, Object... params) {
    super(errorCode, params);
  }

  public SystemException(String errorCode, Throwable cause, Object... params) {
    super(errorCode, cause, params);
  }

  public SystemException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public SystemException(String errorCode) {
    super(errorCode);
  }

  public SystemException(Throwable cause) {
    super(cause);
  }

}
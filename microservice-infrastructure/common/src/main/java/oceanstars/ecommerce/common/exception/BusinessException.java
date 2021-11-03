package oceanstars.ecommerce.common.exception;

import java.io.Serial;

/**
 * 业务异常类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:49 下午
 */
public class BusinessException extends BaseException {

  @Serial
  private static final long serialVersionUID = -3018027111689249481L;

  public BusinessException(String errorCode, Object... params) {
    super(errorCode, params);
  }

  public BusinessException(String errorCode, Throwable cause, Object... params) {
    super(errorCode, cause, params);
  }

  public BusinessException(String errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public BusinessException(String errorCode) {
    super(errorCode);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }
}

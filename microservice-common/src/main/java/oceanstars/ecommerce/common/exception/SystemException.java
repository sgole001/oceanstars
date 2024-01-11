package oceanstars.ecommerce.common.exception;

import java.io.Serial;
import oceanstars.ecommerce.common.constant.IEnum;

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

  public SystemException(IEnum<String, String, ?> errorCode, Object... params) {
    super(errorCode.key(), params);
  }

  public SystemException(IEnum<String, String, ?> errorCode, Throwable cause, Object... params) {
    super(errorCode.key(), cause, params);
  }

  public SystemException(IEnum<String, String, ?> errorCode, Throwable cause) {
    super(errorCode.key(), cause);
  }

  public SystemException(IEnum<String, String, ?> errorCode) {
    super(errorCode.key());
  }

  public SystemException(Throwable cause) {
    super(cause);
  }

}
package oceanstars.ecommerce.common.exception;

import java.io.Serial;
import oceanstars.ecommerce.common.tools.MessageUtil;

/**
 * 异常类基类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:39 下午
 */
public class BaseException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 800240472523038334L;

  /**
   * 错误码
   */
  protected String errorCode;

  /**
   * 错误信息动态参数
   */
  protected Object[] params;

  public BaseException(String errorCode, Object... params) {
    super(buildMessage(errorCode, params));
    this.errorCode = errorCode;
    this.params = params;
  }

  public BaseException(String errorCode, Throwable cause, Object... params) {
    super(buildMessage(errorCode, params), cause);
    this.errorCode = errorCode;
    this.params = params;
  }

  public BaseException(String errorCode, Throwable cause) {
    super(buildMessage(errorCode), cause);
    this.errorCode = errorCode;
  }

  public BaseException(String errorCode) {
    super(buildMessage(errorCode));
    this.errorCode = errorCode;
  }

  public BaseException(Throwable cause) {
    super(cause);
  }

  public String getErrorCode() {
    return this.errorCode;
  }

  /**
   * 构建错误信息
   *
   * @param errorCode 错误代码
   * @param params    错误信息动态参数
   * @return 错误信息
   */
  protected static String buildMessage(String errorCode, Object... params) {

    // 构建错误信息
    String errorMessage;

    try {

      if (params == null || params.length == 0) {
        errorMessage = MessageUtil.ACCESSOR.getMessage(errorCode);
      } else {
        errorMessage = MessageUtil.ACCESSOR.getMessage(errorCode, params);
      }

    } catch (Exception e) {
      errorMessage = errorCode;
    }

    return errorMessage;
  }
}

package oceanstars.ecommerce.common.exception;

import oceanstars.ecommerce.common.constant.CommonMessageConstant;
import oceanstars.ecommerce.common.messages.MessageSourceConfig;
import oceanstars.ecommerce.common.restful.RestResponseMessage;
import oceanstars.ecommerce.common.tools.MessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 4:50 下午
 */
@ControllerAdvice()
@AutoConfigureAfter(value = MessageSourceConfig.class)
public class CommonExceptionHandler {

  /**
   * 日志管理器
   */
  private final Logger logger = LogManager.getLogger(CommonExceptionHandler.class.getName());

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public RestResponseMessage handle(Exception e) {

    // 响应消息实例
    final RestResponseMessage message;

    if (e instanceof BaseException baseException) {

      // 系统异常
      message = switch (e) {
        case SystemException systemException -> new RestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        // 数据库异常
        case DBException dbException -> new RestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        // 业务异常
        case BusinessException businessException -> new RestResponseMessage(HttpStatus.OK.toString());
        // 其他
        default -> new RestResponseMessage(HttpStatus.OK.toString());
      };

      // 异常错误码
      message.setStatus(baseException.getErrorCode());
      // 异常信息
      message.setMessage(baseException.getMessage());

      logger.error(e.getMessage(), e);
    } else {

      message = new RestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());

      // 异常错误码
      message.setStatus(CommonMessageConstant.MSG_COM_00001);
      // 异常信息
      message.setMessage(MessageUtil.ACCESSOR.getMessage(CommonMessageConstant.MSG_COM_00001));

      logger.error(e.getMessage(), e);
    }

    return message;
  }
}

package oceanstars.ecommerce.common.tools;

import oceanstars.ecommerce.common.spring.ApplicationContextProvider;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * 消息工具类
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 6:00 下午
 */
public class MessageUtil {

  /**
   * MessageSource的封装类
   */
  public static final MessageSourceAccessor ACCESSOR = (MessageSourceAccessor) ApplicationContextProvider.getBean("messageAccessor");
}

package oceanstars.ecommerce.infrastructure.shiro.constant;

import oceanstars.ecommerce.common.constant.CommonConstant;

/**
 * Shiro常量接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:33 上午
 */
public interface ShiroConstant extends ShiroMessageConstant {

  /**
   * JWT认证HTTP头部属性名
   */
  String JWT_AUTH_HTTP_HEAD = CommonConstant.KEY_JWT_HTTP_HEADER;

  /**
   * Shiro针对Restful拦截过滤链分割符正则
   */
  String REST_CHAIN_PATTERN_SEPARATOR_REGULAR = "\\|\\|\\|";

  /**
   * Shiro针对Restful拦截过滤链分割符（URI|||Rest Method eg:/users|||Post）
   */
  String REST_CHAIN_PATTERN_SEPARATOR = "|||";
}

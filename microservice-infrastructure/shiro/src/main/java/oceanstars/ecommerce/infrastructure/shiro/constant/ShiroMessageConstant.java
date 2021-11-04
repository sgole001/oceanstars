package oceanstars.ecommerce.infrastructure.shiro.constant;

/**
 * Shiro服务消息码常量接口
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/4 11:33 上午
 */
public interface ShiroMessageConstant {

  /**
   * 系统认证用户失败。
   */
  String MSG_SHIRO_SYS_00000 = "MSG_SHIRO_SYS_00000";

  /**
   * 重载Shiro拦截链信息发生异常，未能获取Shiro拦截器对象！
   */
  String MSG_SHIRO_SYS_00001 = "MSG_SHIRO_SYS_00001";

  /**
   * 重载Shiro拦截链信息发生异常！
   */
  String MSG_SHIRO_SYS_00002 = "MSG_SHIRO_SYS_00002";

  /**
   * 系统未配置Shiro过滤规则。
   */
  String MSG_SHIRO_BIZ_00000 = "MSG_SHIRO_BIZ_00000";
}

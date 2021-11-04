package oceanstars.ecommerce.infrastructure.druid.configuration.bean;

/**
 * Druid监控服务配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 10:27 下午
 */
public class StatViewServletConfBean {

  /**
   * 配置键值名常量
   */
  public enum ConfigKey {

    // 登陆名
    LOGIN_USER_NAME("loginUsername"),
    // 登陆密码
    LOGIN_PASSWORD("loginPassword"),
    // 白名单
    ALLOW("allow"),
    // 黑名单
    DENY("deny"),
    // 重置标志位
    RESET_ENABLE("resetEnable");

    /**
     * 键值名
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param name 枚举显示名
     */
    ConfigKey(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  /**
   * Druid监控服务URL
   */
  private String urlPattern = "/druid/*";

  /**
   * 登录用户名
   */
  private String loginUsername;

  /**
   * 登录密码
   */
  private String loginPassword;

  /**
   * 访问控制:允许
   */
  private String allow = "127.0.0.1";

  /**
   * 访问控制:拒绝
   */
  private String deny;

  /**
   * 允许清空统计数据
   */
  private Boolean resetEnable;

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public String getLoginUsername() {
    return loginUsername;
  }

  public void setLoginUsername(String loginUsername) {
    this.loginUsername = loginUsername;
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
  }

  public String getAllow() {
    return allow;
  }

  public void setAllow(String allow) {
    this.allow = allow;
  }

  public String getDeny() {
    return deny;
  }

  public void setDeny(String deny) {
    this.deny = deny;
  }

  public Boolean getResetEnable() {
    return resetEnable;
  }

  public void setResetEnable(Boolean resetEnable) {
    this.resetEnable = resetEnable;
  }
}

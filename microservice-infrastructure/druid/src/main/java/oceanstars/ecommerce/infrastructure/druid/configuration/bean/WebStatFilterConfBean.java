package oceanstars.ecommerce.infrastructure.druid.configuration.bean;

/**
 * Druid Web过滤规则配置信息Bean
 *
 * @author Clover
 * @version 1.0.0
 * @since 2021/11/3 10:31 下午
 */
public class WebStatFilterConfBean {

  /**
   * 配置键值名常量
   */
  public enum ConfigKey {

    // 用户信息保存至Cookie中的名字
    EXCLUSIONS("exclusions"),
    // 用户信息保存至Cookie中的名字
    PRINCIPAL_COOKIE_NAME("principalCookieName"),
    // 用户信息保存至Session中的名字
    PRINCIPAL_SESSION_NAME("principalSessionName"),
    // 监控单个url调用的sql列表
    PROFILE_ENABLE("profileEnable"),
    // session统计功能
    SESSION_STAT_ENABLE("sessionStatEnable"),
    // session统计上限
    SESSION_STAT_MAX_COUNT("sessionStatMaxCount");

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
   * URL过滤规则
   */
  private String urlPattern = "/*";

  /**
   * URL排除规则
   */
  private String exclusions = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

  /**
   * 用户信息保存至Cookie中的名字
   */
  private String principalCookieName;

  /**
   * 用户信息保存至Session中的名字
   */
  private String principalSessionName;

  /**
   * 监控单个url调用的sql列表
   */
  private Boolean profileEnable;

  /**
   * session统计功能
   */
  private Boolean sessionStatEnable;

  /**
   * session统计上限
   */
  private Integer sessionStatMaxCount;

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public String getExclusions() {
    return exclusions;
  }

  public void setExclusions(String exclusions) {
    this.exclusions = exclusions;
  }

  public String getPrincipalCookieName() {
    return principalCookieName;
  }

  public void setPrincipalCookieName(String principalCookieName) {
    this.principalCookieName = principalCookieName;
  }

  public String getPrincipalSessionName() {
    return principalSessionName;
  }

  public void setPrincipalSessionName(String principalSessionName) {
    this.principalSessionName = principalSessionName;
  }

  public Boolean getProfileEnable() {
    return profileEnable;
  }

  public void setProfileEnable(Boolean profileEnable) {
    this.profileEnable = profileEnable;
  }

  public Boolean getSessionStatEnable() {
    return sessionStatEnable;
  }

  public void setSessionStatEnable(Boolean sessionStatEnable) {
    this.sessionStatEnable = sessionStatEnable;
  }

  public Integer getSessionStatMaxCount() {
    return sessionStatMaxCount;
  }

  public void setSessionStatMaxCount(Integer sessionStatMaxCount) {
    this.sessionStatMaxCount = sessionStatMaxCount;
  }
}

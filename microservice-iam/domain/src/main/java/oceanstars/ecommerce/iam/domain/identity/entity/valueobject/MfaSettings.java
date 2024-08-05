package oceanstars.ecommerce.iam.domain.identity.entity.valueobject;

import java.io.Serial;
import oceanstars.ecommerce.common.domain.entity.ValueObject;

/**
 * 多因素认证设置值对象
 *
 * @author Clover
 * @version 1.0.0
 * @since 2024/7/8 11:24
 */
public class MfaSettings extends ValueObject {

  @Serial
  private static final long serialVersionUID = -1694997902996994836L;

  /**
   * 是否启用短信验证码
   */
  private final Boolean smsOtpEnabled;

  /**
   * 用于接收短信验证码的手机号码
   */
  private final String smsPhoneNumber;

  /**
   * 是否启用电子邮件验证码
   */
  private final Boolean emailOtpEnabled;

  /**
   * 用于接收电子邮件验证码的电子邮件地址
   */
  private final String email;

  /**
   * 基于时间的一次性密码（TOTP），如 Google Authenticator， Microsoft Authenticator。
   */
  private final Boolean totpEnabled;

  /**
   * TOTP 密钥,用于生成一次性密码
   */
  private final String totpSecret;

  /**
   * 是否启用安全密钥（如 FIDO2）。
   */
  private final Boolean securityKeyEnabled;

  /**
   * 注册的安全密钥的唯一标识
   */
  private final String securityKeyId;

  /**
   * 是否启用推送通知
   */
  private final Boolean pushNotificationEnabled;

  /**
   * 用于接收推送通知的设备ID
   */
  private final String pushNotificationDeviceId;

  /**
   * 是否启用指纹识别
   */
  private final Boolean fingerprintEnabled;

  /**
   * 是否启用人脸识别
   */
  private final Boolean faceRecognitionEnabled;

  /**
   * 构造函数：根据构建器初始化成员变量
   *
   * @param builder 构建器
   */
  private MfaSettings(Builder builder) {
    smsOtpEnabled = builder.smsOtpEnabled;
    smsPhoneNumber = builder.smsPhoneNumber;
    emailOtpEnabled = builder.emailOtpEnabled;
    email = builder.email;
    totpEnabled = builder.totpEnabled;
    totpSecret = builder.totpSecret;
    securityKeyEnabled = builder.securityKeyEnabled;
    securityKeyId = builder.securityKeyId;
    pushNotificationEnabled = builder.pushNotificationEnabled;
    pushNotificationDeviceId = builder.pushNotificationDeviceId;
    fingerprintEnabled = builder.fingerprintEnabled;
    faceRecognitionEnabled = builder.faceRecognitionEnabled;
  }

  /**
   * 创建多因素认证设置值对象构建器
   *
   * @return 多因素认证设置值对象构建器
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  public Boolean getSmsOtpEnabled() {
    return smsOtpEnabled;
  }

  public String getSmsPhoneNumber() {
    return smsPhoneNumber;
  }

  public Boolean getEmailOtpEnabled() {
    return emailOtpEnabled;
  }

  public String getEmail() {
    return email;
  }

  public Boolean getTotpEnabled() {
    return totpEnabled;
  }

  public String getTotpSecret() {
    return totpSecret;
  }

  public Boolean getSecurityKeyEnabled() {
    return securityKeyEnabled;
  }

  public String getSecurityKeyId() {
    return securityKeyId;
  }

  public Boolean getPushNotificationEnabled() {
    return pushNotificationEnabled;
  }

  public String getPushNotificationDeviceId() {
    return pushNotificationDeviceId;
  }

  public Boolean getFingerprintEnabled() {
    return fingerprintEnabled;
  }

  public Boolean getFaceRecognitionEnabled() {
    return faceRecognitionEnabled;
  }

  /**
   * <此类的功能说明>
   *
   * @author Clover
   * @version 1.0.0
   * @since 2024/7/8 11:53
   */
  public static final class Builder {

    private Boolean smsOtpEnabled;
    private String smsPhoneNumber;
    private Boolean emailOtpEnabled;
    private String email;
    private Boolean totpEnabled;
    private String totpSecret;
    private Boolean securityKeyEnabled;
    private String securityKeyId;
    private Boolean pushNotificationEnabled;
    private String pushNotificationDeviceId;
    private Boolean fingerprintEnabled;
    private Boolean faceRecognitionEnabled;

    public Builder() {
    }

    public Builder smsOtpEnabled(Boolean val) {
      smsOtpEnabled = val;
      return this;
    }

    public Builder smsPhoneNumber(String val) {
      smsPhoneNumber = val;
      return this;
    }

    public Builder emailOtpEnabled(Boolean val) {
      emailOtpEnabled = val;
      return this;
    }

    public Builder email(String val) {
      email = val;
      return this;
    }

    public Builder totpEnabled(Boolean val) {
      totpEnabled = val;
      return this;
    }

    public Builder totpSecret(String val) {
      totpSecret = val;
      return this;
    }

    public Builder securityKeyEnabled(Boolean val) {
      securityKeyEnabled = val;
      return this;
    }

    public Builder securityKeyId(String val) {
      securityKeyId = val;
      return this;
    }

    public Builder pushNotificationEnabled(Boolean val) {
      pushNotificationEnabled = val;
      return this;
    }

    public Builder pushNotificationDeviceId(String val) {
      pushNotificationDeviceId = val;
      return this;
    }

    public Builder fingerprintEnabled(Boolean val) {
      fingerprintEnabled = val;
      return this;
    }

    public Builder faceRecognitionEnabled(Boolean val) {
      faceRecognitionEnabled = val;
      return this;
    }

    public MfaSettings build() {
      return new MfaSettings(this);
    }
  }
}

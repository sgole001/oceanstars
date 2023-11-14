/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.integration.repository.generate.tables.pojos;


import java.time.LocalDateTime;

import oceanstars.ecommerce.integration.repository.generate.tables.interfaces.IIntegrationAuthentication;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegrationAuthenticationPojo implements IIntegrationAuthentication {

    private static final long serialVersionUID = 1L;

    private Long          id;
    private String        appkey;
    private String        appsecret;
    private byte[]        x509certificate;
    private String        createBy;
    private LocalDateTime createAt;

    public IntegrationAuthenticationPojo() {}

    public IntegrationAuthenticationPojo(IIntegrationAuthentication value) {
        this.id = value.getId();
        this.appkey = value.getAppkey();
        this.appsecret = value.getAppsecret();
        this.x509certificate = value.getX509certificate();
        this.createBy = value.getCreateBy();
        this.createAt = value.getCreateAt();
    }

    public IntegrationAuthenticationPojo(
        Long          id,
        String        appkey,
        String        appsecret,
        byte[]        x509certificate,
        String        createBy,
        LocalDateTime createAt
    ) {
        this.id = id;
        this.appkey = appkey;
        this.appsecret = appsecret;
        this.x509certificate = x509certificate;
        this.createBy = createBy;
        this.createAt = createAt;
    }

    /**
     * Getter for <code>integration_authentication.id</code>.
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>integration_authentication.id</code>.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>integration_authentication.appKey</code>.
     */
    @Override
    public String getAppkey() {
        return this.appkey;
    }

    /**
     * Setter for <code>integration_authentication.appKey</code>.
     */
    @Override
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     * Getter for <code>integration_authentication.appSecret</code>.
     */
    @Override
    public String getAppsecret() {
        return this.appsecret;
    }

    /**
     * Setter for <code>integration_authentication.appSecret</code>.
     */
    @Override
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    /**
     * Getter for <code>integration_authentication.x509Certificate</code>.
     */
    @Override
    public byte[] getX509certificate() {
        return this.x509certificate;
    }

    /**
     * Setter for <code>integration_authentication.x509Certificate</code>.
     */
    @Override
    public void setX509certificate(byte[] x509certificate) {
        this.x509certificate = x509certificate;
    }

    /**
     * Getter for <code>integration_authentication.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * Setter for <code>integration_authentication.create_by</code>.
     */
    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter for <code>integration_authentication.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>integration_authentication.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntegrationAuthenticationPojo (");

        sb.append(id);
        sb.append(", ").append(appkey);
        sb.append(", ").append(appsecret);
        sb.append(", ").append("[binary...]");
        sb.append(", ").append(createBy);
        sb.append(", ").append(createAt);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IIntegrationAuthentication from) {
        setId(from.getId());
        setAppkey(from.getAppkey());
        setAppsecret(from.getAppsecret());
        setX509certificate(from.getX509certificate());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
    }

    @Override
    public <E extends IIntegrationAuthentication> E into(E into) {
        into.from(this);
        return into;
    }
}

/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.integration.repository.generate.tables.pojos;


import java.time.LocalDateTime;

import oceanstars.ecommerce.integration.repository.generate.tables.interfaces.IIntegrationRoute;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegrationRoutePojo implements IIntegrationRoute {

    private static final long serialVersionUID = 1L;

    private Long          id;
    private String        code;
    private String        event;
    private String        group;
    private Long          service;
    private Long          gateway;
    private String        name;
    private String        description;
    private String        createBy;
    private LocalDateTime createAt;
    private String        updateBy;
    private LocalDateTime updateAt;
    private Integer       version;

    public IntegrationRoutePojo() {}

    public IntegrationRoutePojo(IIntegrationRoute value) {
        this.id = value.getId();
        this.code = value.getCode();
        this.event = value.getEvent();
        this.group = value.getGroup();
        this.service = value.getService();
        this.gateway = value.getGateway();
        this.name = value.getName();
        this.description = value.getDescription();
        this.createBy = value.getCreateBy();
        this.createAt = value.getCreateAt();
        this.updateBy = value.getUpdateBy();
        this.updateAt = value.getUpdateAt();
        this.version = value.getVersion();
    }

    public IntegrationRoutePojo(
        Long          id,
        String        code,
        String        event,
        String        group,
        Long          service,
        Long          gateway,
        String        name,
        String        description,
        String        createBy,
        LocalDateTime createAt,
        String        updateBy,
        LocalDateTime updateAt,
        Integer       version
    ) {
        this.id = id;
        this.code = code;
        this.event = event;
        this.group = group;
        this.service = service;
        this.gateway = gateway;
        this.name = name;
        this.description = description;
        this.createBy = createBy;
        this.createAt = createAt;
        this.updateBy = updateBy;
        this.updateAt = updateAt;
        this.version = version;
    }

    /**
     * Getter for <code>integration_route.id</code>.
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>integration_route.id</code>.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>integration_route.code</code>.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Setter for <code>integration_route.code</code>.
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for <code>integration_route.event</code>.
     */
    @Override
    public String getEvent() {
        return this.event;
    }

    /**
     * Setter for <code>integration_route.event</code>.
     */
    @Override
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * Getter for <code>integration_route.group</code>.
     */
    @Override
    public String getGroup() {
        return this.group;
    }

    /**
     * Setter for <code>integration_route.group</code>.
     */
    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Getter for <code>integration_route.service</code>.
     */
    @Override
    public Long getService() {
        return this.service;
    }

    /**
     * Setter for <code>integration_route.service</code>.
     */
    @Override
    public void setService(Long service) {
        this.service = service;
    }

    /**
     * Getter for <code>integration_route.gateway</code>.
     */
    @Override
    public Long getGateway() {
        return this.gateway;
    }

    /**
     * Setter for <code>integration_route.gateway</code>.
     */
    @Override
    public void setGateway(Long gateway) {
        this.gateway = gateway;
    }

    /**
     * Getter for <code>integration_route.name</code>.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>integration_route.name</code>.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>integration_route.description</code>.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>integration_route.description</code>.
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for <code>integration_route.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * Setter for <code>integration_route.create_by</code>.
     */
    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter for <code>integration_route.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>integration_route.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * Getter for <code>integration_route.update_by</code>.
     */
    @Override
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * Setter for <code>integration_route.update_by</code>.
     */
    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Getter for <code>integration_route.update_at</code>.
     */
    @Override
    public LocalDateTime getUpdateAt() {
        return this.updateAt;
    }

    /**
     * Setter for <code>integration_route.update_at</code>.
     */
    @Override
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Getter for <code>integration_route.version</code>.
     */
    @Override
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Setter for <code>integration_route.version</code>.
     */
    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntegrationRoutePojo (");

        sb.append(id);
        sb.append(", ").append(code);
        sb.append(", ").append(event);
        sb.append(", ").append(group);
        sb.append(", ").append(service);
        sb.append(", ").append(gateway);
        sb.append(", ").append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(createBy);
        sb.append(", ").append(createAt);
        sb.append(", ").append(updateBy);
        sb.append(", ").append(updateAt);
        sb.append(", ").append(version);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IIntegrationRoute from) {
        setId(from.getId());
        setCode(from.getCode());
        setEvent(from.getEvent());
        setGroup(from.getGroup());
        setService(from.getService());
        setGateway(from.getGateway());
        setName(from.getName());
        setDescription(from.getDescription());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
        setUpdateBy(from.getUpdateBy());
        setUpdateAt(from.getUpdateAt());
        setVersion(from.getVersion());
    }

    @Override
    public <E extends IIntegrationRoute> E into(E into) {
        into.from(this);
        return into;
    }
}

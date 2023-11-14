/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.integration.repository.generate.tables.interfaces;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public interface IIntegrationRoute extends Serializable {

    /**
     * Setter for <code>integration_route.id</code>.
     */
    public void setId(Long value);

    /**
     * Getter for <code>integration_route.id</code>.
     */
    public Long getId();

    /**
     * Setter for <code>integration_route.code</code>.
     */
    public void setCode(String value);

    /**
     * Getter for <code>integration_route.code</code>.
     */
    public String getCode();

    /**
     * Setter for <code>integration_route.event</code>.
     */
    public void setEvent(String value);

    /**
     * Getter for <code>integration_route.event</code>.
     */
    public String getEvent();

    /**
     * Setter for <code>integration_route.group</code>.
     */
    public void setGroup(String value);

    /**
     * Getter for <code>integration_route.group</code>.
     */
    public String getGroup();

    /**
     * Setter for <code>integration_route.service</code>.
     */
    public void setService(Long value);

    /**
     * Getter for <code>integration_route.service</code>.
     */
    public Long getService();

    /**
     * Setter for <code>integration_route.gateway</code>.
     */
    public void setGateway(Long value);

    /**
     * Getter for <code>integration_route.gateway</code>.
     */
    public Long getGateway();

    /**
     * Setter for <code>integration_route.name</code>.
     */
    public void setName(String value);

    /**
     * Getter for <code>integration_route.name</code>.
     */
    public String getName();

    /**
     * Setter for <code>integration_route.description</code>.
     */
    public void setDescription(String value);

    /**
     * Getter for <code>integration_route.description</code>.
     */
    public String getDescription();

    /**
     * Setter for <code>integration_route.create_by</code>.
     */
    public void setCreateBy(String value);

    /**
     * Getter for <code>integration_route.create_by</code>.
     */
    public String getCreateBy();

    /**
     * Setter for <code>integration_route.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value);

    /**
     * Getter for <code>integration_route.create_at</code>.
     */
    public LocalDateTime getCreateAt();

    /**
     * Setter for <code>integration_route.update_by</code>.
     */
    public void setUpdateBy(String value);

    /**
     * Getter for <code>integration_route.update_by</code>.
     */
    public String getUpdateBy();

    /**
     * Setter for <code>integration_route.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime value);

    /**
     * Getter for <code>integration_route.update_at</code>.
     */
    public LocalDateTime getUpdateAt();

    /**
     * Setter for <code>integration_route.version</code>.
     */
    public void setVersion(Integer value);

    /**
     * Getter for <code>integration_route.version</code>.
     */
    public Integer getVersion();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface IIntegrationRoute
     */
    public void from(IIntegrationRoute from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface IIntegrationRoute
     */
    public <E extends IIntegrationRoute> E into(E into);
}

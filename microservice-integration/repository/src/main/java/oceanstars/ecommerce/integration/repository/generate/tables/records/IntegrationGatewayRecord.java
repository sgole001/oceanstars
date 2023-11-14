/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.integration.repository.generate.tables.records;


import java.time.LocalDateTime;

import oceanstars.ecommerce.integration.repository.generate.tables.IntegrationGateway;
import oceanstars.ecommerce.integration.repository.generate.tables.interfaces.IIntegrationGateway;
import oceanstars.ecommerce.integration.repository.generate.tables.pojos.IntegrationGatewayPojo;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IntegrationGatewayRecord extends UpdatableRecordImpl<IntegrationGatewayRecord> implements Record9<Long, String, Short, String, String, LocalDateTime, String, LocalDateTime, Integer>, IIntegrationGateway {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>integration_gateway.id</code>.
     */
    @Override
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>integration_gateway.id</code>.
     */
    @Override
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>integration_gateway.code</code>.
     */
    @Override
    public void setCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>integration_gateway.code</code>.
     */
    @Override
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>integration_gateway.system</code>.
     */
    @Override
    public void setSystem(Short value) {
        set(2, value);
    }

    /**
     * Getter for <code>integration_gateway.system</code>.
     */
    @Override
    public Short getSystem() {
        return (Short) get(2);
    }

    /**
     * Setter for <code>integration_gateway.description</code>.
     */
    @Override
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>integration_gateway.description</code>.
     */
    @Override
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>integration_gateway.create_by</code>.
     */
    @Override
    public void setCreateBy(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>integration_gateway.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return (String) get(4);
    }

    /**
     * Setter for <code>integration_gateway.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>integration_gateway.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>integration_gateway.update_by</code>.
     */
    @Override
    public void setUpdateBy(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>integration_gateway.update_by</code>.
     */
    @Override
    public String getUpdateBy() {
        return (String) get(6);
    }

    /**
     * Setter for <code>integration_gateway.update_at</code>.
     */
    @Override
    public void setUpdateAt(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>integration_gateway.update_at</code>.
     */
    @Override
    public LocalDateTime getUpdateAt() {
        return (LocalDateTime) get(7);
    }

    /**
     * Setter for <code>integration_gateway.version</code>.
     */
    @Override
    public void setVersion(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>integration_gateway.version</code>.
     */
    @Override
    public Integer getVersion() {
        return (Integer) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, Short, String, String, LocalDateTime, String, LocalDateTime, Integer> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, String, Short, String, String, LocalDateTime, String, LocalDateTime, Integer> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return IntegrationGateway.INTEGRATION_GATEWAY.ID;
    }

    @Override
    public Field<String> field2() {
        return IntegrationGateway.INTEGRATION_GATEWAY.CODE;
    }

    @Override
    public Field<Short> field3() {
        return IntegrationGateway.INTEGRATION_GATEWAY.SYSTEM;
    }

    @Override
    public Field<String> field4() {
        return IntegrationGateway.INTEGRATION_GATEWAY.DESCRIPTION;
    }

    @Override
    public Field<String> field5() {
        return IntegrationGateway.INTEGRATION_GATEWAY.CREATE_BY;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return IntegrationGateway.INTEGRATION_GATEWAY.CREATE_AT;
    }

    @Override
    public Field<String> field7() {
        return IntegrationGateway.INTEGRATION_GATEWAY.UPDATE_BY;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return IntegrationGateway.INTEGRATION_GATEWAY.UPDATE_AT;
    }

    @Override
    public Field<Integer> field9() {
        return IntegrationGateway.INTEGRATION_GATEWAY.VERSION;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getCode();
    }

    @Override
    public Short component3() {
        return getSystem();
    }

    @Override
    public String component4() {
        return getDescription();
    }

    @Override
    public String component5() {
        return getCreateBy();
    }

    @Override
    public LocalDateTime component6() {
        return getCreateAt();
    }

    @Override
    public String component7() {
        return getUpdateBy();
    }

    @Override
    public LocalDateTime component8() {
        return getUpdateAt();
    }

    @Override
    public Integer component9() {
        return getVersion();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getCode();
    }

    @Override
    public Short value3() {
        return getSystem();
    }

    @Override
    public String value4() {
        return getDescription();
    }

    @Override
    public String value5() {
        return getCreateBy();
    }

    @Override
    public LocalDateTime value6() {
        return getCreateAt();
    }

    @Override
    public String value7() {
        return getUpdateBy();
    }

    @Override
    public LocalDateTime value8() {
        return getUpdateAt();
    }

    @Override
    public Integer value9() {
        return getVersion();
    }

    @Override
    public IntegrationGatewayRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value2(String value) {
        setCode(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value3(Short value) {
        setSystem(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value4(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value5(String value) {
        setCreateBy(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value6(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value7(String value) {
        setUpdateBy(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value8(LocalDateTime value) {
        setUpdateAt(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord value9(Integer value) {
        setVersion(value);
        return this;
    }

    @Override
    public IntegrationGatewayRecord values(Long value1, String value2, Short value3, String value4, String value5, LocalDateTime value6, String value7, LocalDateTime value8, Integer value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IIntegrationGateway from) {
        setId(from.getId());
        setCode(from.getCode());
        setSystem(from.getSystem());
        setDescription(from.getDescription());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
        setUpdateBy(from.getUpdateBy());
        setUpdateAt(from.getUpdateAt());
        setVersion(from.getVersion());
    }

    @Override
    public <E extends IIntegrationGateway> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IntegrationGatewayRecord
     */
    public IntegrationGatewayRecord() {
        super(IntegrationGateway.INTEGRATION_GATEWAY);
    }

    /**
     * Create a detached, initialised IntegrationGatewayRecord
     */
    public IntegrationGatewayRecord(Long id, String code, Short system, String description, String createBy, LocalDateTime createAt, String updateBy, LocalDateTime updateAt, Integer version) {
        super(IntegrationGateway.INTEGRATION_GATEWAY);

        setId(id);
        setCode(code);
        setSystem(system);
        setDescription(description);
        setCreateBy(createBy);
        setCreateAt(createAt);
        setUpdateBy(updateBy);
        setUpdateAt(updateAt);
        setVersion(version);
    }

    /**
     * Create a detached, initialised IntegrationGatewayRecord
     */
    public IntegrationGatewayRecord(IntegrationGatewayPojo value) {
        super(IntegrationGateway.INTEGRATION_GATEWAY);

        if (value != null) {
            setId(value.getId());
            setCode(value.getCode());
            setSystem(value.getSystem());
            setDescription(value.getDescription());
            setCreateBy(value.getCreateBy());
            setCreateAt(value.getCreateAt());
            setUpdateBy(value.getUpdateBy());
            setUpdateAt(value.getUpdateAt());
            setVersion(value.getVersion());
        }
    }
}

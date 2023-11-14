/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.user.repository.generate.tables.records;


import java.time.LocalDateTime;

import oceanstars.ecommerce.user.repository.generate.tables.RelRolePermission;
import oceanstars.ecommerce.user.repository.generate.tables.interfaces.IRelRolePermission;
import oceanstars.ecommerce.user.repository.generate.tables.pojos.RelRolePermissionPojo;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RelRolePermissionRecord extends UpdatableRecordImpl<RelRolePermissionRecord> implements Record5<Long, Long, Long, String, LocalDateTime>, IRelRolePermission {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>rel_role_permission.id</code>.
     */
    @Override
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>rel_role_permission.id</code>.
     */
    @Override
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>rel_role_permission.rid</code>.
     */
    @Override
    public void setRid(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>rel_role_permission.rid</code>.
     */
    @Override
    public Long getRid() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>rel_role_permission.pid</code>.
     */
    @Override
    public void setPid(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>rel_role_permission.pid</code>.
     */
    @Override
    public Long getPid() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>rel_role_permission.create_by</code>.
     */
    @Override
    public void setCreateBy(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>rel_role_permission.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return (String) get(3);
    }

    /**
     * Setter for <code>rel_role_permission.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>rel_role_permission.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Long, Long, String, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Long, Long, String, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return RelRolePermission.REL_ROLE_PERMISSION.ID;
    }

    @Override
    public Field<Long> field2() {
        return RelRolePermission.REL_ROLE_PERMISSION.RID;
    }

    @Override
    public Field<Long> field3() {
        return RelRolePermission.REL_ROLE_PERMISSION.PID;
    }

    @Override
    public Field<String> field4() {
        return RelRolePermission.REL_ROLE_PERMISSION.CREATE_BY;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return RelRolePermission.REL_ROLE_PERMISSION.CREATE_AT;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getRid();
    }

    @Override
    public Long component3() {
        return getPid();
    }

    @Override
    public String component4() {
        return getCreateBy();
    }

    @Override
    public LocalDateTime component5() {
        return getCreateAt();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getRid();
    }

    @Override
    public Long value3() {
        return getPid();
    }

    @Override
    public String value4() {
        return getCreateBy();
    }

    @Override
    public LocalDateTime value5() {
        return getCreateAt();
    }

    @Override
    public RelRolePermissionRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public RelRolePermissionRecord value2(Long value) {
        setRid(value);
        return this;
    }

    @Override
    public RelRolePermissionRecord value3(Long value) {
        setPid(value);
        return this;
    }

    @Override
    public RelRolePermissionRecord value4(String value) {
        setCreateBy(value);
        return this;
    }

    @Override
    public RelRolePermissionRecord value5(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public RelRolePermissionRecord values(Long value1, Long value2, Long value3, String value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IRelRolePermission from) {
        setId(from.getId());
        setRid(from.getRid());
        setPid(from.getPid());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
    }

    @Override
    public <E extends IRelRolePermission> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RelRolePermissionRecord
     */
    public RelRolePermissionRecord() {
        super(RelRolePermission.REL_ROLE_PERMISSION);
    }

    /**
     * Create a detached, initialised RelRolePermissionRecord
     */
    public RelRolePermissionRecord(Long id, Long rid, Long pid, String createBy, LocalDateTime createAt) {
        super(RelRolePermission.REL_ROLE_PERMISSION);

        setId(id);
        setRid(rid);
        setPid(pid);
        setCreateBy(createBy);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised RelRolePermissionRecord
     */
    public RelRolePermissionRecord(RelRolePermissionPojo value) {
        super(RelRolePermission.REL_ROLE_PERMISSION);

        if (value != null) {
            setId(value.getId());
            setRid(value.getRid());
            setPid(value.getPid());
            setCreateBy(value.getCreateBy());
            setCreateAt(value.getCreateAt());
        }
    }
}

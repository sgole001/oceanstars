/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.user.repository.generate.tables.pojos;


import java.time.LocalDateTime;

import oceanstars.ecommerce.user.repository.generate.tables.interfaces.IRelPermissionResource;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RelPermissionResourcePojo implements IRelPermissionResource {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long pid;
    private Long rid;
    private Long tid;
    private Long oid;
    private String createBy;
    private LocalDateTime createAt;

    public RelPermissionResourcePojo() {}

    public RelPermissionResourcePojo(IRelPermissionResource value) {
        this.id = value.getId();
        this.pid = value.getPid();
        this.rid = value.getRid();
        this.tid = value.getTid();
        this.oid = value.getOid();
        this.createBy = value.getCreateBy();
        this.createAt = value.getCreateAt();
    }

    public RelPermissionResourcePojo(
        Long id,
        Long pid,
        Long rid,
        Long tid,
        Long oid,
        String createBy,
        LocalDateTime createAt
    ) {
        this.id = id;
        this.pid = pid;
        this.rid = rid;
        this.tid = tid;
        this.oid = oid;
        this.createBy = createBy;
        this.createAt = createAt;
    }

    /**
     * Getter for <code>rel_permission_resource.id</code>.
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>rel_permission_resource.id</code>.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>rel_permission_resource.pid</code>.
     */
    @Override
    public Long getPid() {
        return this.pid;
    }

    /**
     * Setter for <code>rel_permission_resource.pid</code>.
     */
    @Override
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * Getter for <code>rel_permission_resource.rid</code>.
     */
    @Override
    public Long getRid() {
        return this.rid;
    }

    /**
     * Setter for <code>rel_permission_resource.rid</code>.
     */
    @Override
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * Getter for <code>rel_permission_resource.tid</code>.
     */
    @Override
    public Long getTid() {
        return this.tid;
    }

    /**
     * Setter for <code>rel_permission_resource.tid</code>.
     */
    @Override
    public void setTid(Long tid) {
        this.tid = tid;
    }

    /**
     * Getter for <code>rel_permission_resource.oid</code>.
     */
    @Override
    public Long getOid() {
        return this.oid;
    }

    /**
     * Setter for <code>rel_permission_resource.oid</code>.
     */
    @Override
    public void setOid(Long oid) {
        this.oid = oid;
    }

    /**
     * Getter for <code>rel_permission_resource.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * Setter for <code>rel_permission_resource.create_by</code>.
     */
    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter for <code>rel_permission_resource.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>rel_permission_resource.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RelPermissionResourcePojo other = (RelPermissionResourcePojo) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.pid == null) {
            if (other.pid != null)
                return false;
        }
        else if (!this.pid.equals(other.pid))
            return false;
        if (this.rid == null) {
            if (other.rid != null)
                return false;
        }
        else if (!this.rid.equals(other.rid))
            return false;
        if (this.tid == null) {
            if (other.tid != null)
                return false;
        }
        else if (!this.tid.equals(other.tid))
            return false;
        if (this.oid == null) {
            if (other.oid != null)
                return false;
        }
        else if (!this.oid.equals(other.oid))
            return false;
        if (this.createBy == null) {
            if (other.createBy != null)
                return false;
        }
        else if (!this.createBy.equals(other.createBy))
            return false;
        if (this.createAt == null) {
            if (other.createAt != null)
                return false;
        }
        else if (!this.createAt.equals(other.createAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.pid == null) ? 0 : this.pid.hashCode());
        result = prime * result + ((this.rid == null) ? 0 : this.rid.hashCode());
        result = prime * result + ((this.tid == null) ? 0 : this.tid.hashCode());
        result = prime * result + ((this.oid == null) ? 0 : this.oid.hashCode());
        result = prime * result + ((this.createBy == null) ? 0 : this.createBy.hashCode());
        result = prime * result + ((this.createAt == null) ? 0 : this.createAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RelPermissionResourcePojo (");

        sb.append(id);
        sb.append(", ").append(pid);
        sb.append(", ").append(rid);
        sb.append(", ").append(tid);
        sb.append(", ").append(oid);
        sb.append(", ").append(createBy);
        sb.append(", ").append(createAt);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IRelPermissionResource from) {
        setId(from.getId());
        setPid(from.getPid());
        setRid(from.getRid());
        setTid(from.getTid());
        setOid(from.getOid());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
    }

    @Override
    public <E extends IRelPermissionResource> E into(E into) {
        into.from(this);
        return into;
    }
}

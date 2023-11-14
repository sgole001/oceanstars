/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.user.repository.generate.tables.pojos;


import java.time.LocalDateTime;

import oceanstars.ecommerce.user.repository.generate.tables.interfaces.IUserPermission;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserPermissionPojo implements IUserPermission {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String code;
    private String name;
    private Short type;
    private String desc;
    private Boolean enabled;
    private String createBy;
    private LocalDateTime createAt;
    private String updateBy;
    private LocalDateTime updateAt;
    private Integer version;

    public UserPermissionPojo() {}

    public UserPermissionPojo(IUserPermission value) {
        this.id = value.getId();
        this.code = value.getCode();
        this.name = value.getName();
        this.type = value.getType();
        this.desc = value.getDesc();
        this.enabled = value.getEnabled();
        this.createBy = value.getCreateBy();
        this.createAt = value.getCreateAt();
        this.updateBy = value.getUpdateBy();
        this.updateAt = value.getUpdateAt();
        this.version = value.getVersion();
    }

    public UserPermissionPojo(
        Long id,
        String code,
        String name,
        Short type,
        String desc,
        Boolean enabled,
        String createBy,
        LocalDateTime createAt,
        String updateBy,
        LocalDateTime updateAt,
        Integer version
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.enabled = enabled;
        this.createBy = createBy;
        this.createAt = createAt;
        this.updateBy = updateBy;
        this.updateAt = updateAt;
        this.version = version;
    }

    /**
     * Getter for <code>user_permission.id</code>.
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>user_permission.id</code>.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for <code>user_permission.code</code>.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Setter for <code>user_permission.code</code>.
     */
    @Override
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for <code>user_permission.name</code>.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>user_permission.name</code>.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>user_permission.type</code>.
     */
    @Override
    public Short getType() {
        return this.type;
    }

    /**
     * Setter for <code>user_permission.type</code>.
     */
    @Override
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * Getter for <code>user_permission.desc</code>.
     */
    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * Setter for <code>user_permission.desc</code>.
     */
    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter for <code>user_permission.enabled</code>.
     */
    @Override
    public Boolean getEnabled() {
        return this.enabled;
    }

    /**
     * Setter for <code>user_permission.enabled</code>.
     */
    @Override
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for <code>user_permission.create_by</code>.
     */
    @Override
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * Setter for <code>user_permission.create_by</code>.
     */
    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter for <code>user_permission.create_at</code>.
     */
    @Override
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>user_permission.create_at</code>.
     */
    @Override
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * Getter for <code>user_permission.update_by</code>.
     */
    @Override
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * Setter for <code>user_permission.update_by</code>.
     */
    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * Getter for <code>user_permission.update_at</code>.
     */
    @Override
    public LocalDateTime getUpdateAt() {
        return this.updateAt;
    }

    /**
     * Setter for <code>user_permission.update_at</code>.
     */
    @Override
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Getter for <code>user_permission.version</code>.
     */
    @Override
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Setter for <code>user_permission.version</code>.
     */
    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserPermissionPojo other = (UserPermissionPojo) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.code == null) {
            if (other.code != null)
                return false;
        }
        else if (!this.code.equals(other.code))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.type == null) {
            if (other.type != null)
                return false;
        }
        else if (!this.type.equals(other.type))
            return false;
        if (this.desc == null) {
            if (other.desc != null)
                return false;
        }
        else if (!this.desc.equals(other.desc))
            return false;
        if (this.enabled == null) {
            if (other.enabled != null)
                return false;
        }
        else if (!this.enabled.equals(other.enabled))
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
        if (this.updateBy == null) {
            if (other.updateBy != null)
                return false;
        }
        else if (!this.updateBy.equals(other.updateBy))
            return false;
        if (this.updateAt == null) {
            if (other.updateAt != null)
                return false;
        }
        else if (!this.updateAt.equals(other.updateAt))
            return false;
        if (this.version == null) {
            if (other.version != null)
                return false;
        }
        else if (!this.version.equals(other.version))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.code == null) ? 0 : this.code.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        result = prime * result + ((this.desc == null) ? 0 : this.desc.hashCode());
        result = prime * result + ((this.enabled == null) ? 0 : this.enabled.hashCode());
        result = prime * result + ((this.createBy == null) ? 0 : this.createBy.hashCode());
        result = prime * result + ((this.createAt == null) ? 0 : this.createAt.hashCode());
        result = prime * result + ((this.updateBy == null) ? 0 : this.updateBy.hashCode());
        result = prime * result + ((this.updateAt == null) ? 0 : this.updateAt.hashCode());
        result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserPermissionPojo (");

        sb.append(id);
        sb.append(", ").append(code);
        sb.append(", ").append(name);
        sb.append(", ").append(type);
        sb.append(", ").append(desc);
        sb.append(", ").append(enabled);
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
    public void from(IUserPermission from) {
        setId(from.getId());
        setCode(from.getCode());
        setName(from.getName());
        setType(from.getType());
        setDesc(from.getDesc());
        setEnabled(from.getEnabled());
        setCreateBy(from.getCreateBy());
        setCreateAt(from.getCreateAt());
        setUpdateBy(from.getUpdateBy());
        setUpdateAt(from.getUpdateAt());
        setVersion(from.getVersion());
    }

    @Override
    public <E extends IUserPermission> E into(E into) {
        into.from(this);
        return into;
    }
}

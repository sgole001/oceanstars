/*
 * This file is generated by jOOQ.
 */
package oceanstars.ecommerce.user.repository.generate;


import oceanstars.ecommerce.user.repository.generate.tables.RelAccountRole;
import oceanstars.ecommerce.user.repository.generate.tables.RelPermissionResource;
import oceanstars.ecommerce.user.repository.generate.tables.RelRolePermission;
import oceanstars.ecommerce.user.repository.generate.tables.RelRoleRole;
import oceanstars.ecommerce.user.repository.generate.tables.UserAccount;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in the default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index IDX_CREAT_IP = Internal.createIndex(DSL.name("idx_creat_ip"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.CREAT_IP }, false);
    public static final Index IDX_EMAIL = Internal.createIndex(DSL.name("idx_email"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.EMAIL }, false);
    public static final Index IDX_EXTERNAL_ID = Internal.createIndex(DSL.name("idx_external_id"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.EXTERNAL_ID }, false);
    public static final Index IDX_LAST_LOGIN_IP = Internal.createIndex(DSL.name("idx_last_login_ip"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.LAST_LOGIN_IP }, false);
    public static final Index IDX_LOGIN_TIMES = Internal.createIndex(DSL.name("idx_login_times"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.LOGIN_TIMES }, false);
    public static final Index IDX_MOBILE = Internal.createIndex(DSL.name("idx_mobile"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.MOBILE }, false);
    public static final Index IDX_REL_ACC_ROLE_AID = Internal.createIndex(DSL.name("idx_rel_acc_role_aid"), RelAccountRole.REL_ACCOUNT_ROLE, new OrderField[] { RelAccountRole.REL_ACCOUNT_ROLE.AID }, false);
    public static final Index IDX_REL_ACC_ROLE_RID = Internal.createIndex(DSL.name("idx_rel_acc_role_rid"), RelAccountRole.REL_ACCOUNT_ROLE, new OrderField[] { RelAccountRole.REL_ACCOUNT_ROLE.RID }, false);
    public static final Index IDX_REL_PERM_RES_OPS_OID = Internal.createIndex(DSL.name("idx_rel_perm_res_ops_oid"), RelPermissionResource.REL_PERMISSION_RESOURCE, new OrderField[] { RelPermissionResource.REL_PERMISSION_RESOURCE.TID }, false);
    public static final Index IDX_REL_PERM_RES_OPS_PID = Internal.createIndex(DSL.name("idx_rel_perm_res_ops_pid"), RelPermissionResource.REL_PERMISSION_RESOURCE, new OrderField[] { RelPermissionResource.REL_PERMISSION_RESOURCE.PID }, false);
    public static final Index IDX_REL_PERM_RES_OPS_RID = Internal.createIndex(DSL.name("idx_rel_perm_res_ops_rid"), RelPermissionResource.REL_PERMISSION_RESOURCE, new OrderField[] { RelPermissionResource.REL_PERMISSION_RESOURCE.RID }, false);
    public static final Index IDX_REL_PERM_RES_OPS_TID = Internal.createIndex(DSL.name("idx_rel_perm_res_ops_tid"), RelPermissionResource.REL_PERMISSION_RESOURCE, new OrderField[] { RelPermissionResource.REL_PERMISSION_RESOURCE.TID }, false);
    public static final Index IDX_REL_ROLE_PERM_PID = Internal.createIndex(DSL.name("idx_rel_role_perm_pid"), RelRolePermission.REL_ROLE_PERMISSION, new OrderField[] { RelRolePermission.REL_ROLE_PERMISSION.PID }, false);
    public static final Index IDX_REL_ROLE_PERM_RID = Internal.createIndex(DSL.name("idx_rel_role_perm_rid"), RelRolePermission.REL_ROLE_PERMISSION, new OrderField[] { RelRolePermission.REL_ROLE_PERMISSION.RID }, false);
    public static final Index IDX_REL_ROLE_PID = Internal.createIndex(DSL.name("idx_rel_role_pid"), RelRoleRole.REL_ROLE_ROLE, new OrderField[] { RelRoleRole.REL_ROLE_ROLE.PID }, false);
    public static final Index IDX_REL_ROLE_RID = Internal.createIndex(DSL.name("idx_rel_role_rid"), RelRoleRole.REL_ROLE_ROLE, new OrderField[] { RelRoleRole.REL_ROLE_ROLE.RID }, false);
    public static final Index IDX_SOURCE = Internal.createIndex(DSL.name("idx_source"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.SOURCE }, false);
    public static final Index IDX_STATUS = Internal.createIndex(DSL.name("idx_status"), UserAccount.USER_ACCOUNT, new OrderField[] { UserAccount.USER_ACCOUNT.STATUS }, false);
}

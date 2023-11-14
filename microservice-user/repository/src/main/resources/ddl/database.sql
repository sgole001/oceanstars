/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户账号数据                   */
/******************************************/
CREATE TABLE `user_account`
(
    `id`            bigint(0)    NOT NULL COMMENT 'id',
    `code`          varchar(12)  NOT NULL COMMENT '账号编码-自然键',
    `source`        smallint(0)  NOT NULL COMMENT '账号注册源',
    `means`         smallint(0)  NOT NULL COMMENT '账号注册方式',
    `email`         varchar(50)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `mobile`        varchar(11)  NOT NULL DEFAULT '' COMMENT '手机',
    `password`      varchar(250) NOT NULL COMMENT '账号密码',
    `external_id`   varchar(50)  NOT NULL DEFAULT '' COMMENT '第三方外部UID',
    `creat_ip`      varchar(50)  NOT NULL COMMENT '创建时IP',
    `last_login_ip` varchar(50)  NOT NULL COMMENT '最后登陆IP',
    `login_times`   int(0)       NOT NULL DEFAULT 0 COMMENT '登陆次数',
    `status`        smallint(0)  NOT NULL COMMENT '账号状态',
    `create_by`     varchar(255) NOT NULL COMMENT '创建者',
    `create_at`     datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`     varchar(255) NOT NULL COMMENT '更新者',
    `update_at`     datetime(0)  NOT NULL COMMENT '更新时间',
    `version`       int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_account_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_register` (`source`, `means`, `email`, `mobile`, `external_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_mobile` ON `user_account` (`mobile` ASC);
CREATE INDEX `idx_email` ON `user_account` (`email` ASC);
CREATE INDEX `idx_status` ON `user_account` (`status` ASC);
CREATE INDEX `idx_source` ON `user_account` (`source` ASC);
CREATE INDEX `idx_external_id` ON `user_account` (`external_id` ASC);
CREATE INDEX `idx_creat_ip` ON `user_account` (`creat_ip` ASC);
CREATE INDEX `idx_last_login_ip` ON `user_account` (`last_login_ip` ASC);
CREATE INDEX `idx_login_times` ON `user_account` (`login_times` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户账号简况                   */
/******************************************/
CREATE TABLE `user_profile`
(
    `id`         bigint(0)    NOT NULL COMMENT 'id',
    `code`       varchar(32)  NOT NULL COMMENT '账号简况编码-自然键',
    `first_name` varchar(50)  NULL COMMENT '姓',
    `last_name`  varchar(50)  NULL COMMENT '名',
    `nick_name`  varchar(50)  NULL COMMENT '昵称',
    `avatar`     varchar(255) NULL COMMENT '用户头像路径',
    `gender`     smallint(0)  NULL COMMENT '性别 0 : male; 1 : female',
    `create_by`  varchar(255) NOT NULL COMMENT '创建者',
    `create_at`  datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`  varchar(255) NOT NULL COMMENT '更新者',
    `update_at`  datetime(0)  NOT NULL COMMENT '更新时间',
    `version`    int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_profile_identifier` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user   */
/*   表名称 = 用户角色   */
/******************************************/
CREATE TABLE `user_role`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `code`      varchar(13)  NOT NULL COMMENT '角色编码-自然键',
    `name`      varchar(255) NOT NULL COMMENT '角色名',
    `desc`      varchar(255) COMMENT '描述',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '角色逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_role_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_role` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户权限                       */
/******************************************/
CREATE TABLE `user_permission`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `code`      varchar(13)  NOT NULL COMMENT '权限编号-自然键',
    `name`      varchar(255) NOT NULL COMMENT '权限名',
    `type`      smallint(2)  NOT NULL COMMENT '权限类型',
    `desc`      varchar(255) COMMENT '权限描述',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '权限逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_perm_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_perm` (`name`, `type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户权限操作                   */
/******************************************/
CREATE TABLE `user_permission_operation`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `code`      varchar(12)  NOT NULL COMMENT '权限操作编码-自然键',
    `behavior`  varchar(255) NOT NULL COMMENT '权限操作行为',
    `desc`      varchar(255) COMMENT '权限操作说明',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '权限操作逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_opt_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_operation` (`behavior`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 权限资源类型                   */
/******************************************/
CREATE TABLE `user_resource_type`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `code`      varchar(12)  NOT NULL COMMENT '权限资源编码-自然键',
    `name`      varchar(255) NOT NULL COMMENT '权限资源名',
    `desc`      varchar(255) COMMENT '权限资源说明',
    `href`      varchar(255) NOT NULL COMMENT '资源数据API路径',
    `method`    varchar(10)  NOT NULL COMMENT '资源数据API的HTTP请求方法',
    `body`      text COMMENT '资源数据PI的HTTP请求Body(JSON字符串)',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '权限资源逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_res_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_res` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户账号和角色多对多映射         */
/******************************************/
CREATE TABLE `rel_account_role`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `aid`       bigint(0)    NOT NULL COMMENT '账号ID',
    `rid`       bigint(0)    NOT NULL COMMENT '角色ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_acc_role` (`aid`, `rid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_rel_acc_role_aid` ON `rel_account_role` (`aid` ASC);
CREATE INDEX `idx_rel_acc_role_rid` ON `rel_account_role` (`rid` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户角色隶属映射                */
/******************************************/
CREATE TABLE `rel_role_role`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `rid`       bigint(0)    NOT NULL COMMENT '角色ID',
    `pid`       bigint(0)    NOT NULL COMMENT '隶属角色ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_role` (`rid`, `pid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_rel_role_rid` ON `rel_role_role` (`rid` ASC);
CREATE INDEX `idx_rel_role_pid` ON `rel_role_role` (`pid` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 角色权限多对多映射              */
/******************************************/
CREATE TABLE `rel_role_permission`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `rid`       bigint(0)    NOT NULL COMMENT '角色ID',
    `pid`       bigint(0)    NOT NULL COMMENT '权限ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_role_perm` (`rid`, `pid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_rel_role_perm_rid` ON `rel_role_permission` (`rid` ASC);
CREATE INDEX `idx_rel_role_perm_pid` ON `rel_role_permission` (`pid` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 权限资源操作多对多映射              */
/******************************************/
CREATE TABLE `rel_permission_resource`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `pid`       bigint(0)    NOT NULL COMMENT '权限ID',
    `rid`       bigint(0)    NOT NULL COMMENT '资源ID',
    `tid`       bigint(0)    NOT NULL COMMENT '资源类型ID',
    `oid`       bigint(0)    NOT NULL COMMENT '权限操作ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_perm_res_ops` (`pid`, `rid`, `tid`, `oid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_rel_perm_res_ops_pid` ON `rel_permission_resource` (`pid` ASC);
CREATE INDEX `idx_rel_perm_res_ops_rid` ON `rel_permission_resource` (`rid` ASC);
CREATE INDEX `idx_rel_perm_res_ops_tid` ON `rel_permission_resource` (`tid` ASC);
CREATE INDEX `idx_rel_perm_res_ops_oid` ON `rel_permission_resource` (`tid` ASC);

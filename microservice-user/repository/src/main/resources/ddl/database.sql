/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户账号数据                   */
/******************************************/
CREATE TABLE `user_account`
(
    `id`          bigint(0)    NOT NULL COMMENT 'id',
    `source`      smallint(0)  NOT NULL COMMENT '账号注册源',
    `means`       smallint(0)  NOT NULL COMMENT '账号注册方式',
    `email`       varchar(50)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `mobile`      varchar(11)  NOT NULL DEFAULT '' COMMENT '手机',
    `password`    varchar(250) NOT NULL COMMENT '账号密码',
    `external_id` varchar(50)  NOT NULL DEFAULT '' COMMENT '第三方外部UID',
    `status`      smallint(0)  NOT NULL COMMENT '账号状态',
    `create_by`   varchar(255) NOT NULL COMMENT '创建者',
    `create_at`   datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`   varchar(255) NOT NULL COMMENT '更新者',
    `update_at`   datetime(0)  NOT NULL COMMENT '更新时间',
    `version`     int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_account_identifier_mobile` (`mobile`) USING BTREE,
    UNIQUE INDEX `idx_account_identifier_email` (`email`) USING BTREE,
    UNIQUE INDEX `idx_account_identifier_external` (`source`, `external_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_account_means` ON `user_account` (`means` ASC);
CREATE INDEX `idx_account_status` ON `user_account` (`status` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户账号简况                   */
/******************************************/
CREATE TABLE `user_profile`
(
    `id`         bigint(0)    NOT NULL COMMENT 'id',
    `account`    bigint(0)    NOT NULL COMMENT '账号ID',
    `first_name` varchar(50)  NULL COMMENT '姓',
    `last_name`  varchar(50)  NULL COMMENT '名',
    `nick_name`  varchar(50)  NULL COMMENT '昵称',
    `avatar`     varchar(255) NULL COMMENT '用户头像路径',
    `gender`     smallint(0)  NULL COMMENT '性别 0 : male; 1 : female',
    `birthday`   date         NULL COMMENT '生日',
    `create_by`  varchar(255) NOT NULL COMMENT '创建者',
    `create_at`  datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`  varchar(255) NOT NULL COMMENT '更新者',
    `update_at`  datetime(0)  NOT NULL COMMENT '更新时间',
    `version`    int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_profile_identifier` (`account`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_profile_first_name` ON `user_profile` (`first_name` ASC);
CREATE INDEX `idx_profile_last_name` ON `user_profile` (`last_name` ASC);
CREATE INDEX `idx_profile_nick_name` ON `user_profile` (`nick_name` ASC);
CREATE INDEX `idx_profile_gender` ON `user_profile` (`gender` ASC);
CREATE INDEX `idx_profile_birthday` ON `user_profile` (`birthday` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user   */
/*   表名称 = 用户角色   */
/******************************************/
CREATE TABLE `user_role`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `name`      varchar(255) NOT NULL COMMENT '角色名',
    `desc`      varchar(255) COMMENT '描述',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '角色逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_role_identifier` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_role_enabled` ON `user_role` (`enabled` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户权限                       */
/******************************************/
CREATE TABLE `user_permission`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `name`      varchar(255) NOT NULL COMMENT '权限名',
    `type`      smallint(0)  NOT NULL COMMENT '权限类型（区分对应操作的资源）',
    `desc`      varchar(255) COMMENT '权限描述',
    `enabled`   tinyint(0)   NOT NULL DEFAULT 1 COMMENT '权限逻辑有效标志位',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_perm_identifier` (`name`, `type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_permission_enabled` ON `user_permission` (`enabled` ASC);

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 权限资源操作多对多映射              */
/******************************************/
CREATE TABLE `user_permission_behavior`
(
    `id`         bigint(0)    NOT NULL COMMENT 'id',
    `permission` bigint(0)    NOT NULL COMMENT '权限ID',
    `resource`   bigint(0)    NOT NULL COMMENT '资源ID',
    `opt`        tinyint(0)   NOT NULL COMMENT '资源操作行为(二进制存储): 0x01 - 禁止操作; 0x02 - 允许读入; 0x04 - 允许创建; 0x08 - 允许更新; 0x10 - 允许删除',
    `create_by`  varchar(255) NOT NULL COMMENT '创建者',
    `create_at`  datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`  varchar(255) NOT NULL COMMENT '更新者',
    `update_at`  datetime(0)  NOT NULL COMMENT '更新时间',
    `version`    int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_perm_behavior_identifier` (`permission`, `resource`) USING BTREE
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
    `account`   bigint(0)    NOT NULL COMMENT '账号ID',
    `role`      bigint(0)    NOT NULL COMMENT '角色ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_acc_role_identifier` (`account`, `role`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 用户角色隶属映射                */
/******************************************/
CREATE TABLE `rel_role_role`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `role`      bigint(0)    NOT NULL COMMENT '角色ID',
    `parent`    bigint(0)    NOT NULL COMMENT '隶属角色ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_role_identifier` (`role`, `parent`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_user           */
/*   表名称 = 角色权限多对多映射              */
/******************************************/
CREATE TABLE `rel_role_permission`
(
    `id`         bigint(0)    NOT NULL COMMENT 'id',
    `role`       bigint(0)    NOT NULL COMMENT '角色ID',
    `permission` bigint(0)    NOT NULL COMMENT '权限ID',
    `create_by`  varchar(255) NOT NULL COMMENT '创建者',
    `create_at`  datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_role_perm_identifier` (`role`, `permission`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
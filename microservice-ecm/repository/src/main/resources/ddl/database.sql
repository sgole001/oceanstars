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

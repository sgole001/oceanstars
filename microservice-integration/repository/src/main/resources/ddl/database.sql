/******************************************/
/*   数据库全名 = oceanstars_integration   */
/*   表名称 = 集成网关信息   */
/******************************************/
CREATE TABLE `integration_gateway`
(
    `id`          bigint       NOT NULL COMMENT '物理PK-代理主键',
    `code`        varchar(14)  NOT NULL COMMENT '集成网关编码-自然主键（GW-UUID第一部分（根据system获取UUID））',
    `system`      smallint     NOT NULL COMMENT '集成目标系统(一个集成网关只集成一个外部系统)',
    `description` varchar(255) NOT NULL COMMENT '集成网关描述',
    `create_by`   varchar(255) NOT NULL COMMENT '创建者',
    `create_at`   datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(255) NOT NULL COMMENT '更新者',
    `update_at`   datetime     NOT NULL COMMENT '更新时间',
    `version`     int          NOT NULL DEFAULT 0 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`),
    UNIQUE KEY (`system`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_integration   */
/*   表名称 = 集成路由信息   */
/******************************************/
CREATE TABLE `integration_route`
(
    `id`          bigint       NOT NULL COMMENT '物理PK-代理主键',
    `code`        varchar(14)  NOT NULL COMMENT '集成路由编码-自然主键（ROUTE-UUID第一部分（根据event和group获取UUID））',
    `event`       varchar(50)  NOT NULL COMMENT '集成触发事件',
    `group`       varchar(50)  NOT NULL COMMENT '集成处理组',
    `service`     bigint       NOT NULL COMMENT '集成路由服务ID',
    `gateway`     bigint       NOT NULL COMMENT '集成网关ID',
    `name`        varchar(100) NOT NULL COMMENT '集成路由名',
    `description` varchar(255) NOT NULL COMMENT '集成路由描述',
    `create_by`   varchar(255) NOT NULL COMMENT '创建者',
    `create_at`   datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(255) NOT NULL COMMENT '更新者',
    `update_at`   datetime     NOT NULL COMMENT '更新时间',
    `version`     int          NOT NULL DEFAULT 0 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_event` ON `integration_route` (`event` ASC);
CREATE INDEX `idx_group` ON `integration_route` (`group` ASC);
CREATE INDEX `idx_service` ON `integration_route` (`service` ASC);
CREATE INDEX `idx_gateway` ON `integration_route` (`gateway` ASC);
CREATE UNIQUE INDEX `idx_route` ON `integration_route` (`event`, `group`);

/******************************************/
/*   数据库全名 = oceanstars_integration   */
/*   表名称 = 集成目标服务信息   */
/******************************************/
CREATE TABLE `integration_service`
(
    `id`             bigint       NOT NULL COMMENT '物理PK-代理主键',
    `code`           varchar(14)  NOT NULL COMMENT '集成目标服务编码-自然主键（ROUTE-UUID第一部分（根据endpoint获取UUID））',
    `endpoint`       varchar(255) NOT NULL COMMENT '目标服务终结点',
    `type`           smallint     NOT NULL COMMENT '目标集成类型（数据出入境）',
    `name`           varchar(100) COMMENT '目标服务名',
    `retries`        smallint     NOT NULL default 3 COMMENT '目标服务请求异常重试次数',
    `connectTimeout` int          NOT NULL default 60000 COMMENT '目标服务请求连接超时时间（单位：milliseconds）',
    `authentication` bigint COMMENT '目标服务认证信息ID',
    `create_by`      varchar(255) NOT NULL COMMENT '创建者',
    `create_at`      datetime     NOT NULL COMMENT '创建时间',
    `update_by`      varchar(255) NOT NULL COMMENT '更新者',
    `update_at`      datetime     NOT NULL COMMENT '更新时间',
    `version`        int          NOT NULL DEFAULT 0 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE UNIQUE INDEX `idx_endpoint` ON `integration_service` (`endpoint` ASC);


/******************************************/
/*   数据库全名 = oceanstars_integration   */
/*   表名称 = 集成认证信息   */
/******************************************/
CREATE TABLE `integration_authentication`
(
    `id`              bigint       NOT NULL COMMENT '物理PK-代理主键',
    `appKey`          varchar(255) COMMENT '集成目标服务认证用Key',
    `appSecret`       varchar(255) COMMENT '集成目标服务认证用秘钥（AES256加密保存）',
    `x509Certificate` blob COMMENT '集成目标服务认证证书',
    `create_by`       varchar(255) NOT NULL COMMENT '创建者',
    `create_at`       datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE UNIQUE INDEX `idx_appKey` ON `integration_authentication` (`appKey` ASC);
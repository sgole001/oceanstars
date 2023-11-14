/******************************************/
/*   数据库全名 = oceanstars_message   */
/*   表名称 = 消息事件元数据   */
/******************************************/
CREATE TABLE `message_event_meta`
(
    `id`        bigint       NOT NULL COMMENT '物理PK-代理主键',
    `code`      varchar(14)  NOT NULL COMMENT '消息配置编码-自然主键（EVENT-UUID第一部分（根据type和bus获取UUID））',
    `type`      varchar(255) NOT NULL COMMENT '消息类型',
    `bus`       smallint     NOT NULL COMMENT '消息总线类型：0:Spring,1:Kafka',
    `config`    blob         NOT NULL COMMENT '消息配置:序列化保存',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime     NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime     NOT NULL COMMENT '更新时间',
    `version`   int          NOT NULL DEFAULT 0 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_type` ON `message_event_meta` (`type` ASC);
CREATE INDEX `idx_bus` ON `message_event_meta` (`bus` ASC);
/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容管理(菜单)                 */
/******************************************/
CREATE TABLE `ecm_content_menu`
(
    `id`           bigint(0)    NOT NULL COMMENT 'id',
    `name`         varchar(128) NOT NULL COMMENT '菜单名称（字符类型：英数字，_,-）',
    `display_name` varchar(255) NOT NULL COMMENT '菜单展示名称',
    `description`  text(4096) COMMENT '菜单描述',
    `status`       smallint(0)  NOT NULL COMMENT '菜单状态: 0-草稿, 1-待审核, 2-审核通过, 3-审核不通过, 4-已发布, 5-已下架, 6-已删除',
    `type`         smallint(0)  NOT NULL COMMENT '菜单功能类型: 0-菜单, 1-功能',
    `icon`         varchar(255) COMMENT '菜单图标',
    `func`         bigint(0)    NOT NULL COMMENT '菜单功能ID',
    `action`       text(4096) COMMENT '菜单动作：点击菜单后的执行脚本',
    `parent`       bigint(0) COMMENT '菜单隶属',
    `visible`      tinyint      NOT NULL DEFAULT 1 COMMENT '菜单是否可见：0-不可见，1-可见',
    `prev`         bigint(0) COMMENT '相邻（前）菜单ID',
    `next`         bigint(0) COMMENT '相邻（后）菜单ID',
    `seq`          bigint(0)    NOT NULL DEFAULT 0 COMMENT '相同隶属下菜单排序序列号（升序）',
    `create_by`    varchar(255) NOT NULL COMMENT '创建者',
    `create_at`    datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`    varchar(255) NOT NULL COMMENT '更新者',
    `update_at`    datetime(0)  NOT NULL COMMENT '更新时间',
    `version`      int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_menu_identifier` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE FULLTEXT INDEX `idx_content_menu_name` ON `ecm_content_menu` (`name`);
CREATE FULLTEXT INDEX `idx_content_menu_display_name` ON `ecm_content_menu` (`display_name`);
CREATE INDEX `idx_content_menu_status` ON `ecm_content_menu` (`status` ASC);
CREATE INDEX `idx_content_menu_type` ON `ecm_content_menu` (`type` ASC);
CREATE INDEX `idx_content_menu_func` ON `ecm_content_menu` (`func` ASC);
CREATE INDEX `idx_content_menu_parent` ON `ecm_content_menu` (`parent` ASC);
CREATE INDEX `idx_content_menu_prev` ON `ecm_content_menu` (`prev` ASC);
CREATE INDEX `idx_content_menu_next` ON `ecm_content_menu` (`next` ASC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容统计管理（统计日志）         */
/******************************************/
CREATE TABLE `ecm_content_statistics`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `content`   bigint(0)    NOT NULL COMMENT '内容ID',
    `type`      smallint(0)  NOT NULL COMMENT '内容类型',
    `stats`     smallint(0)  NOT NULL COMMENT '统计类型: 0(visits)-访问次数, 1(comments)-评论数量, 2(likes)-点赞数量, 3(dislikes)-点踩数量, 4(favorites)-收藏数量, 5(shares)-分享数量, 6(downloads)-下载数量',
    `value`     bigint(0)    NOT NULL DEFAULT 0 COMMENT '统计值',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_statistics_identifier` (`content`, `type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_statistics` ON `ecm_content_statistics` (`stats` DESC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 分类                         */
/******************************************/
CREATE TABLE `ecm_category`
(
    `id`           bigint(0)    NOT NULL COMMENT 'id',
    `name`         varchar(32)  NOT NULL COMMENT '分类名称-自然键',
    `display_name` varchar(255) NOT NULL COMMENT '分类展示名称',
    `description`  text(4096) COMMENT '分类描述',
    `url`          varchar(255) COMMENT '分类链接',
    `status`       smallint(0)  NOT NULL COMMENT '分类状态: 0-草稿, 1-待审核, 2-审核通过, 3-审核不通过, 4-已发布, 5-已下架, 6-已删除',
    `create_by`    varchar(255) NOT NULL COMMENT '创建者',
    `create_at`    datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`    varchar(255) NOT NULL COMMENT '更新者',
    `update_at`    datetime(0)  NOT NULL COMMENT '更新时间',
    `version`      int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_category_identifier` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_content_category_display_name` ON `ecm_category` (`display_name` ASC);
CREATE INDEX `idx_content_category_status` ON `ecm_category` (`status` ASC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 标签                         */
/******************************************/
CREATE TABLE `ecm_tag`
(
    `id`           bigint(0)    NOT NULL COMMENT 'id',
    `name`         varchar(32)  NOT NULL COMMENT '标签名称-自然键',
    `display_name` varchar(255) NOT NULL COMMENT '标签展示名称',
    `description`  text(4096) COMMENT '标签描述',
    `icon`         varchar(255) COMMENT '标签图标',
    `url`          varchar(255) COMMENT '标签链接',
    `status`       smallint(0)  NOT NULL COMMENT '标签状态: 0-草稿, 1-待审核, 2-审核通过, 3-审核不通过, 4-已发布, 5-已下架, 6-已删除',
    `create_by`    varchar(255) NOT NULL COMMENT '创建者',
    `create_at`    datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`    varchar(255) NOT NULL COMMENT '更新者',
    `update_at`    datetime(0)  NOT NULL COMMENT '更新时间',
    `version`      int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_tag_identifier` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_content_tag_display_name` ON `ecm_tag` (`display_name` ASC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 知识产权资产-功能列表           */
/******************************************/
CREATE TABLE `ecm_asset_ip_function`
(
    `id`          bigint(0)    NOT NULL COMMENT 'id',
    `name`        varchar(128) NOT NULL COMMENT '功能名称（字符类型：英数字，_,-）',
    `description` text(4096) COMMENT '功能描述',
    `status`      smallint(0)  NOT NULL COMMENT '功能状态: 0-已删除, 1-已发布',
    `parent`      bigint(0) COMMENT '功能隶属',
    `create_by`   varchar(255) NOT NULL COMMENT '创建者',
    `create_at`   datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`   varchar(255) NOT NULL COMMENT '更新者',
    `update_at`   datetime(0)  NOT NULL COMMENT '更新时间',
    `version`     int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_asset_identifier` (`name`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE FULLTEXT INDEX `idx_asset_ip_function_name` ON `ecm_asset_ip_function` (`name`);
CREATE INDEX `idx_asset_ip_function_create_at_status` ON `ecm_asset_ip_function` (`create_at` DESC, `status` ASC);
CREATE INDEX `idx_asset_ip_function_update_at_status` ON `ecm_asset_ip_function` (`update_at` DESC, `status` ASC);
CREATE INDEX `idx_asset_ip_function_parent` ON `ecm_asset_ip_function` (`parent` DESC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 分类隶属关系                  */
/******************************************/
CREATE TABLE `rel_category_category`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `category`  bigint(0)    NOT NULL COMMENT '分类ID',
    `parent`    bigint(0)    NOT NULL COMMENT '隶属分类ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_category_category` (`category`, `parent`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容和分类关联              */
/******************************************/
CREATE TABLE `rel_content_category`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `content`   bigint(0)    NOT NULL COMMENT '内容ID',
    `type`      smallint(0)  NOT NULL COMMENT '内容类型',
    `category`  bigint(0)    NOT NULL COMMENT '分类ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_content_category_identifier` (`content`, `type`, `category`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容和标签关联              */
/******************************************/
CREATE TABLE `rel_content_tag`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `content`   bigint(0)    NOT NULL COMMENT '内容ID',
    `type`      smallint(0)  NOT NULL COMMENT '内容类型',
    `tag`       bigint(0)    NOT NULL COMMENT '标签ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_content_tag_identifier` (`content`, `type`, `tag`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
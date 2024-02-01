/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容管理                      */
/******************************************/
CREATE TABLE `ecm_content`
(
    `id`           bigint(0)    NOT NULL COMMENT 'id',
    `code`         varchar(12)  NOT NULL COMMENT '内容编码-自然键',
    `name`         varchar(32)  NOT NULL COMMENT '内容名称',
    `type`         smallint(0)  NOT NULL COMMENT '内容类型',
    `display_name` varchar(255) NOT NULL COMMENT '内容展示名称',
    `description`  text(4096) COMMENT '内容描述',
    `visits`       bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容访问次数',
    `comments`     bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容评论数量',
    `likes`        bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容点赞数量',
    `dislikes`     bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容点踩数量',
    `favorites`    bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容收藏数量',
    `shares`       bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容分享数量',
    `downloads`    bigint(0)    NOT NULL DEFAULT 0 COMMENT '内容下载数量',
    `status`       smallint(0)  NOT NULL COMMENT '内容状态: 0-草稿, 1-待审核, 2-审核通过, 3-审核不通过, 4-已发布, 5-已下架, 6-已删除',
    `create_by`    varchar(255) NOT NULL COMMENT '创建者',
    `create_at`    datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by`    varchar(255) NOT NULL COMMENT '更新者',
    `update_at`    datetime(0)  NOT NULL COMMENT '更新时间',
    `version`      int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_identifier` (`code`) USING BTREE,
    UNIQUE INDEX `idx_content` (`name`, `type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_content_display_name` ON `ecm_content` (`display_name` ASC);
CREATE INDEX `idx_visits` ON `ecm_content` (`visits` ASC);
CREATE INDEX `idx_comments` ON `ecm_content` (`comments` ASC);
CREATE INDEX `idx_likes` ON `ecm_content` (`likes` ASC);
CREATE INDEX `idx_dislikes` ON `ecm_content` (`dislikes` ASC);
CREATE INDEX `idx_favorites` ON `ecm_content` (`favorites` ASC);
CREATE INDEX `idx_shares` ON `ecm_content` (`shares` ASC);
CREATE INDEX `idx_downloads` ON `ecm_content` (`downloads` ASC);
CREATE INDEX `idx_status` ON `ecm_content` (`status` ASC);

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容类型-WEB功能配置信息        */
/******************************************/
CREATE TABLE `ecm_content_web_function`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `cid`       bigint(0)    NOT NULL COMMENT '内容ID',
    `type`      smallint(0)  NOT NULL COMMENT 'Web功能类型: 0-PAGE, 1-INLINE PAGE, 2-HREF, 3-EVENT',
    `href`      varchar(255) COMMENT '功能跳转',
    `icon`      varchar(255) COMMENT '功能图标',
    `pid`       bigint(0)    NOT NULL COMMENT 'Web功能隶属 - 通过内容ID关联隶属关系',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    `update_by` varchar(255) NOT NULL COMMENT '更新者',
    `update_at` datetime(0)  NOT NULL COMMENT '更新时间',
    `version`   int(0)       NOT NULL DEFAULT 1 COMMENT '版本(乐观锁)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_content_web_function_identifier` (`cid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX `idx_content_web_function_type` ON `ecm_content_web_function` (`type` ASC);
CREATE INDEX `idx_content_web_function_parent` ON `ecm_content_web_function` (`pid` ASC);

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
/*   表名称 = 分类隶属关系                  */
/******************************************/
CREATE TABLE `rel_category_category`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `cid`       bigint(0)    NOT NULL COMMENT '分类ID',
    `pid`       bigint(0)    NOT NULL COMMENT '隶属分类ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_category_category` (`cid`, `pid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容和内容分类关系              */
/******************************************/
CREATE TABLE `rel_content_category`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `cid`       bigint(0)    NOT NULL COMMENT '内容ID',
    `catid`     bigint(0)    NOT NULL COMMENT '分类ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_content_category` (`cid`, `catid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/******************************************/
/*   数据库全名 = oceanstars_ecm           */
/*   表名称 = 内容和内容标签关联              */
/******************************************/
CREATE TABLE `rel_content_tag`
(
    `id`        bigint(0)    NOT NULL COMMENT 'id',
    `cid`       bigint(0)    NOT NULL COMMENT '内容ID',
    `tid`       bigint(0)    NOT NULL COMMENT '标签ID',
    `create_by` varchar(255) NOT NULL COMMENT '创建者',
    `create_at` datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_rel_content_tag_tag` (`cid`, `tid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
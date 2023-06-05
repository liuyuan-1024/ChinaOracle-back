-- 创建库
create database if not exists chinaoracle;

-- 切换库
use chinaoracle;

-- 角色表
CREATE TABLE `role`
(
    `id`          int(11)                                NOT NULL AUTO_INCREMENT comment '角色ID',
    `name`        varchar(50)                            NOT NULL comment '角色名称',
    `description` varchar(200) DEFAULT NULL comment '角色描述',
    `status`      tinyint(1)                             NOT NULL DEFAULT '1' comment '角色状态',
    `created_at`  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    `updated_at`  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_name` (`name`)
) comment '角色表'
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

insert into `role`(name, description)
values ('super_admin', '拥有系统绝对的管理权限'),
       ('admin', '拥有系统大多数管理权限'),
       ('user', '仅拥有对自己的管理权限');

-- 权限表
CREATE TABLE `permission`
(
    `id`          int(11)                                NOT NULL AUTO_INCREMENT comment '权限ID',
    `name`        varchar(50)                            NOT NULL comment '权限名称',
    `description` varchar(200) DEFAULT NULL comment '权限描述',
    `created_at`  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    `updated_at`  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_name` (`name`)
) comment '权限表'
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

-- 角色权限连接表
CREATE TABLE `role_permission`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `role_id`       int(11) NOT NULL,
    `permission_id` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_role_permission_role_id` (`role_id`),
    CONSTRAINT `fk_role_permission_role_id` FOREIGN KEY (`role_id`)
        REFERENCES `role` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_role_permission_permission_id` FOREIGN KEY (`permission_id`)
        REFERENCES `permission` (`id`) ON DELETE CASCADE
) comment '角色权限连接表'
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;


-- 用户表（软删除）
create table if not exists `user`
(
    `id`         bigint                               not null auto_increment comment '用户ID',
    `email`      varchar(256)                         not null comment '邮箱',
    `password`   varchar(256)                         not null comment '密码',
    `nick_name`  varchar(256)                         null comment '昵称',
    `avatar`     varchar(1024)                        null comment '头像',
    `profile`    varchar(512)                         null comment '简介',
    `role`       int(11)                              not null comment '权限等级, 数值越大权限越大',
    `is_ban`     tinyint(1) default 0                 not null comment '0-未封禁 1-被封禁',
    `created_at` datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    `updated_at` datetime   default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    `is_deleted` tinyint(1) default 0                 not null comment '是否已删除(0-未删除 1-已删除)',
    primary key (`id`),
    CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE CASCADE
) comment '用户表'
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;


-- 编程语言标签表（软删除）
create table if not exists `language`
(
    `id`         int(11)                                not null auto_increment comment '编程语言标签ID',
    `name`       varchar(64) default '编程语言标签名称' not null comment '编程语言标签名称',
    `creator_id` bigint      default 1                  not null comment '创建人',
    `created_at` datetime    default CURRENT_TIMESTAMP  not null comment '创建时间',
    `updated_at` datetime    default CURRENT_TIMESTAMP  not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    `is_deleted` tinyint(1)  default 0                  not null comment '是否已删除(0-未删除 1-已删除)',
    primary key (`id`),
    foreign key (creator_id) references `user` (id) on update cascade
) comment '编程语言标签表',
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

insert into `language` (`id`, `name`) value (1, '未知语言');


-- 开源许可证类型表（软删除）
create table if not exists `license`
(
    `id`          int(11)                                    not null auto_increment comment '许可证ID',
    `name`        varchar(128) default '许可证名称'          not null comment '许可证名称',
    `url`         varchar(256) default '许可证文本的URL地址' not null comment '许可证文本的URL地址',
    `description` text                                       not null comment '许可证的描述信息',
    `creator_id`  bigint       default 1                     not null comment '创建人',
    `created_at`  datetime     default CURRENT_TIMESTAMP     not null comment '创建时间',
    `updated_at`  datetime     default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    `is_deleted`  tinyint(1)   default 0                     not null comment '是否已删除(0-未删除 1-已删除)',
    primary key (`id`),
    foreign key (creator_id) references `user` (id) on update cascade
) comment '开源许可证类型表',
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

insert into `license` (`id`, `name`, `url`, `description`)
    value (1, 'GPL', 'https://www.gnu.org/licenses/gpl-3.0.txt',
           '默认开源许可证');


-- 代码仓库表（软删除）
create table if not exists `repository`
(
    `id`          bigint                                 not null auto_increment comment '仓库ID',
    `name`        varchar(256) default '仓库名称'        not null comment '仓库名称',
    `description` text                                   not null comment '仓库的描述信息',
    `owner_id`    bigint                                 not null comment '仓库所有者的ID',
    `created_at`  datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    `updated_at`  datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    `language_id` int(11)      default 1                 not null comment '仓库主要使用的编程语言',
    `stars`       int(11)      default 0                 not null comment '仓库的星标数',
    `forks`       int(11)      default 0                 not null comment '仓库的分支数',
    `readme`      text                                   not null comment '仓库的README文件内容',
    `license_id`  int(11)      default 1                 not null comment '开源许可证类型',
    `is_private`  tinyint(1)   default 0                 not null comment '是否为私有仓库(0-公开 1-私有)',
    `is_deleted`  tinyint(1)   default 0                 not null comment '是否已删除(0-未删除 1-已删除)',
    primary key (`id`),
    foreign key (owner_id) references `user` (id) on update cascade,
    foreign key (language_id) references `language` (id) on update cascade,
    foreign key (license_id) references `license` (id) on update cascade,
    key `idx_owner_id` (`owner_id`),
    key `idx_stars` (`stars`),
    key `idx_forks` (`forks`)
) comment '代码仓库表'
    engine = InnoDB
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

insert into `repository` (`name`, `description`, `owner_id`, `readme`) value ('测试仓库', '测试仓库', 1, '测试仓库');

-- 仓库star表（硬删除）
create table if not exists `star`
(
    `id`            bigint auto_increment comment 'id',
    `user_id`       bigint                             not null comment '用户id',
    `repository_id` bigint                             not null comment '仓库id',
    `created_at`    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updated_at`    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后更新时间',
    primary key (id),
    foreign key (user_id) references `user` (id) on update cascade,
    foreign key (repository_id) references `repository` (id) on update cascade,
    index idx_userId (user_id),
    index idx_repositoryId (repository_id)
) comment '仓库点赞表';


-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表(json 数组)',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';
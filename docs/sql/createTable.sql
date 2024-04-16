create database my_im;

use my_im;

create table user
(
    id            bigint unsigned auto_increment comment '用户id'
        primary key,
    name          varchar(20)                              null comment '用户昵称',
    avatar        varchar(255)                             null comment '用户头像',
    sex           int                                      null comment '性别 1为男性，2为女性',
    phone       char(32)                                 not null comment '唯一电话',
    status        int         default 0                    null comment '使用状态 0.正常 1拉黑',
    create_time   datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    update_time   datetime(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '修改时间',
    is_deleted tinyint default 0 not null comment '逻辑删除',
    constraint uniq_name
        unique (name),
    constraint uniq_phone
        unique (phone)
)
    comment '用户表' collate = utf8mb4_unicode_ci
                     row_format = DYNAMIC;
create index idx_create_time
    on user (create_time);

create index idx_update_time
    on user (update_time);

CREATE TABLE `user_apply` (
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
 `uid` bigint(20) NOT NULL COMMENT '申请人uid',
 `target_id` bigint(20) NOT NULL COMMENT '接收人uid',
 `msg` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '申请信息',
 `is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
 `read_status` int(11) NOT NULL COMMENT '阅读状态 1未读 2已读',
 `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
 `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_uid_target_id` (`uid`,`target_id`) USING BTREE,
 KEY `idx_target_id` (`target_id`) USING BTREE,
 KEY `idx_create_time` (`create_time`) USING BTREE,
 KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户申请表';

CREATE TABLE `user_friend` (
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
 `uid` bigint(20) NOT NULL COMMENT 'uid',
 `friend_uid` bigint(20) NOT NULL COMMENT '好友uid',
 `is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
 `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
 `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_uid_friend_uid` (`uid`,`friend_uid`) USING BTREE,
 KEY `idx_create_time` (`create_time`) USING BTREE,
 KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户联系人表';

CREATE TABLE `room` (
  `id` bigint(32) unsigned NOT NULL COMMENT '房间id',
  `type` tinyint not null comment '房间类型 1代表单聊 2代表群聊',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  `is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='房间表';


CREATE TABLE `message` (
    `id` bigint(32) unsigned NOT NULL COMMENT '消息id',
    `type` tinyint default 1 not null comment '消息类型 1为文本类 2为图片类',
    `room_id` tinyint not null comment '房间id',
    `from_uid` bigint(32) unsigned NOT NULL COMMENT '发送者uid',
    `content` varchar(1024) default null comment '消息内容',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
    `is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_create_time` (`create_time`) USING BTREE,
    KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='消息表';


CREATE TABLE `room_friend` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
`room_id` bigint(20) NOT NULL COMMENT '房间id',
`uid1` bigint(20) NOT NULL COMMENT 'uid1（更小的uid）',
`uid2` bigint(20) NOT NULL COMMENT 'uid2（更大的uid）',
`room_key` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房间key由两个uid拼接，先做排序uid1_uid2',
`create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
`update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
`is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
PRIMARY KEY (`id`) USING BTREE,
UNIQUE KEY `room_key` (`room_key`) USING BTREE,
KEY `idx_room_id` (`room_id`) USING BTREE,
KEY `idx_create_time` (`create_time`) USING BTREE,
KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='单聊房间表';

CREATE TABLE `room_group` (
`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
`room_id` bigint(20) NOT NULL COMMENT '房间id',
`name` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '群名称',
`avatar` varchar(256) COLLATE utf8mb4_unicode_ci default NULL COMMENT '群头像',
`is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
`create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
`update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_room_id` (`room_id`) USING BTREE,
KEY `idx_create_time` (`create_time`) USING BTREE,
KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群聊房间表';


CREATE TABLE `group_member` (
 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
 `group_id` bigint(20) NOT NULL COMMENT '群组id',
 `uid` bigint(20) NOT NULL COMMENT '成员uid',
 `role` int(11) NOT NULL COMMENT '成员角色 1群主 2管理员 3普通成员',
 `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
 `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
 `is_delete` tinyint DEFAULT 0 COMMENT '逻辑删除',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_group_id_role` (`group_id`,`role`) USING BTREE,
 KEY `idx_create_time` (`create_time`) USING BTREE,
 KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群成员表';


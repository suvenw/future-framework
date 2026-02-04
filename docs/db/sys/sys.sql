-- api_sys.sys_depart definition

CREATE TABLE `sys_depart` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                              `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                              `status` int DEFAULT '0' COMMENT '状态（1启用，0不启用）',
                              `sort` int DEFAULT '0' COMMENT '菜单排序,默认按升级排序',
                              `parent_id` bigint DEFAULT NULL COMMENT '父机构ID',
                              `depart_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构/部门名称',
                              `depart_name_en` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '英文名',
                              `depart_name_abbr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '缩写',
                              `description` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '描述',
                              `org_type` int DEFAULT NULL COMMENT '机构类型 1一级部门 2子部门',
                              `org_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码',
                              `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
                              `fax` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '传真',
                              `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
                              `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `index_depart_parent_id` (`parent_id`) USING BTREE,
                              KEY `index_depart_org_code` (`org_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COMMENT='部门组织机构表';


-- api_sys.sys_permission definition

CREATE TABLE `sys_permission` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                  `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                                  `status` int DEFAULT '0' COMMENT '按钮权限状态(0无效1有效)',
                                  `sort` int DEFAULT '0' COMMENT '菜单排序,默认按升级排序',
                                  `parent_id` bigint DEFAULT '0' COMMENT '父id',
                                  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单标题',
                                  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径',
                                  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件',
                                  `component_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件名字',
                                  `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一级菜单跳转地址',
                                  `menu_type` int DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
                                  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限编码',
                                  `perms_type` int DEFAULT '0' COMMENT '权限策略1显示2禁用',
                                  `always_show` int DEFAULT NULL COMMENT '聚合子路由: 1是0否',
                                  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
                                  `is_route` int DEFAULT '1' COMMENT '是否路由菜单: 0:不是  1:是（默认值1）',
                                  `is_leaf` int DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
                                  `keep_alive` int DEFAULT NULL COMMENT '是否缓存该页面:    1:是   0:不是',
                                  `hidden` int DEFAULT '0' COMMENT '是否隐藏路由: 0否,1是',
                                  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                  `del_flag` int DEFAULT '0' COMMENT '删除状态 0正常 1已删除',
                                  `rule_flag` int DEFAULT '0' COMMENT '是否添加数据权限1是0否',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `index_prem_pid` (`parent_id`) USING BTREE,
                                  KEY `index_prem_is_route` (`is_route`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';


-- api_sys.sys_permission_copy1 definition

CREATE TABLE `sys_permission_copy1` (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                        `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                                        `status` int DEFAULT '0' COMMENT '按钮权限状态(0无效1有效)',
                                        `sort` int DEFAULT '0' COMMENT '菜单排序,默认按升级排序',
                                        `parent_id` bigint DEFAULT '0' COMMENT '父id',
                                        `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单标题',
                                        `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径',
                                        `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件',
                                        `component_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组件名字',
                                        `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一级菜单跳转地址',
                                        `menu_type` int DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',
                                        `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限编码',
                                        `perms_type` int DEFAULT '0' COMMENT '权限策略1显示2禁用',
                                        `always_show` int DEFAULT NULL COMMENT '聚合子路由: 1是0否',
                                        `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
                                        `is_route` int DEFAULT '1' COMMENT '是否路由菜单: 0:不是  1:是（默认值1）',
                                        `is_leaf` int DEFAULT NULL COMMENT '是否叶子节点:    1:是   0:不是',
                                        `keep_alive` int DEFAULT NULL COMMENT '是否缓存该页面:    1:是   0:不是',
                                        `hidden` int DEFAULT '0' COMMENT '是否隐藏路由: 0否,1是',
                                        `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                                        `del_flag` int DEFAULT '0' COMMENT '删除状态 0正常 1已删除',
                                        `rule_flag` int DEFAULT '0' COMMENT '是否添加数据权限1是0否',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        KEY `index_prem_pid` (`parent_id`) USING BTREE,
                                        KEY `index_prem_is_route` (`is_route`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';


-- api_sys.sys_permission_data_rule definition

CREATE TABLE `sys_permission_data_rule` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                            `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                            `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
                                            `status` int DEFAULT NULL COMMENT '权限有效状态1有0否',
                                            `sort` int DEFAULT '0' COMMENT '菜单排序,默认按升级排序',
                                            `permission_id` bigint DEFAULT NULL COMMENT '菜单ID',
                                            `rule_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则名称',
                                            `rule_column` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段',
                                            `rule_conditions` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '条件',
                                            `rule_value` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则值',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `index_fucntionid` (`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;


-- api_sys.sys_role definition

CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                            `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                            `status` int DEFAULT NULL COMMENT '删除状态(1-正常,0-已删除)',
                            `sort` int DEFAULT NULL COMMENT '排序字段,默认按升级排序',
                            `role_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
                            `role_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
                            `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `index_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8mb3 COMMENT='角色表';


-- api_sys.sys_role_permission definition

CREATE TABLE `sys_role_permission` (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                       `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                       `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                                       `role_id` bigint DEFAULT NULL COMMENT '角色id',
                                       `permission_id` bigint DEFAULT NULL COMMENT '权限id',
                                       `data_rule_ids` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据权限',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `index_group_role_per_id` (`role_id`,`permission_id`) USING BTREE,
                                       KEY `index_group_role_id` (`role_id`) USING BTREE,
                                       KEY `index_group_per_id` (`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8mb3 COMMENT='角色权限表';


-- api_sys.sys_user definition

CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户基础表',
                            `status` int DEFAULT NULL COMMENT '状态：0：下架，1：上架',
                            `create_date` datetime DEFAULT NULL COMMENT '创建日期，时间截',
                            `modify_date` datetime DEFAULT NULL COMMENT '修改日期，时间截',
                            `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
                            `user_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '自定义账号',
                            `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
                            `user_head_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
                            `we_chat_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称',
                            `we_chat_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称图片',
                            `sex` int DEFAULT '0' COMMENT '性别：0.不详，1.男，2.女',
                            `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
                            `birthday_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生日类型：1.新历，2.农历',
                            `birthday` datetime DEFAULT NULL COMMENT '生日日期',
                            `store_id` bigint DEFAULT NULL COMMENT '归属门店id',
                            `follow_user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '美丽顾问Id',
                            `qr_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '二维码信息',
                            `remarks` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户简介',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户基础信息表';


-- api_sys.sys_user_depart definition

CREATE TABLE `sys_user_depart` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                   `dep_id` bigint DEFAULT NULL COMMENT '部门id',
                                   `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                   `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                                   `status` int DEFAULT NULL,
                                   `sort` int DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3 COMMENT='用户部门表';


-- api_sys.sys_user_role definition

CREATE TABLE `sys_user_role` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                 `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                                 `user_id` bigint DEFAULT NULL COMMENT '用户id',
                                 `role_id` bigint DEFAULT NULL COMMENT '角色id',
                                 PRIMARY KEY (`id`) USING BTREE,
                                 KEY `index_group_user_id` (`user_id`) USING BTREE,
                                 KEY `index_group_role_id` (`role_id`) USING BTREE,
                                 KEY `index_group_userid_roleid` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=422 DEFAULT CHARSET=utf8mb3 COMMENT='用户角色表';


-- api_sys.user_info definition

CREATE TABLE `user_info` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户基础表',
                             `status` int DEFAULT NULL COMMENT '状态：0：下架，1：上架',
                             `create_date` datetime DEFAULT NULL COMMENT '创建日期，时间截',
                             `modify_date` datetime DEFAULT NULL COMMENT '修改日期，时间截',
                             `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
                             `user_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '自定义账号',
                             `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
                             `user_head_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
                             `we_chat_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称',
                             `we_chat_img` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信昵称图片',
                             `sex` int DEFAULT '0' COMMENT '性别：0.不详，1.男，2.女',
                             `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话号码',
                             `birthday_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生日类型：1.新历，2.农历',
                             `birthday` datetime DEFAULT NULL COMMENT '生日日期',
                             `store_id` bigint DEFAULT NULL COMMENT '归属门店id',
                             `follow_user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '美丽顾问Id',
                             `qr_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '二维码信息',
                             `remarks` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户简介',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户基础信息表';
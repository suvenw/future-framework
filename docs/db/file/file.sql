-- api_file.file_app_storage_config definition

CREATE TABLE `file_app_storage_config` (
                                           `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                           `app_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用名称',
                                           `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id',
                                           `storage_config_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id,对应FileStorageEnum',
                                           `config_filed_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '动态配置,业务自定义自段名;1.本地;basePath ',
                                           `config_filed_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '动态配置,业务自定义自段名值',
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                           `create_date` datetime NOT NULL COMMENT '创建时间',
                                           `modify_date` datetime NOT NULL COMMENT '修改时间',
                                           `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                           `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.file_data_detailed definition

CREATE TABLE `file_data_detailed` (
                                      `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                      `app_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用名称',
                                      `client_id` bigint DEFAULT NULL COMMENT '注册应用授权Id',
                                      `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务公司id',
                                      `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务公司名称',
                                      `file_product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务产品名称',
                                      `file_business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务名称',
                                      `use_business_id` bigint DEFAULT NULL COMMENT '使用业务Id',
                                      `storage_config_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id,对应 FileStorageEnum',
                                      `file_source_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,原来文件上传的名称',
                                      `file_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称类型:txt, xml,jpg 等',
                                      `error_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '检查数据错误提示编码',
                                      `error_message` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '检查数据错误提示信息',
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                      `create_date` datetime NOT NULL COMMENT '创建时间',
                                      `modify_date` datetime NOT NULL COMMENT '修改时间',
                                      `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                      `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.file_upload_action_water definition

CREATE TABLE `file_upload_action_water` (
                                            `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务公司id',
                                            `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务公司名称',
                                            `dept_id` bigint DEFAULT NULL COMMENT '业务公司人员的部门id',
                                            `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务公司人员的部门名称',
                                            `upload_user_id` bigint DEFAULT NULL COMMENT '上传人员的id',
                                            `upload_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传人员的名称',
                                            `file_product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务产品名称',
                                            `file_business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务名称',
                                            `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                            `client_id` bigint DEFAULT NULL COMMENT '注册应用授权Id',
                                            `use_business_id` bigint DEFAULT NULL COMMENT '使用业务Id',
                                            `storage_config_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id,对应 FileStorageEnum',
                                            `file_upload_storage_id` bigint DEFAULT NULL COMMENT '文件存储信息id',
                                            `file_source_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,原来文件上传的名称',
                                            `file_oss_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,存储到中间件名称,eg, abc.jpg ',
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                            `create_date` datetime NOT NULL COMMENT '创建时间',
                                            `modify_date` datetime NOT NULL COMMENT '修改时间',
                                            `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                            `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.file_upload_app definition

CREATE TABLE `file_upload_app` (
                                   `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                   `app_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用名称',
                                   `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id',
                                   `client_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权密匙',
                                   `client_salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权加密干扰盐',
                                   `path_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用业务前缀path,用于区别业务',
                                   `client_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用回调地址',
                                   `access_expiration_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效时间:当前时间+毫秒数',
                                   `access_count` int DEFAULT NULL COMMENT 'url授权可访问次数',
                                   `access_url_format` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成临时访问url规则',
                                   `access_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成临时访问url',
                                   `file_app_storage_config_id` bigint DEFAULT NULL COMMENT '文件存储配置id',
                                   `endpoint` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '节点地址,默认值',
                                   `bucket` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'bucket,默认值',
                                   `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'accessKey 不能为空,默认值',
                                   `access_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'accessSecret 不能为空,默认值',
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                   `create_date` datetime NOT NULL COMMENT '创建时间',
                                   `modify_date` datetime NOT NULL COMMENT '修改时间',
                                   `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                   `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.file_upload_storage definition

CREATE TABLE `file_upload_storage` (
                                       `idempotent` bigint DEFAULT NULL,
                                       `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                       `client_id` bigint DEFAULT NULL COMMENT '注册应用授权Id',
                                       `use_business_id` bigint DEFAULT NULL COMMENT '使用业务Id',
                                       `storage_config_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id,对应 FileStorageEnum',
                                       `file_source_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,原来文件上传的名称',
                                       `file_md5` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件md5字节流,长度大于128,建议使用前128个节,否则前部进行md5加密,防止文件重复上传',
                                       `file_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称类型:txt, xml,jpg 等',
                                       `file_size` bigint DEFAULT NULL COMMENT '文件大小',
                                       `bucket_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件存储的桶的名称',
                                       `oss_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件存储的桶的key',
                                       `file_oss_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,存储到中间件名称,eg, abc.jpg ',
                                       `file_oss_store_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称,存储到中间件名称',
                                       `file_domain` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件访问代理域名前缀 https://file.aiin.vip',
                                       `file_access_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件访问完整域名,由 fileDomain+fileOssStorePath; == https://file.aiin.vip/img/abc.jpg',
                                       `access_expiration_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效时间:当前时间+毫秒数,默认按app business中,也可以独立配置,优先考虑个性设置',
                                       `access_count` int DEFAULT NULL COMMENT 'url授权可访问次数,默认按app business中,也可以独立配置,优先考虑个性设置',
                                       `access_url_format` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成临时访问url规则',
                                       `callback_validate` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求接口,回调返回的参数字符串,用于回调业务安全验证',
                                       `interpret_data` int DEFAULT NULL COMMENT '是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否',
                                       `interpret_data_total` int DEFAULT NULL COMMENT 'interpretData 为1时有时,默认为0,对应存储到mongodb记录总条数据',
                                       `file_history` int DEFAULT NULL COMMENT '是否将修改验证后的正确数据,生成文件替换历史文件,0.未替换,1.替换,对应fileHistoryStorePath存历史url,其它对应字段为新文件信息',
                                       `file_history_store_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否将修改验证后的正确数据,生成文件替换历史文件',
                                       `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                       `create_date` datetime NOT NULL COMMENT '创建时间',
                                       `modify_date` datetime NOT NULL COMMENT '修改时间',
                                       `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                       `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.file_upload_use_business definition

CREATE TABLE `file_upload_use_business` (
                                            `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用id',
                                            `client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '注册应用授权Id',
                                            `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                            `company_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                                            `file_product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务产品名称',
                                            `file_business_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上传业务名称',
                                            `callback_service` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式;',
                                            `callback_async_service` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方;',
                                            `data_form` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回调数据格式, 如何为空时,post json 请求,否则to map<key,Value> 结果处理数据 ',
                                            `form_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回调数据格式 post/get',
                                            `interpret_data` int DEFAULT NULL COMMENT '是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中',
                                            `check_data` int DEFAULT NULL COMMENT '这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要',
                                            `access_expiration_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效时间:当前时间+毫秒数,默认按app设计,也可以独立配置',
                                            `access_count` int DEFAULT NULL COMMENT 'url授权可访问次数,默认按app设计,也可以独立配置',
                                            `access_url_format` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成临时访问url规则',
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对应的业务主键值',
                                            `create_date` datetime NOT NULL COMMENT '创建时间',
                                            `modify_date` datetime NOT NULL COMMENT '修改时间',
                                            `tenant_id` bigint DEFAULT NULL COMMENT '租户ID,租户主键值',
                                            `deleted` int DEFAULT NULL COMMENT '租户ID,租户主键值',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- api_file.salary_app_key definition

CREATE TABLE `salary_app_key` (
                                  `Id` bigint NOT NULL,
                                  `app_id` bigint DEFAULT NULL,
                                  `company_id` varchar(100) DEFAULT NULL,
                                  `tenant_id` varchar(100) DEFAULT NULL,
                                  `secret` varchar(100) DEFAULT NULL,
                                  `is_enable` int DEFAULT NULL,
                                  `deleted` int DEFAULT NULL,
                                  `create_by` varchar(100) DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL,
                                  `update_by` varchar(100) DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  UNIQUE KEY `salary_app_key_Id_IDX` (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
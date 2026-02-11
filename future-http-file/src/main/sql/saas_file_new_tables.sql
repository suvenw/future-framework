-- ============================================================
-- SaaS平台文件上传与解释回调系统 - 数据库表结构设计
-- 设计日期: 2026-02-11
-- ============================================================

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 表1: SaaS公司业务功能信息表
-- 功能: 记录租户/公司维度下的业务上传功能配置
-- ============================================================
DROP TABLE IF EXISTS `saas_company_business_function`;
CREATE TABLE `saas_company_business_function` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
    
    -- 公司/租户信息
    `company_id` varchar(64) DEFAULT NULL COMMENT '公司ID',
    `company_name` varchar(128) DEFAULT NULL COMMENT '公司名称',
    
    -- 业务配置信息
    `platform_type` varchar(32) NOT NULL COMMENT '平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口',
    `business_code` varchar(64) NOT NULL COMMENT '业务编码',
    `business_name` varchar(128) NOT NULL COMMENT '业务名称',
    `function_type` varchar(32) NOT NULL COMMENT '功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载',
    
    -- 回调配置信息
    `business_unique_code` varchar(128) NOT NULL COMMENT '申请的业务唯一码，用于在上传/回调流程中与解释记录串联',
    `callback_url` varchar(512) DEFAULT NULL COMMENT '业务回调地址，用于通知业务方文件解释完成及处理进度',
    `query_url` varchar(512) DEFAULT NULL COMMENT '业务查询地址，业务方根据此地址分批查询解释后的数据',
    `access_method` varchar(16) DEFAULT 'POST' COMMENT '访问方式: GET, POST, PUT, DELETE',
    
    -- 状态信息
    `status` varchar(16) DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE-启用, INACTIVE-停用',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注信息',
    
    -- 通用字段
    `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
    `create_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `modify_id` bigint DEFAULT NULL COMMENT '修改人ID',
    `modify_name` varchar(64) DEFAULT NULL COMMENT '修改人名称',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    `deleted` int DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    `version` int DEFAULT 0 COMMENT '版本号',
    
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_business_unique_code` (`business_unique_code`),
    UNIQUE KEY `uk_company_business_function` (`tenant_id`, `company_id`, `business_code`, `function_type`),
    KEY `idx_company_id` (`company_id`),
    KEY `idx_function_type` (`function_type`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='SaaS公司业务功能信息表';

-- ============================================================
-- 表2: 业务功能中文与英文关系维护表
-- 功能: 记录文件上传字段英文名和中文名称和对应排编号
-- ============================================================
DROP TABLE IF EXISTS `saas_file_field_mapping`;
CREATE TABLE `saas_file_field_mapping` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
    
    -- 关联信息
    `business_function_id` bigint DEFAULT NULL COMMENT '业务功能配置ID，关联saas_company_business_function表',
    
    -- 字段信息
    `field_english_name` varchar(128) NOT NULL COMMENT '字段英文名称',
    `field_chinese_name` varchar(128) NOT NULL COMMENT '字段中文名称',
    `sort_order` int NOT NULL DEFAULT 0 COMMENT '排编号，用于排序和定位',
    `field_type` varchar(32) DEFAULT NULL COMMENT '字段数据类型: STRING-字符串, NUMBER-数字, DATE-日期, BOOLEAN-布尔值',
    `field_length` int DEFAULT NULL COMMENT '字段长度或精度',
    `is_primary_key` tinyint DEFAULT 0 COMMENT '是否为主键: 0-否, 1-是',
    `is_required` tinyint DEFAULT 0 COMMENT '是否必填: 0-否, 1-是',
    `default_value` varchar(256) DEFAULT NULL COMMENT '默认值',
    `field_description` varchar(512) DEFAULT NULL COMMENT '字段描述',
    
    -- 验证规则
    `validate_rule` varchar(512) DEFAULT NULL COMMENT '字段格式验证规则',
    `transform_rule` varchar(512) DEFAULT NULL COMMENT '字段转换规则(正则表达式或转换代码)',
    `sample_value` varchar(512) DEFAULT NULL COMMENT '字段示例值',
    
    -- 统计信息
    `total_count` int DEFAULT 0 COMMENT '字段对应的数据总条数',
    `not_null_count` int DEFAULT 0 COMMENT '字段非空数据条数',
    `null_count` int DEFAULT 0 COMMENT '字段空值数据条数',
    `duplicate_count` int DEFAULT 0 COMMENT '字段重复值数量',
    
    -- 状态信息
    `status` varchar(16) DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE-激活, INACTIVE-禁用, DELETED-已删除',
    `remark` varchar(512) DEFAULT NULL COMMENT '备注',
    
    -- 通用字段
    `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
    `create_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `modify_id` bigint DEFAULT NULL COMMENT '修改人ID',
    `modify_name` varchar(64) DEFAULT NULL COMMENT '修改人名称',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    `deleted` int DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    `version` int DEFAULT 0 COMMENT '版本号',
    
    PRIMARY KEY (`id`),
    KEY `idx_business_function_id` (`business_function_id`),
    KEY `idx_field_english_name` (`field_english_name`),
    KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='业务功能中文与英文关系维护表';

-- ============================================================
-- 表3: 业务文件上传文件表
-- 功能: 实现具体业务上传文件管理
-- ============================================================
DROP TABLE IF EXISTS `saas_file_upload`;
CREATE TABLE `saas_file_upload` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
    
    -- 关联信息
    `business_function_id` bigint DEFAULT NULL COMMENT '业务功能配置ID，关联saas_company_business_function表',
    `field_mapping_id` bigint DEFAULT NULL COMMENT '字段映射ID，关联saas_file_field_mapping表',
    
    -- 业务信息
    `business_unique_code` varchar(128) DEFAULT NULL COMMENT '业务唯一码，关联业务功能配置',
    `upload_batch_no` varchar(64) DEFAULT NULL COMMENT '上传批次号',
    
    -- 上传用户信息
    `upload_user_id` bigint DEFAULT NULL COMMENT '上传人员ID',
    `upload_user_name` varchar(64) DEFAULT NULL COMMENT '上传人员名称',
    `upload_dept_id` bigint DEFAULT NULL COMMENT '上传人员部门ID',
    `upload_dept_name` varchar(128) DEFAULT NULL COMMENT '上传人员部门名称',
    
    -- 文件信息
    `file_source_name` varchar(256) NOT NULL COMMENT '原始文件名称',
    `file_storage_name` varchar(256) DEFAULT NULL COMMENT '存储文件名称',
    `file_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
    `file_size` bigint DEFAULT NULL COMMENT '文件大小(字节)',
    `file_md5` varchar(64) DEFAULT NULL COMMENT '文件MD5值',
    `file_path` varchar(512) DEFAULT NULL COMMENT '文件存储路径',
    `file_storage_id` bigint DEFAULT NULL COMMENT '文件存储记录ID',
    
    -- 解释标识
    `interpret_flag` tinyint DEFAULT 0 COMMENT '是否需要解释: 0-否, 1-是',
    `interpret_status` varchar(32) DEFAULT NULL COMMENT '解释状态: PENDING-待解释, PROCESSING-解释中, COMPLETED-已解释, FAILED-解释失败',
    `interpret_key` varchar(128) DEFAULT NULL COMMENT '解释标识，用于关联解释记录',
    
    -- 处理状态
    `status` varchar(32) DEFAULT 'PENDING' COMMENT '处理状态: PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, FAILED-失败',
    `progress_percent` int DEFAULT 0 COMMENT '处理进度百分比 0-100',
    
    -- 统计信息
    `total_count` int DEFAULT 0 COMMENT '文件总记录数',
    `success_count` int DEFAULT 0 COMMENT '成功处理数',
    `fail_count` int DEFAULT 0 COMMENT '失败处理数',
    `skip_count` int DEFAULT 0 COMMENT '跳过处理数',
    
    -- 消息信息
    `message` varchar(1024) DEFAULT NULL COMMENT '处理说明',
    `error_message` varchar(2048) DEFAULT NULL COMMENT '错误信息',
    
    -- 回调配置
    `need_callback` tinyint DEFAULT 0 COMMENT '是否需要回调: 0-否, 1-是',
    `callback_url` varchar(512) DEFAULT NULL COMMENT '回调URL',
    `callback_relation_key` varchar(128) DEFAULT NULL COMMENT '文件业务回调关系唯一标识',
    `callback_status` varchar(32) DEFAULT NULL COMMENT '回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败',
    `callback_fail_count` int DEFAULT 0 COMMENT '回调失败次数',
    `last_callback_time` datetime DEFAULT NULL COMMENT '最后回调时间',
    `callback_response` varchar(2048) DEFAULT NULL COMMENT '回调响应结果',
    
    -- 业务处理结果
    `business_process_status` varchar(32) DEFAULT NULL COMMENT '业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败',
    `business_process_result` varchar(2048) DEFAULT NULL COMMENT '业务处理结果描述',
    `business_exception_info` varchar(2048) DEFAULT NULL COMMENT '业务处理异常信息',
    `business_process_time` datetime DEFAULT NULL COMMENT '业务处理时间',
    
    -- 通用字段
    `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
    `create_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `modify_id` bigint DEFAULT NULL COMMENT '修改人ID',
    `modify_name` varchar(64) DEFAULT NULL COMMENT '修改人名称',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    `deleted` int DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    `version` int DEFAULT 0 COMMENT '版本号',
    
    PRIMARY KEY (`id`),
    KEY `idx_business_function_id` (`business_function_id`),
    KEY `idx_business_unique_code` (`business_unique_code`),
    KEY `idx_upload_batch_no` (`upload_batch_no`),
    KEY `idx_interpret_flag` (`interpret_flag`),
    KEY `idx_interpret_status` (`interpret_status`),
    KEY `idx_status` (`status`),
    KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='业务文件上传文件表';

-- ============================================================
-- 表4: 文件解释记录明细表
-- 功能: 记录文件解释的详细信息，包含解释信息JSON
-- ============================================================
DROP TABLE IF EXISTS `saas_file_interpret_record`;
CREATE TABLE `saas_file_interpret_record` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id` bigint DEFAULT NULL COMMENT '租户ID',
    
    -- 关联信息
    `file_upload_id` bigint NOT NULL COMMENT '文件上传ID，关联saas_file_upload表',
    `interpret_key` varchar(128) DEFAULT NULL COMMENT '解释标识',
    
    -- 业务信息
    `business_unique_code` varchar(128) DEFAULT NULL COMMENT '业务申请唯一码，用于数据新增或修改',
    `business_type` varchar(64) DEFAULT NULL COMMENT '业务类型',
    `business_description` varchar(512) DEFAULT NULL COMMENT '业务描述',
    
    -- 解释信息
    `interpret_info` longtext COMMENT '解释信息JSON，存储字段与值的映射关系',
    `interpret_status` varchar(32) DEFAULT 'PENDING' COMMENT '解释状态: PENDING-待解释, PROCESSING-解释中, COMPLETED-已解释, FAILED-解释失败',
    `interpret_progress` int DEFAULT 0 COMMENT '解释进度 0-100',
    `interpret_fail_count` int DEFAULT 0 COMMENT '解释失败次数',
    `last_interpret_time` datetime DEFAULT NULL COMMENT '最后解释时间',
    `error_message` varchar(2048) DEFAULT NULL COMMENT '错误信息',
    
    -- 统计信息
    `total_count` int DEFAULT 0 COMMENT '数据总条数',
    `success_count` int DEFAULT 0 COMMENT '成功解释条数',
    `fail_count` int DEFAULT 0 COMMENT '失败解释条数',
    `skip_count` int DEFAULT 0 COMMENT '跳过解释条数',
    
    -- 回调信息
    `need_callback` tinyint DEFAULT 0 COMMENT '是否需要回调: 0-否, 1-是',
    `callback_url` varchar(512) DEFAULT NULL COMMENT '回调URL',
    `callback_method` varchar(16) DEFAULT 'POST' COMMENT '回调HTTP方法: GET, POST',
    `callback_status` varchar(32) DEFAULT 'PENDING' COMMENT '回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败',
    `callback_fail_count` int DEFAULT 0 COMMENT '回调失败次数',
    `last_callback_time` datetime DEFAULT NULL COMMENT '最后回调时间',
    `callback_request` text COMMENT '回调请求内容',
    `callback_response` text COMMENT '回调响应内容',
    
    -- 业务处理结果
    `business_process_status` varchar(32) DEFAULT NULL COMMENT '业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败',
    `business_process_result` varchar(2048) DEFAULT NULL COMMENT '业务处理结果描述',
    `business_exception_info` varchar(2048) DEFAULT NULL COMMENT '业务处理异常信息',
    `business_process_time` datetime DEFAULT NULL COMMENT '业务处理时间',
    
    -- 扩展信息
    `ext_field1` varchar(512) DEFAULT NULL COMMENT '扩展字段1',
    `ext_field2` varchar(512) DEFAULT NULL COMMENT '扩展字段2',
    `ext_field3` varchar(512) DEFAULT NULL COMMENT '扩展字段3',
    `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
    
    -- 通用字段
    `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
    `create_name` varchar(64) DEFAULT NULL COMMENT '创建人名称',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `modify_id` bigint DEFAULT NULL COMMENT '修改人ID',
    `modify_name` varchar(64) DEFAULT NULL COMMENT '修改人名称',
    `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
    `deleted` int DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    `version` int DEFAULT 0 COMMENT '版本号',
    
    PRIMARY KEY (`id`),
    KEY `idx_file_upload_id` (`file_upload_id`),
    KEY `idx_interpret_key` (`interpret_key`),
    KEY `idx_business_unique_code` (`business_unique_code`),
    KEY `idx_interpret_status` (`interpret_status`),
    KEY `idx_business_process_status` (`business_process_status`),
    KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件解释记录明细表';

SET FOREIGN_KEY_CHECKS = 1;

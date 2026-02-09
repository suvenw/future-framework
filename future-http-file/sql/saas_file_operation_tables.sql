-- =============================================================================
-- SaaS文件业务操作相关表结构
-- 创建时间: 2026-02-09
-- 说明: 包含文件操作记录表、字段映射表、解释记录表
-- =============================================================================

-- =============================================================================
-- SaaS文件操作记录表 (saas_file_operation_record)
-- 功能: 记录每一次文件上传操作，包含公司、平台、业务、功能类型等信息
-- =============================================================================
DROP TABLE IF EXISTS saas_file_operation_record;
CREATE TABLE saas_file_operation_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    company_id VARCHAR(64) COMMENT '业务公司id',
    company_name VARCHAR(128) COMMENT '业务公司名称',
    dept_id BIGINT DEFAULT 0 COMMENT '业务公司人员的部门id',
    dept_name VARCHAR(128) COMMENT '业务公司人员的部门名称',
    upload_user_id BIGINT DEFAULT 0 COMMENT '上传人员的id',
    upload_user_name VARCHAR(64) COMMENT '上传人员的名称',
    app_id VARCHAR(64) NOT NULL COMMENT '注册应用id',
    client_id BIGINT NOT NULL COMMENT '注册应用授权Id',
    use_business_id BIGINT NOT NULL DEFAULT 0 COMMENT '使用业务Id',
    file_product_name VARCHAR(128) NOT NULL COMMENT '上传业务产品名称',
    file_business_name VARCHAR(128) NOT NULL COMMENT '上传业务名称',
    function_type VARCHAR(32) NOT NULL COMMENT '功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载',
    platform_type VARCHAR(32) COMMENT '平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口',
    file_upload_storage_id BIGINT NOT NULL COMMENT '文件存储信息id',
    file_source_name VARCHAR(256) NOT NULL COMMENT '文件名称,原来文件上传的名称',
    file_type VARCHAR(32) COMMENT '文件类型',
    file_size BIGINT DEFAULT 0 COMMENT '文件大小',
    file_md5 VARCHAR(64) COMMENT '文件MD5',
    status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '操作状态: PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, FAILED-失败',
    progress_percent INT DEFAULT 0 COMMENT '处理进度百分比 0-100',
    total_count INT DEFAULT 0 COMMENT '总记录数',
    success_count INT DEFAULT 0 COMMENT '成功处理数',
    fail_count INT DEFAULT 0 COMMENT '失败处理数',
    message VARCHAR(512) COMMENT '处理说明',
    error_message TEXT COMMENT '错误信息',
    need_callback INT DEFAULT 0 COMMENT '是否需要回调: 0-否, 1-是',
    callback_url VARCHAR(512) COMMENT '回调URL',
    callback_status VARCHAR(32) COMMENT '回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败',
    callback_fail_count INT DEFAULT 0 COMMENT '回调失败次数',
    last_callback_time DATETIME COMMENT '最后回调时间',
    callback_response TEXT COMMENT '回调响应结果',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    modify_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_user_id BIGINT DEFAULT 0 COMMENT '创建人ID',
    create_user_name VARCHAR(64) COMMENT '创建人名称',
    modify_user_id BIGINT DEFAULT 0 COMMENT '修改人ID',
    modify_user_name VARCHAR(64) COMMENT '修改人名称',
    PRIMARY KEY (id),
    KEY idx_app_id (app_id),
    KEY idx_client_id (client_id),
    KEY idx_file_upload_storage_id (file_upload_storage_id),
    KEY idx_status (status),
    KEY idx_create_date (create_date),
    KEY idx_deleted (deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SaaS文件操作记录表';

-- =============================================================================
-- SaaS文件字段映射表 (saas_file_field_mapping)
-- 功能: 记录文件解释的字段英文名、中文名、排编号，关联解释记录和操作记录
-- =============================================================================
DROP TABLE IF EXISTS saas_file_field_mapping;
CREATE TABLE saas_file_field_mapping (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    operation_record_id BIGINT NOT NULL COMMENT '操作记录ID，关联saas_file_operation_record表',
    interpret_record_id BIGINT DEFAULT 0 COMMENT '解释记录ID，关联saas_file_interpret_record表',
    field_english_name VARCHAR(64) NOT NULL COMMENT '字段英文名称',
    field_chinese_name VARCHAR(128) NOT NULL COMMENT '字段中文名称',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排编号，用于排序和定位',
    field_type VARCHAR(32) COMMENT '字段数据类型: STRING-字符串, NUMBER-数字, DATE-日期, BOOLEAN-布尔值',
    field_length INT DEFAULT 0 COMMENT '字段长度或精度',
    is_primary_key TINYINT DEFAULT 0 COMMENT '是否为主键: 0-否, 1-是',
    is_required TINYINT DEFAULT 0 COMMENT '是否必填: 0-否, 1-是',
    default_value VARCHAR(256) COMMENT '默认值',
    field_description VARCHAR(512) COMMENT '字段描述',
    total_count INT DEFAULT 0 COMMENT '字段对应的数据总条数',
    not_null_count INT DEFAULT 0 COMMENT '字段非空数据条数',
    null_count INT DEFAULT 0 COMMENT '字段空值数据条数',
    duplicate_count INT DEFAULT 0 COMMENT '字段重复值数量',
    validate_rule VARCHAR(512) COMMENT '字段格式验证规则',
    transform_rule TEXT COMMENT '字段转换规则(正则表达式或转换代码)',
    sample_value VARCHAR(512) COMMENT '字段示例值',
    remark VARCHAR(512) COMMENT '备注',
    status VARCHAR(32) DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE-激活, INACTIVE-禁用, DELETED-已删除',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    modify_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_user_id BIGINT DEFAULT 0 COMMENT '创建人ID',
    create_user_name VARCHAR(64) COMMENT '创建人名称',
    modify_user_id BIGINT DEFAULT 0 COMMENT '修改人ID',
    modify_user_name VARCHAR(64) COMMENT '修改人名称',
    PRIMARY KEY (id),
    KEY idx_operation_record_id (operation_record_id),
    KEY idx_interpret_record_id (interpret_record_id),
    KEY idx_deleted (deleted),
    KEY idx_sort_order (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SaaS文件字段映射表';

-- =============================================================================
-- SaaS文件解释记录表 (saas_file_interpret_record)
-- 功能: 记录文件解释的详细信息，包含解释信息JSON、业务申请唯一码、回调状态等
-- =============================================================================
DROP TABLE IF EXISTS saas_file_interpret_record;
CREATE TABLE saas_file_interpret_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    operation_record_id BIGINT NOT NULL COMMENT '操作记录ID，关联saas_file_operation_record表',
    interpret_key VARCHAR(128) NOT NULL COMMENT '解释标识，用于关联字段映射',
    business_unique_code VARCHAR(128) NOT NULL COMMENT '业务申请唯一码，用于数据新增或修改',
    business_type VARCHAR(64) COMMENT '业务类型',
    business_description VARCHAR(512) COMMENT '业务描述',
    interpret_info LONGTEXT COMMENT '解释信息JSON，存储字段与值的映射关系',
    interpret_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' COMMENT '解释状态: PENDING-待解释, PROCESSING-解释中, COMPLETED-已解释, FAILED-解释失败',
    interpret_progress INT DEFAULT 0 COMMENT '解释进度 0-100',
    interpret_fail_count INT DEFAULT 0 COMMENT '解释失败次数',
    last_interpret_time DATETIME COMMENT '最后解释时间',
    error_message TEXT COMMENT '错误信息',
    total_count INT DEFAULT 0 COMMENT '数据总条数',
    success_count INT DEFAULT 0 COMMENT '成功解释条数',
    fail_count INT DEFAULT 0 COMMENT '失败解释条数',
    skip_count INT DEFAULT 0 COMMENT '跳过解释条数',
    need_callback INT DEFAULT 0 COMMENT '是否需要回调: 0-否, 1-是',
    callback_url VARCHAR(512) COMMENT '回调URL',
    callback_method VARCHAR(16) COMMENT '回调HTTP方法: GET, POST',
    callback_status VARCHAR(32) COMMENT '回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败',
    callback_fail_count INT DEFAULT 0 COMMENT '回调失败次数',
    last_callback_time DATETIME COMMENT '最后回调时间',
    callback_request TEXT COMMENT '回调请求内容',
    callback_response TEXT COMMENT '回调响应内容',
    business_process_status VARCHAR(32) COMMENT '业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败',
    business_process_result VARCHAR(512) COMMENT '业务处理结果描述',
    business_exception_info TEXT COMMENT '业务处理异常信息',
    business_process_time DATETIME COMMENT '业务处理时间',
    ext_field1 VARCHAR(256) COMMENT '扩展字段1',
    ext_field2 VARCHAR(256) COMMENT '扩展字段2',
    ext_field3 VARCHAR(256) COMMENT '扩展字段3',
    remark VARCHAR(512) COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
    create_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    modify_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    create_user_id BIGINT DEFAULT 0 COMMENT '创建人ID',
    create_user_name VARCHAR(64) COMMENT '创建人名称',
    modify_user_id BIGINT DEFAULT 0 COMMENT '修改人ID',
    modify_user_name VARCHAR(64) COMMENT '修改人名称',
    PRIMARY KEY (id),
    KEY idx_operation_record_id (operation_record_id),
    KEY idx_interpret_key (interpret_key),
    KEY idx_business_unique_code (business_unique_code),
    KEY idx_interpret_status (interpret_status),
    KEY idx_business_process_status (business_process_status),
    KEY idx_callback_status (callback_status),
    KEY idx_deleted (deleted),
    KEY idx_create_date (create_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SaaS文件解释记录表';

-- =============================================================================
-- 示例数据插入
-- =============================================================================

-- 插入示例操作记录
INSERT INTO saas_file_operation_record (
    id, tenant_id, company_id, company_name, app_id, client_id, 
    file_product_name, file_business_name, function_type, platform_type,
    file_upload_storage_id, file_source_name, file_type, file_size, status
) VALUES (
    1, 0, 'C001', '测试公司', 'APP001', 10001, 
    '产品导入', '用户批量导入', 'IMPORT', 'WEB',
    1, '用户数据导入模板.xlsx', 'xlsx', 102400, 'PENDING'
);

-- 插入示例字段映射
INSERT INTO saas_file_field_mapping (
    id, tenant_id, operation_record_id, field_english_name, field_chinese_name, 
    sort_order, field_type, is_primary_key, is_required, field_description
) VALUES 
(1, 0, 1, 'user_name', '用户姓名', 1, 'STRING', 1, 1, '必填，用户姓名'),
(2, 0, 1, 'user_email', '用户邮箱', 2, 'STRING', 0, 1, '必填，邮箱格式'),
(3, 0, 1, 'user_phone', '联系电话', 3, 'STRING', 0, 0, '选填，手机号格式'),
(4, 0, 1, 'user_age', '年龄', 4, 'NUMBER', 0, 0, '选填，0-150之间'),
(5, 0, 1, 'join_date', '入职日期', 5, 'DATE', 0, 1, '必填，日期格式yyyy-MM-dd');

-- 插入示例解释记录
INSERT INTO saas_file_interpret_record (
    id, tenant_id, operation_record_id, interpret_key, business_unique_code,
    business_type, interpret_status, total_count, success_count, fail_count,
    need_callback, callback_url, callback_method, business_process_status
) VALUES (
    1, 0, 1, 'USER_IMPORT_001', 'BIZ00120260209001',
    'USER_IMPORT', 'COMPLETED', 100, 95, 5,
    1, 'http://business.example.com/callback', 'POST', 'PENDING'
);

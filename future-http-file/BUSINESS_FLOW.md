# 文件上传下载业务流程说明文档

## 概述

本文档详细说明了公司企业申请业务功能、配置字段映射、执行文件上传、数据解析、校验及回调的完整业务流程。

## 业务流程图

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        文件上传下载业务流程                                   │
└─────────────────────────────────────────────────────────────────────────────┘

第一阶段：业务功能申请与配置
─────────────────────────────────────────────────────────────────────────────

1. 公司企业申请业务功能
   ├── 填写公司信息（companyId, companyName）
   ├── 填写业务配置（businessCode, businessName, functionType, platformType）
   ├── 填写回调信息（callbackUrl, queryUrl, accessMethod）
   └── 系统生成业务唯一码（businessUniqueCode）

2. 配置字段映射
   ├── 定义英文字段名（fieldEnglishName）
   ├── 定义中文字段名（fieldChineseName）
   ├── 设置排序号（sortOrder）
   ├── 配置数据类型（fieldType: STRING/NUMBER/DATE/BOOLEAN）
   ├── 设置验证规则（validateRule: 正则表达式）
   ├── 设置是否必填（isRequired: 0/1）
   └── 保存字段映射列表

3. 激活业务功能
   └── 状态变更为 ACTIVE，可供使用

第二阶段：文件上传与处理
─────────────────────────────────────────────────────────────────────────────

4. 上传文件
   ├── 选择业务功能（businessUniqueCode）
   ├── 上传文件（支持 XLS, XLSX, CSV 格式）
   └── 选择是否需要回调（needCallback: 0/1）

5. 系统处理流程（自动执行）
   ├── 步骤1: 校验业务功能配置
   │   └── 检查业务功能是否存在且已激活
   ├── 步骤2: 创建操作记录
   │   └── 记录文件上传信息和处理状态
   ├── 步骤3: 解析文件
   │   ├── 根据文件类型选择解析器
   │   ├── 读取文件内容
   │   └── 提取数据行
   ├── 步骤4: 数据映射转换
   │   ├── 根据字段映射转换数据
   │   └── 英文字段名 -> 中文字段名
   ├── 步骤5: 数据校验
   │   ├── 必填项检查
   │   ├── 数据类型检查
   │   └── 正则规则验证
   ├── 步骤6: 保存解释记录
   │   ├── 每行数据保存为一条解释记录
   │   └── 记录原始数据和校验结果
   └── 步骤7: 回调业务服务
       ├── 构建回调数据
       ├── 发送HTTP请求到业务回调地址
       └── 处理回调结果

6. 处理结果返回
   ├── 成功：返回操作记录ID和数据统计
   └── 失败：返回错误信息和校验错误列表

第三阶段：数据查询与处理
─────────────────────────────────────────────────────────────────────────────

7. 业务方查询数据
   ├── 通过业务唯一码查询解释记录
   ├── 分批获取处理后的数据
   └── 处理业务逻辑

8. 业务方回写结果
   ├── 标记记录处理成功
   ├── 标记记录处理失败（含错误信息）
   └── 系统更新统计信息

9. 异常处理与重试
   ├── 查询处理进度
   ├── 重试失败的记录
   └── 批量重试（可选）
```

## API 接口列表

### 一、公司业务功能申请管理接口

#### 1. 申请业务功能
```
POST /saas/business/apply
```

**请求参数：**
```json
{
  "companyId": "C001",
  "companyName": "测试公司",
  "platformType": "WEB",
  "businessCode": "ORDER_IMPORT",
  "businessName": "订单导入",
  "functionType": "IMPORT",
  "callbackUrl": "https://example.com/api/callback",
  "queryUrl": "https://example.com/api/query",
  "accessMethod": "POST",
  "remark": "订单数据导入功能"
}
```

**响应结果：**
```json
{
  "id": 1,
  "companyId": "C001",
  "businessCode": "ORDER_IMPORT",
  "businessName": "订单导入",
  "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_1707187200000_abc12345",
  "status": "PENDING",
  "createDate": "2026-02-11T10:00:00"
}
```

#### 2. 更新业务功能
```
POST /saas/business/update
```

#### 3. 获取业务功能详情
```
GET /saas/business/info?id=1
```

#### 4. 分页查询业务功能列表
```
GET /saas/business/pageList?companyId=C001&pageNo=1&pageSize=20
```

#### 5. 根据业务唯一码查询
```
GET /saas/business/getByCode?businessUniqueCode=BIZ_C001_ORDER_IMPORT_xxx
```

#### 6. 激活业务功能
```
POST /saas/business/activate
```

**请求参数：**
```json
{
  "id": 1
}
```

#### 7. 停用业务功能
```
POST /saas/business/deactivate
```

#### 8. 删除业务功能
```
POST /saas/business/delete
```

#### 9. 批量删除业务功能
```
POST /saas/business/batchDelete
```

### 二、字段映射管理接口

#### 10. 保存字段映射
```
POST /saas/business/fieldMappings/save
```

**请求参数：**
```json
{
  "businessFunctionId": 1,
  "fieldEnglishName": "orderNo",
  "fieldChineseName": "订单编号",
  "sortOrder": 1,
  "fieldType": "STRING",
  "fieldLength": 50,
  "isRequired": 1,
  "validateRule": "^[A-Z0-9]{10,20}$",
  "fieldDescription": "订单唯一编号"
}
```

#### 11. 更新字段映射
```
POST /saas/business/fieldMapping/update
```

#### 12. 删除字段映射
```
POST /saas/business/fieldMapping/delete
```

#### 13. 获取字段映射列表
```
GET /saas/business/fieldMappings/list?businessFunctionId=1
```

#### 14. 批量保存字段映射
```
POST /saas/business/fieldMappings/batchSave?businessFunctionId=1
```

**请求参数：**
```json
[
  {
    "fieldEnglishName": "orderNo",
    "fieldChineseName": "订单编号",
    "sortOrder": 1,
    "fieldType": "STRING",
    "isRequired": 1
  },
  {
    "fieldEnglishName": "amount",
    "fieldChineseName": "金额",
    "sortOrder": 2,
    "fieldType": "NUMBER",
    "isRequired": 1
  }
]
```

### 三、文件上传业务流程接口

#### 15. 上传文件（执行完整业务流程）
```
POST /saas/upload/process
```

**请求参数（multipart/form-data）：**
```
file: [文件内容]
businessUniqueCode: BIZ_C001_ORDER_IMPORT_xxx
appId: APP001
clientId: CLIENT001
uploadUserId: 10001
uploadUserName: 张三
needCallback: 1
```

**响应结果：**
```json
{
  "success": true,
  "message": "处理完成",
  "operationRecordId": 100,
  "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_xxx",
  "fileName": "orders.xlsx",
  "totalRows": 100,
  "successRows": 98,
  "failRows": 2,
  "interpretRecordCount": 100,
  "validationErrors": [
    "第5行: 订单编号(orderNo)为必填项",
    "第10行: 金额(amount)数据类型错误，期望NUMBER"
  ],
  "callbackSuccess": true,
  "startTime": "2026-02-11T10:00:00",
  "endTime": "2026-02-11T10:00:05"
}
```

#### 16. 获取处理进度
```
GET /saas/upload/progress?operationRecordId=100
```

**响应结果：**
```json
{
  "operationRecordId": 100,
  "status": "COMPLETED",
  "progressPercent": 100,
  "message": "处理完成",
  "callbackStatus": "SUCCESS",
  "totalCount": 100,
  "successCount": 98,
  "failCount": 2
}
```

#### 17. 重试失败记录
```
POST /saas/upload/retry
```

**请求参数：**
```json
{
  "id": 100
}
```

### 四、SaaS文件业务操作接口

#### 18. 分页获取操作记录列表
```
GET /saas/operation/pageList
```

#### 19. 获取操作记录详情
```
GET /saas/operation/info?id=100
```

#### 20. 分页获取解释记录列表
```
GET /saas/interpret/pageList?id=100&pageNo=1&pageSize=20
```

#### 21. 按业务唯一码分页查询解释记录
```
POST /saas/interpret/biz/pageList
```

**请求参数：**
```json
{
  "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_xxx",
  "pageNo": 1,
  "pageSize": 20
}
```

#### 22. 分批查询待处理数据
```
GET /saas/interpret/pending?id=100&status=PENDING&pageNo=1&pageSize=50
```

#### 23. 业务回调接口（业务方调用）
```
POST /saas/callback
```

**请求参数：**
```json
{
  "interpretRecordId": 1000,
  "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_xxx",
  "businessProcessStatus": "SUCCESS",
  "businessProcessResult": "订单创建成功",
  "businessExceptionInfo": null
}
```

#### 24. 回写处理结果（业务方调用）
```
POST /saas/callback/result
```

### 五、SaaS文件生成与下载接口

#### 25. 申请生成文件（异步）
```
POST /saas/file/generate/apply
```

**请求参数：**
```json
{
  "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_xxx",
  "dataQueryUrl": "https://example.com/api/queryData",
  "queryParams": {
    "startDate": "2026-01-01",
    "endDate": "2026-01-31"
  },
  "fileNamePrefix": "订单数据",
  "fileType": "XLSX",
  "downloadUserId": 10001,
  "downloadUserName": "张三"
}
```

#### 26. 同步生成文件
```
POST /saas/file/generate/sync
```

#### 27. 异步生成文件
```
POST /saas/file/generate/async
```

#### 28. 获取生成状态
```
GET /saas/file/generate/status?taskId=100
```

#### 29. 获取下载URL
```
GET /saas/file/generate/downloadUrl?taskId=100
```

#### 30. 获取生成进度
```
GET /saas/file/generate/progress?taskId=100
```

## 数据表结构

### 1. 公司业务功能表（saas_company_business_function）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键ID |
| company_id | varchar | 公司ID |
| company_name | varchar | 公司名称 |
| platform_type | varchar | 平台类型 |
| business_code | varchar | 业务编码 |
| business_name | varchar | 业务名称 |
| function_type | varchar | 功能类型 |
| business_unique_code | varchar | 业务唯一码 |
| callback_url | varchar | 回调地址 |
| query_url | varchar | 查询地址 |
| access_method | varchar | 访问方式 |
| status | varchar | 状态 |
| remark | varchar | 备注 |
| create_date | datetime | 创建时间 |
| modify_date | datetime | 修改时间 |

### 2. 字段映射表（saas_file_field_mapping）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键ID |
| business_function_id | bigint | 业务功能ID |
| field_english_name | varchar | 英文字段名 |
| field_chinese_name | varchar | 中文字段名 |
| sort_order | int | 排序号 |
| field_type | varchar | 字段类型 |
| field_length | int | 字段长度 |
| is_primary_key | tinyint | 是否主键 |
| is_required | tinyint | 是否必填 |
| default_value | varchar | 默认值 |
| validate_rule | varchar | 验证规则 |
| transform_rule | varchar | 转换规则 |
| sample_value | varchar | 示例值 |
| status | varchar | 状态 |
| remark | varchar | 备注 |

### 3. 文件操作记录表（saas_file_operation_record）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键ID |
| app_id | varchar | 应用ID |
| client_id | varchar | 客户端ID |
| use_business_id | bigint | 业务功能ID |
| company_id | varchar | 公司ID |
| file_product_name | varchar | 业务产品名称 |
| file_business_name | varchar | 业务名称 |
| file_source_name | varchar | 源文件名 |
| file_type | varchar | 文件类型 |
| status | varchar | 状态 |
| progress_percent | int | 进度百分比 |
| total_count | int | 总记录数 |
| success_count | int | 成功数 |
| fail_count | int | 失败数 |
| callback_url | varchar | 回调地址 |
| callback_status | varchar | 回调状态 |
| message | varchar | 消息 |

### 4. 文件解释记录表（saas_file_interpret_record）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | bigint | 主键ID |
| file_upload_id | bigint | 文件上传ID |
| business_unique_code | varchar | 业务唯一码 |
| row_index | int | 行序号 |
| interpret_info | text | 解释信息（JSON） |
| business_status | varchar | 业务处理状态 |
| business_result | text | 业务处理结果 |
| business_error_message | text | 业务错误信息 |
| business_process_time | datetime | 业务处理时间 |

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| SYS_PARAM_ERROR | 参数错误 |
| SYS_RESPONSE_RESULT_IS_NULL | 查询结果为空 |
| SYS_ERROR | 系统错误 |
| FILE_PARSE_ERROR | 文件解析错误 |
| FILE_TYPE_NOT_SUPPORTED | 不支持的文件类型 |
| BUSINESS_FUNCTION_NOT_FOUND | 业务功能不存在 |
| BUSINESS_FUNCTION_NOT_ACTIVE | 业务功能未激活 |
| CALLBACK_FAILED | 回调失败 |

## 使用示例

### 完整业务流程示例

```java
// 步骤1: 申请业务功能
CompanyBusinessFunctionRequestVo applyRequest = new CompanyBusinessFunctionRequestVo();
applyRequest.setCompanyId("C001");
applyRequest.setCompanyName("测试公司");
applyRequest.setBusinessCode("ORDER_IMPORT");
applyRequest.setBusinessName("订单导入");
applyRequest.setFunctionType("IMPORT");
applyRequest.setPlatformType("WEB");
applyRequest.setCallbackUrl("https://example.com/api/callback");

// 调用接口申请
// POST /saas/business/apply

// 步骤2: 配置字段映射
List<FileFieldRequestDto> fieldMappings = new ArrayList<>();

FileFieldRequestDto field1 = new FileFieldRequestDto();
field1.setFieldEnglishName("orderNo");
field1.setFieldChineseName("订单编号");
field1.setSortOrder(1);
field1.setFieldType("STRING");
field1.setIsRequired(1);
field1.setValidateRule("^[A-Z0-9]{10,20}$");
fieldMappings.add(field1);

FileFieldRequestDto field2 = new FileFieldRequestDto();
field2.setFieldEnglishName("amount");
field2.setFieldChineseName("金额");
field2.setSortOrder(2);
field2.setFieldType("NUMBER");
field2.setIsRequired(1);
fieldMappings.add(field2);

// 调用接口批量保存字段映射
// POST /saas/business/fieldMappings/batchSave?businessFunctionId=1

// 步骤3: 激活业务功能
// POST /saas/business/activate
// {"id": 1}

// 步骤4: 上传文件（获取businessUniqueCode后）
// POST /saas/upload/process
// 参数:
//   - file: [Excel文件]
//   - businessUniqueCode: BIZ_C001_ORDER_IMPORT_xxx
//   - needCallback: 1

// 步骤5: 查询处理进度
// GET /saas/upload/progress?operationRecordId=100

// 步骤6: 业务方处理数据后回写结果
// POST /saas/callback
// {
//   "interpretRecordId": 1000,
//   "businessUniqueCode": "BIZ_C001_ORDER_IMPORT_xxx",
//   "businessProcessStatus": "SUCCESS",
//   "businessProcessResult": "订单创建成功"
// }
```

## 注意事项

1. **业务唯一码生成规则**：`BIZ_{companyId}_{businessCode}_{timestamp}_{random}`
2. **文件格式支持**：XLS, XLSX, CSV
3. **数据类型支持**：STRING, NUMBER, INTEGER, LONG, DATE, DATETIME, BOOLEAN
4. **回调重试机制**：失败后自动重试3次，间隔5秒、10秒、15秒
5. **字段映射批量保存**：会先删除原有映射，再保存新的映射
6. **状态流转**：
   - 业务功能：PENDING -> ACTIVE/INACTIVE
   - 文件处理：PENDING -> PARSING -> VALIDATING -> SAVING -> CALLBACK -> COMPLETED
   - 解释记录：PENDING -> SUCCESS/FAILED

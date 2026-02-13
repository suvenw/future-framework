package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 平台业务文件上传表
 * 
 * 功能：实现具体业务上传文件管理，关联业务功能配置和字段映射
 *       包含文件信息、解释标识、处理状态、回调配置等
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "saas_file_upload")
public class FileUpload extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 关联信息 ====================
    
    /** 业务功能配置ID，关联saas_company_business_function表 */
    @ApiDesc(value = "业务功能配置ID", required = 0)
    private long businessFunctionId;
    
    /** 字段映射ID，关联saas_file_field_mapping表 */
    @ApiDesc(value = "字段映射ID", required = 0)
    private long fieldMappingId;

    // ==================== 业务信息 ====================
    
    /** 注册应用ID */
    @ApiDesc(value = "注册应用ID", required = 0)
    private String appId;
    
    /** 业务唯一码，关联业务功能配置 */
    @ApiDesc(value = "业务唯一码", required = 0)
    private String businessUniqueCode;
    
    /** 上传批次号 */
    @ApiDesc(value = "上传批次号", required = 0)
    private String uploadBatchNo;

    // ==================== 上传用户信息 ====================
    
    /** 上传人员ID */
    @ApiDesc(value = "上传人员ID", required = 0)
    private long uploadUserId;
    
    /** 上传人员名称 */
    @ApiDesc(value = "上传人员名称", required = 0)
    private String uploadUserName;
    
    /** 上传人员部门ID */
    @ApiDesc(value = "上传人员部门ID", required = 0)
    private long uploadDeptId;
    
    /** 上传人员部门名称 */
    @ApiDesc(value = "上传人员部门名称", required = 0)
    private String uploadDeptName;

    // ==================== 文件信息 ====================
    
    /** 原始文件名称 */
    @ApiDesc(value = "原始文件名称", required = 1)
    private String fileSourceName;
    
    /** 存储文件名称 */
    @ApiDesc(value = "存储文件名称", required = 0)
    private String fileStorageName;
    
    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 0)
    private String fileType;
    
    /** 文件大小(字节) */
    @ApiDesc(value = "文件大小", required = 0)
    private long fileSize;
    
    /** 文件MD5值 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;
    
    /** 文件存储路径 */
    @ApiDesc(value = "文件存储路径", required = 0)
    private String filePath;
    
    /** 文件存储记录ID */
    @ApiDesc(value = "文件存储记录ID", required = 0)
    private long fileStorageId;

    // ==================== 解释标识 ====================
    
    /** 是否需要解释: 0-否, 1-是 */
    @ApiDesc(value = "是否需要解释", required = 0)
    private int interpretFlag;
    
    /** 解释状态: PENDING-待解释, PROCESSING-解释中, COMPLETED-已解释, FAILED-解释失败 */
    @ApiDesc(value = "解释状态", required = 0)
    private String interpretStatus;
    
    /** 解释标识，用于关联解释记录 */
    @ApiDesc(value = "解释标识", required = 0)
    private String interpretKey;

    // ==================== 处理状态 ====================
    
    /** 处理状态: PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, FAILED-失败 */
    @ApiDesc(value = "处理状态", required = 0)
    private String status;
    
    /** 处理进度百分比 0-100 */
    @ApiDesc(value = "处理进度", required = 0)
    private int progressPercent;

    // ==================== 统计信息 ====================
    
    /** 文件总记录数 */
    @ApiDesc(value = "文件总记录数", required = 0)
    private int totalCount;
    
    /** 成功处理数 */
    @ApiDesc(value = "成功处理数", required = 0)
    private int successCount;
    
    /** 失败处理数 */
    @ApiDesc(value = "失败处理数", required = 0)
    private int failCount;
    
    /** 跳过处理数 */
    @ApiDesc(value = "跳过处理数", required = 0)
    private int skipCount;

    // ==================== 消息信息 ====================
    
    /** 处理说明 */
    @ApiDesc(value = "处理说明", required = 0)
    private String message;
    
    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    // ==================== 回调配置 ====================
    
    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调", required = 0)
    private int needCallback;
    
    /** 回调URL */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;
    
    /** 文件业务回调关系唯一标识 */
    @ApiDesc(value = "回调关系唯一标识", required = 0)
    private String callbackRelationKey;
    
    /** 回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;
    
    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;
    
    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private LocalDateTime lastCallbackTime;
    
    /** 回调响应结果 */
    @ApiDesc(value = "回调响应结果", required = 0)
    private String callbackResponse;

    // ==================== 业务处理结果 ====================
    
    /** 业务处理状态: PENDING-待处理, PROCESSING-处理中, SUCCESS-处理成功, FAILED-处理失败 */
    @ApiDesc(value = "业务处理状态", required = 0)
    private String businessProcessStatus;
    
    /** 业务处理结果描述 */
    @ApiDesc(value = "业务处理结果描述", required = 0)
    private String businessProcessResult;
    
    /** 业务处理异常信息 */
    @ApiDesc(value = "业务处理异常信息", required = 0)
    private String businessExceptionInfo;
    
    /** 业务处理时间 */
    @ApiDesc(value = "业务处理时间", required = 0)
    private LocalDateTime businessProcessTime;

    // ==================== 构建辅助方法 ====================

    public static FileUpload build() {
        return new FileUpload();
    }

    public FileUpload toBusinessFunctionId(long businessFunctionId) {
        this.businessFunctionId = businessFunctionId;
        return this;
    }

    public FileUpload toFieldMappingId(long fieldMappingId) {
        this.fieldMappingId = fieldMappingId;
        return this;
    }

    public FileUpload toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileUpload toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public FileUpload toUploadBatchNo(String uploadBatchNo) {
        this.uploadBatchNo = uploadBatchNo;
        return this;
    }

    public FileUpload toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public FileUpload toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public FileUpload toUploadDeptId(long uploadDeptId) {
        this.uploadDeptId = uploadDeptId;
        return this;
    }

    public FileUpload toUploadDeptName(String uploadDeptName) {
        this.uploadDeptName = uploadDeptName;
        return this;
    }

    public FileUpload toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileUpload toFileStorageName(String fileStorageName) {
        this.fileStorageName = fileStorageName;
        return this;
    }

    public FileUpload toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileUpload toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public FileUpload toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public FileUpload toFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public FileUpload toFileStorageId(long fileStorageId) {
        this.fileStorageId = fileStorageId;
        return this;
    }

    public FileUpload toInterpretFlag(int interpretFlag) {
        this.interpretFlag = interpretFlag;
        return this;
    }

    public FileUpload toInterpretStatus(String interpretStatus) {
        this.interpretStatus = interpretStatus;
        return this;
    }

    public FileUpload toInterpretKey(String interpretKey) {
        this.interpretKey = interpretKey;
        return this;
    }

    public FileUpload toStatus(String status) {
        this.status = status;
        return this;
    }

    public FileUpload toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public FileUpload toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public FileUpload toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public FileUpload toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public FileUpload toSkipCount(int skipCount) {
        this.skipCount = skipCount;
        return this;
    }

    public FileUpload toMessage(String message) {
        this.message = message;
        return this;
    }

    public FileUpload toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public FileUpload toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public FileUpload toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public FileUpload toCallbackRelationKey(String callbackRelationKey) {
        this.callbackRelationKey = callbackRelationKey;
        return this;
    }

    public FileUpload toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public FileUpload toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public FileUpload toLastCallbackTime(LocalDateTime lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public FileUpload toCallbackResponse(String callbackResponse) {
        this.callbackResponse = callbackResponse;
        return this;
    }

    public FileUpload toBusinessProcessStatus(String businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
        return this;
    }

    public FileUpload toBusinessProcessResult(String businessProcessResult) {
        this.businessProcessResult = businessProcessResult;
        return this;
    }

    public FileUpload toBusinessExceptionInfo(String businessExceptionInfo) {
        this.businessExceptionInfo = businessExceptionInfo;
        return this;
    }

    public FileUpload toBusinessProcessTime(LocalDateTime businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
        return this;
    }
}

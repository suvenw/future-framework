package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * SaaS平台业务上传操作记录表
 * 
 * 功能：记录每一次文件上传操作，包含公司、平台、业务、功能类型等信息
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "saas_file_operation_record")
public class SaaSFileOperationRecord extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 基础信息 ====================
    
    /** 业务公司id */
    @ApiDesc(value = "业务公司id", required = 0)
    private String companyId;
    
    /** 业务公司名称 */
    @ApiDesc(value = "业务公司名称", required = 0)
    private String companyName;
    
    /** 业务公司人员的部门id */
    @ApiDesc(value = "业务公司人员的部门id", required = 0)
    private long deptId;
    
    /** 业务公司人员的部门名称 */
    @ApiDesc(value = "业务公司人员的部门名称", required = 0)
    private String deptName;
    
    /** 上传人员的id */
    @ApiDesc(value = "上传人员的id", required = 0)
    private long uploadUserId;
    
    /** 上传人员的名称 */
    @ApiDesc(value = "上传人员的名称", required = 0)
    private String uploadUserName;

    // ==================== 业务信息 ====================
    
    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;
    
    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;
    
    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 1)
    private long useBusinessId;
    
    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 1)
    private String fileProductName;
    
    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 1)
    private String fileBusinessName;
    
    /** 功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载 */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;
    
    /** 平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口 */
    @ApiDesc(value = "平台类型", required = 0)
    private String platformType;

    // ==================== 文件信息 ====================
    
    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;
    
    /** 文件名称,原来文件上传的名称 */
    @ApiDesc(value = "文件名称", required = 1)
    private String fileSourceName;
    
    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 1)
    private String fileType;
    
    /** 文件大小 */
    @ApiDesc(value = "文件大小", required = 0)
    private long fileSize;
    
    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    // ==================== 状态信息 ====================
    
    /** 操作状态: PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, FAILED-失败 */
    @ApiDesc(value = "操作状态", required = 1)
    private String status;
    
    /** 处理进度百分比 0-100 */
    @ApiDesc(value = "处理进度", required = 0)
    private int progressPercent;
    
    /** 总记录数 */
    @ApiDesc(value = "总记录数", required = 0)
    private int totalCount;
    
    /** 成功处理数 */
    @ApiDesc(value = "成功处理数", required = 0)
    private int successCount;
    
    /** 失败处理数 */
    @ApiDesc(value = "失败处理数", required = 0)
    private int failCount;
    
    /** 处理说明 */
    @ApiDesc(value = "处理说明", required = 0)
    private String message;
    
    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    // ==================== 回调信息 ====================
    
    /** 是否需要回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要回调", required = 0)
    private int needCallback;
    
    /** 回调URL */
    @ApiDesc(value = "回调URL", required = 0)
    private String callbackUrl;
    
    /** 回调状态: PENDING-待回调, SUCCESS-回调成功, FAILED-回调失败 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;
    
    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;
    
    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private Date lastCallbackTime;
    
    /** 回调响应结果 */
    @ApiDesc(value = "回调响应结果", required = 0)
    private String callbackResponse;

    public static SaaSFileOperationRecord build() {
        return new SaaSFileOperationRecord();
    }

    public SaaSFileOperationRecord toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public SaaSFileOperationRecord toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SaaSFileOperationRecord toDeptId(long deptId) {
        this.deptId = deptId;
        return this;
    }

    public SaaSFileOperationRecord toDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public SaaSFileOperationRecord toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public SaaSFileOperationRecord toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public SaaSFileOperationRecord toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileOperationRecord toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileOperationRecord toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileOperationRecord toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileOperationRecord toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileOperationRecord toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public SaaSFileOperationRecord toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public SaaSFileOperationRecord toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileOperationRecord toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileOperationRecord toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileOperationRecord toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileOperationRecord toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileOperationRecord toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSFileOperationRecord toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileOperationRecord toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public SaaSFileOperationRecord toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public SaaSFileOperationRecord toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public SaaSFileOperationRecord toMessage(String message) {
        this.message = message;
        return this;
    }

    public SaaSFileOperationRecord toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileOperationRecord toNeedCallback(int needCallback) {
        this.needCallback = needCallback;
        return this;
    }

    public SaaSFileOperationRecord toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public SaaSFileOperationRecord toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public SaaSFileOperationRecord toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public SaaSFileOperationRecord toLastCallbackTime(Date lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public SaaSFileOperationRecord toCallbackResponse(String callbackResponse) {
        this.callbackResponse = callbackResponse;
        return this;
    }
}

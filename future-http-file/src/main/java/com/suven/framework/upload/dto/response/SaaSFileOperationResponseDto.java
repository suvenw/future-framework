package com.suven.framework.upload.dto.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * SaaS文件操作记录响应DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileOperationResponseDto extends BaseTenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @ApiDesc(value = "记录ID", required = 1)
    private long id;

    /** 业务公司id */
    @ApiDesc(value = "业务公司id", required = 0)
    private String companyId;

    /** 业务公司名称 */
    @ApiDesc(value = "业务公司名称", required = 0)
    private String companyName;

    /** 上传人员ID */
    @ApiDesc(value = "上传人员ID", required = 0)
    private long uploadUserId;

    /** 上传人员名称 */
    @ApiDesc(value = "上传人员名称", required = 0)
    private String uploadUserName;

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

    /** 功能类型: IMPORT, EXPORT, UPLOAD, DOWNLOAD */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;

    /** 平台类型: WEB, APP, MINI, API */
    @ApiDesc(value = "平台类型", required = 0)
    private String platformType;

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 1)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 1)
    private String fileType;

    /** 文件大小 */
    @ApiDesc(value = "文件大小", required = 0)
    private long fileSize;

    /** 操作状态 */
    @ApiDesc(value = "操作状态", required = 1)
    private String status;

    /** 处理进度 */
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

    /** 回调状态 */
    @ApiDesc(value = "回调状态", required = 0)
    private String callbackStatus;

    /** 回调失败次数 */
    @ApiDesc(value = "回调失败次数", required = 0)
    private int callbackFailCount;

    /** 最后回调时间 */
    @ApiDesc(value = "最后回调时间", required = 0)
    private Date lastCallbackTime;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private Date createDate;

    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private Date modifyDate;

    /** 字段映射列表 */
    @ApiDesc(value = "字段映射列表", required = 0)
    private List<SaaSFileFieldResponseDto> fieldMappings;

    /** 解释记录列表 */
    @ApiDesc(value = "解释记录列表", required = 0)
    private List<SaaSFileInterpretResponseDto> interpretRecords;

    public static SaaSFileOperationResponseDto build() {
        return new SaaSFileOperationResponseDto();
    }

    public SaaSFileOperationResponseDto toId(long id) {
        this.id = id;
        return this;
    }

    public SaaSFileOperationResponseDto toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public SaaSFileOperationResponseDto toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SaaSFileOperationResponseDto toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public SaaSFileOperationResponseDto toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public SaaSFileOperationResponseDto toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileOperationResponseDto toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileOperationResponseDto toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileOperationResponseDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileOperationResponseDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileOperationResponseDto toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public SaaSFileOperationResponseDto toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public SaaSFileOperationResponseDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileOperationResponseDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileOperationResponseDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileOperationResponseDto toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileOperationResponseDto toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSFileOperationResponseDto toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileOperationResponseDto toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public SaaSFileOperationResponseDto toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public SaaSFileOperationResponseDto toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public SaaSFileOperationResponseDto toMessage(String message) {
        this.message = message;
        return this;
    }

    public SaaSFileOperationResponseDto toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileOperationResponseDto toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public SaaSFileOperationResponseDto toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public SaaSFileOperationResponseDto toLastCallbackTime(Date lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public SaaSFileOperationResponseDto toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public SaaSFileOperationResponseDto toModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public SaaSFileOperationResponseDto toFieldMappings(List<SaaSFileFieldResponseDto> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }

    public SaaSFileOperationResponseDto toInterpretRecords(List<SaaSFileInterpretResponseDto> interpretRecords) {
        this.interpretRecords = interpretRecords;
        return this;
    }
}

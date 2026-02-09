package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * SaaS文件操作记录响应VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileOperationResponseVo extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /** 功能类型 */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;

    /** 平台类型 */
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
    private List<SaaSFileFieldResponseVo> fieldMappings;

    /** 解释记录列表 */
    @ApiDesc(value = "解释记录列表", required = 0)
    private List<SaaSFileInterpretResponseVo> interpretRecords;

    public static SaaSFileOperationResponseVo build() {
        return new SaaSFileOperationResponseVo();
    }

    public SaaSFileOperationResponseVo toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public SaaSFileOperationResponseVo toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SaaSFileOperationResponseVo toUploadUserId(long uploadUserId) {
        this.uploadUserId = uploadUserId;
        return this;
    }

    public SaaSFileOperationResponseVo toUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
        return this;
    }

    public SaaSFileOperationResponseVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileOperationResponseVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileOperationResponseVo toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileOperationResponseVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileOperationResponseVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileOperationResponseVo toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public SaaSFileOperationResponseVo toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public SaaSFileOperationResponseVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileOperationResponseVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileOperationResponseVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileOperationResponseVo toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileOperationResponseVo toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSFileOperationResponseVo toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileOperationResponseVo toTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public SaaSFileOperationResponseVo toSuccessCount(int successCount) {
        this.successCount = successCount;
        return this;
    }

    public SaaSFileOperationResponseVo toFailCount(int failCount) {
        this.failCount = failCount;
        return this;
    }

    public SaaSFileOperationResponseVo toMessage(String message) {
        this.message = message;
        return this;
    }

    public SaaSFileOperationResponseVo toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileOperationResponseVo toCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
        return this;
    }

    public SaaSFileOperationResponseVo toCallbackFailCount(int callbackFailCount) {
        this.callbackFailCount = callbackFailCount;
        return this;
    }

    public SaaSFileOperationResponseVo toLastCallbackTime(Date lastCallbackTime) {
        this.lastCallbackTime = lastCallbackTime;
        return this;
    }

    public SaaSFileOperationResponseVo toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public SaaSFileOperationResponseVo toModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }

    public SaaSFileOperationResponseVo toFieldMappings(List<SaaSFileFieldResponseVo> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }

    public SaaSFileOperationResponseVo toInterpretRecords(List<SaaSFileInterpretResponseVo> interpretRecords) {
        this.interpretRecords = interpretRecords;
        return this;
    }
}

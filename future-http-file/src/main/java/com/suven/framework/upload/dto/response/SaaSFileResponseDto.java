package com.suven.framework.upload.dto.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * SaaS平台文件上传下载响应DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileResponseDto extends BaseTenantEntity implements Serializable {

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 异步任务ID */
    @ApiDesc(value = "异步任务ID", required = 0)
    private String asyncTaskId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 1)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 1)
    private String fileType;

    /** 文件大小 */
    @ApiDesc(value = "文件大小", required = 1)
    private long fileSize;

    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    /** 文件访问完整域名 */
    @ApiDesc(value = "文件访问完整域名", required = 0)
    private String fileAccessUrl;

    /** 临时访问URL */
    @ApiDesc(value = "临时访问URL", required = 0)
    private String tempAccessUrl;

    /** 临时URL过期时间 */
    @ApiDesc(value = "临时URL过期时间", required = 0)
    private LocalDateTime tempUrlExpirationTime;

    /** 临时URL剩余访问次数 */
    @ApiDesc(value = "临时URL剩余访问次数", required = 0)
    private int tempUrlRemainingCount;

    /** 生成状态 0:生成中,1:成功,2:失败 */
    @ApiDesc(value = "生成状态", required = 0)
    private int generateStatus;

    /** 进度百分比 */
    @ApiDesc(value = "进度百分比", required = 0)
    private int progressPercent;

    /** 是否为重复上传 */
    @ApiDesc(value = "是否为重复上传", required = 0)
    private boolean duplicateUpload;

    /** 错误信息 */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private LocalDateTime createDate;

    public static SaaSFileResponseDto build() {
        return new SaaSFileResponseDto();
    }

    public SaaSFileResponseDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileResponseDto toAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
        return this;
    }

    public SaaSFileResponseDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileResponseDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileResponseDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileResponseDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileResponseDto toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileResponseDto toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileResponseDto toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public SaaSFileResponseDto toTempAccessUrl(String tempAccessUrl) {
        this.tempAccessUrl = tempAccessUrl;
        return this;
    }

    public SaaSFileResponseDto toTempUrlExpirationTime(Date tempUrlExpirationTime) {
        this.tempUrlExpirationTime = tempUrlExpirationTime;
        return this;
    }

    public SaaSFileResponseDto toTempUrlRemainingCount(int tempUrlRemainingCount) {
        this.tempUrlRemainingCount = tempUrlRemainingCount;
        return this;
    }

    public SaaSFileResponseDto toGenerateStatus(int generateStatus) {
        this.generateStatus = generateStatus;
        return this;
    }

    public SaaSFileResponseDto toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileResponseDto toDuplicateUpload(boolean duplicateUpload) {
        this.duplicateUpload = duplicateUpload;
        return this;
    }

    public SaaSFileResponseDto toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileResponseDto toRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public SaaSFileResponseDto toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}

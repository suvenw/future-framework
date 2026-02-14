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
public class FileResponseDto extends BaseTenantEntity implements Serializable {

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

    public static FileResponseDto build() {
        return new FileResponseDto();
    }

    public FileResponseDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public FileResponseDto toAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
        return this;
    }

    public FileResponseDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileResponseDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileResponseDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileResponseDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileResponseDto toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public FileResponseDto toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public FileResponseDto toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public FileResponseDto toTempAccessUrl(String tempAccessUrl) {
        this.tempAccessUrl = tempAccessUrl;
        return this;
    }

    public FileResponseDto toTempUrlExpirationTime(LocalDateTime tempUrlExpirationTime) {
        this.tempUrlExpirationTime = tempUrlExpirationTime;
        return this;
    }

    public FileResponseDto toTempUrlRemainingCount(int tempUrlRemainingCount) {
        this.tempUrlRemainingCount = tempUrlRemainingCount;
        return this;
    }

    public FileResponseDto toGenerateStatus(int generateStatus) {
        this.generateStatus = generateStatus;
        return this;
    }

    public FileResponseDto toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public FileResponseDto toDuplicateUpload(boolean duplicateUpload) {
        this.duplicateUpload = duplicateUpload;
        return this;
    }

    public FileResponseDto toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public FileResponseDto toRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public FileResponseDto toCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }
}

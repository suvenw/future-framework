package com.suven.framework.upload.vo.response;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * SaaS平台文件下载响应VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadResponseVo extends BaseIdEntity implements Serializable {

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
    @ApiDesc(value = "文件大小", required = 1)
    private long fileSize;

    /** 文件访问完整域名 */
    @ApiDesc(value = "文件访问完整域名", required = 0)
    private String fileAccessUrl;

    /** 临时访问URL(如果生成了临时URL) */
    @ApiDesc(value = "临时访问URL", required = 0)
    private String tempAccessUrl;

    /** 临时URL过期时间 */
    @ApiDesc(value = "临时URL过期时间", required = 0)
    private Date tempUrlExpirationTime;

    /** 临时URL剩余访问次数 */
    @ApiDesc(value = "临时URL剩余访问次数", required = 0)
    private int tempUrlRemainingCount;

    /** 是否为重复上传 */
    @ApiDesc(value = "是否为重复上传", required = 0)
    private boolean duplicateUpload;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private Date createDate;

    public static FileDownloadResponseVo build() {
        return new FileDownloadResponseVo();
    }

    public FileDownloadResponseVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public FileDownloadResponseVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileDownloadResponseVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileDownloadResponseVo toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public FileDownloadResponseVo toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public FileDownloadResponseVo toTempAccessUrl(String tempAccessUrl) {
        this.tempAccessUrl = tempAccessUrl;
        return this;
    }

    public FileDownloadResponseVo toTempUrlExpirationTime(Date tempUrlExpirationTime) {
        this.tempUrlExpirationTime = tempUrlExpirationTime;
        return this;
    }

    public FileDownloadResponseVo toTempUrlRemainingCount(int tempUrlRemainingCount) {
        this.tempUrlRemainingCount = tempUrlRemainingCount;
        return this;
    }

    public FileDownloadResponseVo toDuplicateUpload(boolean duplicateUpload) {
        this.duplicateUpload = duplicateUpload;
        return this;
    }

    public FileDownloadResponseVo toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}

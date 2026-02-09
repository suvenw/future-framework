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
 * SaaS平台大数据文件生成响应VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileGenerateResponseVo extends BaseIdEntity implements Serializable {

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 异步任务ID(如果为异步生成) */
    @ApiDesc(value = "异步任务ID", required = 0)
    private String asyncTaskId;

    /** 文件名称 */
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

    /** 临时访问URL(如果生成) */
    @ApiDesc(value = "临时访问URL", required = 0)
    private String tempAccessUrl;

    /** 临时URL过期时间 */
    @ApiDesc(value = "临时URL过期时间", required = 0)
    private Date tempUrlExpirationTime;

    /** 生成状态 0:生成中,1:成功,2:失败 */
    @ApiDesc(value = "生成状态", required = 1)
    private int generateStatus;

    /** 进度百分比(0-100) */
    @ApiDesc(value = "进度百分比", required = 0)
    private int progressPercent;

    /** 错误信息(如果失败) */
    @ApiDesc(value = "错误信息", required = 0)
    private String errorMessage;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private Date createDate;

    public static SaaSFileGenerateResponseVo build() {
        return new SaaSFileGenerateResponseVo();
    }

    public SaaSFileGenerateResponseVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileGenerateResponseVo toAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
        return this;
    }

    public SaaSFileGenerateResponseVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileGenerateResponseVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileGenerateResponseVo toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileGenerateResponseVo toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public SaaSFileGenerateResponseVo toTempAccessUrl(String tempAccessUrl) {
        this.tempAccessUrl = tempAccessUrl;
        return this;
    }

    public SaaSFileGenerateResponseVo toTempUrlExpirationTime(Date tempUrlExpirationTime) {
        this.tempUrlExpirationTime = tempUrlExpirationTime;
        return this;
    }

    public SaaSFileGenerateResponseVo toGenerateStatus(int generateStatus) {
        this.generateStatus = generateStatus;
        return this;
    }

    public SaaSFileGenerateResponseVo toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileGenerateResponseVo toErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public SaaSFileGenerateResponseVo toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}

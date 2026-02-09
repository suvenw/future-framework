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
 * SaaS平台文件上传响应VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileUploadResponseVo extends BaseIdEntity implements Serializable {

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

    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    /** 文件访问完整域名 */
    @ApiDesc(value = "文件访问完整域名", required = 0)
    private String fileAccessUrl;

    /** 是否为重复上传 */
    @ApiDesc(value = "是否为重复上传", required = 1)
    private boolean duplicateUpload;

    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private Date createDate;

    public static SaaSFileUploadResponseVo build() {
        return new SaaSFileUploadResponseVo();
    }

    public SaaSFileUploadResponseVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileUploadResponseVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileUploadResponseVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileUploadResponseVo toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileUploadResponseVo toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileUploadResponseVo toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public SaaSFileUploadResponseVo toDuplicateUpload(boolean duplicateUpload) {
        this.duplicateUpload = duplicateUpload;
        return this;
    }

    public SaaSFileUploadResponseVo toRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public SaaSFileUploadResponseVo toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}

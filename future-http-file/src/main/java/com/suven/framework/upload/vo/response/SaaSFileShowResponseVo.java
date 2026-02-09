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
 * SaaS平台文件展示响应VO
 * 用于文件列表展示
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileShowResponseVo extends BaseIdEntity implements Serializable {

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

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

    /** 临时访问URL */
    @ApiDesc(value = "临时访问URL", required = 0)
    private String tempAccessUrl;

    /** 临时URL过期时间 */
    @ApiDesc(value = "临时URL过期时间", required = 0)
    private Date tempUrlExpirationTime;

    /** 生成状态 0:生成中,1:成功,2:失败 */
    @ApiDesc(value = "生成状态", required = 0)
    private int generateStatus;

    /** 进度百分比(0-100) */
    @ApiDesc(value = "进度百分比", required = 0)
    private int progressPercent;

    /** 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否 */
    @ApiDesc(value = "是否需要解释数据", required = 0)
    private int interpretData;

    /** 解释数据总条数 */
    @ApiDesc(value = "解释数据总条数", required = 0)
    private int interpretDataTotal;

    /** 备注 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    /** 创建时间 */
    @ApiDesc(value = "创建时间", required = 1)
    private Date createDate;

    /** 修改时间 */
    @ApiDesc(value = "修改时间", required = 0)
    private Date modifyDate;

    public static SaaSFileShowResponseVo build() {
        return new SaaSFileShowResponseVo();
    }

    public SaaSFileShowResponseVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileShowResponseVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileShowResponseVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileShowResponseVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileShowResponseVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileShowResponseVo toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileShowResponseVo toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileShowResponseVo toFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
        return this;
    }

    public SaaSFileShowResponseVo toTempAccessUrl(String tempAccessUrl) {
        this.tempAccessUrl = tempAccessUrl;
        return this;
    }

    public SaaSFileShowResponseVo toTempUrlExpirationTime(Date tempUrlExpirationTime) {
        this.tempUrlExpirationTime = tempUrlExpirationTime;
        return this;
    }

    public SaaSFileShowResponseVo toGenerateStatus(int generateStatus) {
        this.generateStatus = generateStatus;
        return this;
    }

    public SaaSFileShowResponseVo toProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
        return this;
    }

    public SaaSFileShowResponseVo toInterpretData(int interpretData) {
        this.interpretData = interpretData;
        return this;
    }

    public SaaSFileShowResponseVo toInterpretDataTotal(int interpretDataTotal) {
        this.interpretDataTotal = interpretDataTotal;
        return this;
    }

    public SaaSFileShowResponseVo toRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public SaaSFileShowResponseVo toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public SaaSFileShowResponseVo toModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }
}

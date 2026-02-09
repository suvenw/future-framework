package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * SaaS平台文件下载请求VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileDownloadRequestVo extends HttpRequestByIdPageVo {

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 1)
    private long fileUploadStorageId;

    /** 是否生成临时访问URL 0:直接下载,1:生成临时URL */
    @ApiDesc(value = "是否生成临时访问URL", required = 0)
    private int generateTempUrl;

    /** 临时URL有效期(秒),默认3600秒 */
    @ApiDesc(value = "临时URL有效期(秒)", required = 0)
    private int expirationTime;

    public static SaaSFileDownloadRequestVo build() {
        return new SaaSFileDownloadRequestVo();
    }

    public SaaSFileDownloadRequestVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileDownloadRequestVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileDownloadRequestVo toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileDownloadRequestVo toGenerateTempUrl(int generateTempUrl) {
        this.generateTempUrl = generateTempUrl;
        return this;
    }

    public SaaSFileDownloadRequestVo toExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }
}

package com.suven.framework.upload.dto.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * SaaS平台文件上传下载请求DTO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileRequestDto extends BaseTenantEntity implements Serializable {

    /** 文件存储信息id */
    @ApiDesc(value = "文件存储信息id", required = 0)
    private long fileUploadStorageId;

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

    /** 幂等性标识 */
    @ApiDesc(value = "幂等性标识", required = 0)
    private String idempotent;

    /** 文件名称,原来文件上传的名称 */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 0)
    private String fileType;

    /** 文件大小 */
    @ApiDesc(value = "文件大小", required = 0)
    private long fileSize;

    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    /** 是否需要解释数据 */
    @ApiDesc(value = "是否需要解释数据", required = 0)
    private int interpretData;

    /** 第三方API地址 */
    @ApiDesc(value = "第三方API地址", required = 0)
    private String thirdPartyApiUrl;

    /** 第三方API请求方法 */
    @ApiDesc(value = "第三方API请求方法", required = 0)
    private String thirdPartyApiMethod;

    /** 第三方API请求头 */
    @ApiDesc(value = "第三方API请求头", required = 0)
    private String thirdPartyApiHeaders;

    /** 第三方API请求参数 */
    @ApiDesc(value = "第三方API请求参数", required = 0)
    private String thirdPartyApiParams;

    /** 生成文件类型 */
    @ApiDesc(value = "生成文件类型", required = 0)
    private String fileTypeForGenerate;

    /** 生成文件名称前缀 */
    @ApiDesc(value = "生成文件名称前缀", required = 0)
    private String fileNamePrefix;

    /** 超时时间(秒) */
    @ApiDesc(value = "超时时间(秒)", required = 0)
    private int timeout;

    /** 是否生成临时URL */
    @ApiDesc(value = "是否生成临时URL", required = 0)
    private int generateTempUrl;

    /** 临时URL有效期(秒) */
    @ApiDesc(value = "临时URL有效期(秒)", required = 0)
    private int expirationTime;

    /** 创建时间-开始 */
    @ApiDesc(value = "创建时间-开始", required = 0)
    private LocalDateTime createDateStart;
    
    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private LocalDateTime createDateEnd;

    public static SaaSFileRequestDto build() {
        return new SaaSFileRequestDto();
    }

    public SaaSFileRequestDto toFileUploadStorageId(long fileUploadStorageId) {
        this.fileUploadStorageId = fileUploadStorageId;
        return this;
    }

    public SaaSFileRequestDto toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileRequestDto toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileRequestDto toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileRequestDto toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileRequestDto toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileRequestDto toIdempotent(String idempotent) {
        this.idempotent = idempotent;
        return this;
    }

    public SaaSFileRequestDto toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileRequestDto toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileRequestDto toFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public SaaSFileRequestDto toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileRequestDto toInterpretData(int interpretData) {
        this.interpretData = interpretData;
        return this;
    }

    public SaaSFileRequestDto toThirdPartyApiUrl(String thirdPartyApiUrl) {
        this.thirdPartyApiUrl = thirdPartyApiUrl;
        return this;
    }

    public SaaSFileRequestDto toThirdPartyApiMethod(String thirdPartyApiMethod) {
        this.thirdPartyApiMethod = thirdPartyApiMethod;
        return this;
    }

    public SaaSFileRequestDto toThirdPartyApiHeaders(String thirdPartyApiHeaders) {
        this.thirdPartyApiHeaders = thirdPartyApiHeaders;
        return this;
    }

    public SaaSFileRequestDto toThirdPartyApiParams(String thirdPartyApiParams) {
        this.thirdPartyApiParams = thirdPartyApiParams;
        return this;
    }

    public SaaSFileRequestDto toFileTypeForGenerate(String fileTypeForGenerate) {
        this.fileTypeForGenerate = fileTypeForGenerate;
        return this;
    }

    public SaaSFileRequestDto toFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
        return this;
    }

    public SaaSFileRequestDto toTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public SaaSFileRequestDto toGenerateTempUrl(int generateTempUrl) {
        this.generateTempUrl = generateTempUrl;
        return this;
    }

    public SaaSFileRequestDto toExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public SaaSFileRequestDto toCreateDateStart(LocalDateTime createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public SaaSFileRequestDto toCreateDateEnd(LocalDateTime createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }


}

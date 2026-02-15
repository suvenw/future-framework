package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpFromRequestVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * SaaS平台大数据文件生成请求VO
 * 用于调用第三方接口生成文件并下载
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileGenerateRequestVo extends HttpFromRequestVo {

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
    @ApiDesc(value = "上传业务产品名称", required = 1)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 1)
    private String fileBusinessName;

    /** 第三方API地址 */
    @ApiDesc(value = "第三方API地址", required = 1)
    private String thirdPartyApiUrl;

    /** 第三方API请求方法 GET/POST */
    @ApiDesc(value = "第三方API请求方法", required = 1)
    private String thirdPartyApiMethod;

    /** 第三方API请求头JSON格式 */
    @ApiDesc(value = "第三方API请求头", required = 0)
    private String thirdPartyApiHeaders;

    /** 第三方API请求参数JSON格式 */
    @ApiDesc(value = "第三方API请求参数", required = 0)
    private String thirdPartyApiParams;

    /** 生成文件类型 pdf/excel/csv等 */
    @ApiDesc(value = "生成文件类型", required = 1)
    private String fileType;

    /** 生成文件名称前缀 */
    @ApiDesc(value = "生成文件名称前缀", required = 1)
    private String fileNamePrefix;

    /** 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中 1.是,0.否 */
    @ApiDesc(value = "是否需要解释数据", required = 0)
    private int interpretData;

    /** 超时时间(秒),默认300秒 */
    @ApiDesc(value = "超时时间(秒)", required = 0)
    private int timeout;

    public static FileGenerateRequestVo build() {
        return new FileGenerateRequestVo();
    }

    public FileGenerateRequestVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileGenerateRequestVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public FileGenerateRequestVo toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public FileGenerateRequestVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileGenerateRequestVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileGenerateRequestVo toThirdPartyApiUrl(String thirdPartyApiUrl) {
        this.thirdPartyApiUrl = thirdPartyApiUrl;
        return this;
    }

    public FileGenerateRequestVo toThirdPartyApiMethod(String thirdPartyApiMethod) {
        this.thirdPartyApiMethod = thirdPartyApiMethod;
        return this;
    }

    public FileGenerateRequestVo toThirdPartyApiHeaders(String thirdPartyApiHeaders) {
        this.thirdPartyApiHeaders = thirdPartyApiHeaders;
        return this;
    }

    public FileGenerateRequestVo toThirdPartyApiParams(String thirdPartyApiParams) {
        this.thirdPartyApiParams = thirdPartyApiParams;
        return this;
    }

    public FileGenerateRequestVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileGenerateRequestVo toFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
        return this;
    }

    public FileGenerateRequestVo toInterpretData(int interpretData) {
        this.interpretData = interpretData;
        return this;
    }

    public FileGenerateRequestVo toTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }
}

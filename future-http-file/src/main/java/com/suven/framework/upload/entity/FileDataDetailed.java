package com.suven.framework.upload.entity;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;

/**
 * 对应的mongodb表的基本信息数据,方便业务搜索数据使用;
 */
public class FileDataDetailed extends BaseTenantEntity {

    @ApiDesc(value = "注册应用id", required = 0)
    private String appId; //appid 应用id
    @ApiDesc(value = "注册应用名称", required = 0)
    private String appName;  //应用名称
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private long clientId;
    @ApiDesc(value = "业务公司id", required = 0)
    private String  companyId;
    @ApiDesc(value = "业务公司名称", required = 0)
    private String companyName;
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;
    @ApiDesc(value = "注册应用授权Id,对应 FileStorageEnum", required = 0)
    private String storageConfigId;
    @ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
    private String fileSourceName;
    @ApiDesc(value = "文件名称类型:txt, xml,jpg 等", required = 0)
    private String fileType;

    @ApiDesc(value = "检查数据错误提示编码", required = 0)
    private String errorCode;
    @ApiDesc(value = "检查数据错误提示信息", required = 0)
    private String errorMessage;

    public static FileDataDetailed build(){
        return new FileDataDetailed();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFileProductName() {
        return fileProductName;
    }

    public void setFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
    }

    public String getFileBusinessName() {
        return fileBusinessName;
    }

    public void setFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
    }

    public long getUseBusinessId() {
        return useBusinessId;
    }

    public void setUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
    }

    public String getStorageConfigId() {
        return storageConfigId;
    }

    public void setStorageConfigId(String storageConfigId) {
        this.storageConfigId = storageConfigId;
    }

    public String getFileSourceName() {
        return fileSourceName;
    }

    public void setFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

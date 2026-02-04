package com.suven.framework.upload.entity;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;

public class FileUploadApp extends BaseTenantEntity {

    @ApiDesc(value = "注册应用id", required = 0)
    private String appId; //appid 应用id
    @ApiDesc(value = "注册应用名称", required = 0)
    private String appName;  //应用名称
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private String clientId; //appid 授权id
    @ApiDesc(value = "注册应用授权密匙", required = 0)
    private String clientSecret; // 授权密匙
    @ApiDesc(value = "注册应用授权加密干扰盐", required = 0)
    private String clientSalt;  //加密干扰盐,各业务方独立应用,只用于服务端加密,解密使用

    @ApiDesc(value = "注册应用业务前缀path,用于区别业务", required = 0)
    private String pathName;  //应用业务前缀path,用于区别业务
    @ApiDesc(value = "注册应用回调地址", required = 0)
    private String clientUrl; //回调地址
    @ApiDesc(value = "有效时间:当前时间+毫秒数", required = 0)
    private String accessExpirationTime; //有效时间
    @ApiDesc(value = "url授权可访问次数", required = 0)
    private int accessCount; // 可访问次数
    //sourcepath = http://app.img.abc.jpg, aesUrl = http://app.img.key.jpg
    //aes format = key=appId+clientId+clientSalt+time+abc,
    @ApiDesc(value = "生成临时访问url规则", required = 0)
    private String accessUrlFormat;//
    @ApiDesc(value = "生成临时访问url", required = 0)
    private String  accessUrl;


    @ApiDesc(value = "文件存储配置id", required = 0)
    private long fileAppStorageConfigId;

    /**
     * 节点地址
     * 1. MinIO：https://www.iocoder.cn/Spring-Boot/MinIO 。例如说，http://127.0.0.1:9000
     * 2. 阿里云：https://help.aliyun.com/document_detail/31837.html
     * 3. 腾讯云：https://cloud.tencent.com/document/product/436/6224
     * 4. 七牛云：https://developer.qiniu.com/kodo/4088/s3-access-domainname
     * 5. 华为云：https://developer.huaweicloud.com/endpoint?OBS
     */
//    @NotNull(message = "endpoint 不能为空")
    @ApiDesc(value = "节点地址,默认值", required = 0)
    private String endpoint;
    /**
     * 存储 Bucket
     */
    @ApiDesc(value = "bucket,默认值", required = 0)
    private String bucket;

    /**
     * 访问 Key
     * 1. MinIO：https://www.iocoder.cn/Spring-Boot/MinIO
     * 2. 阿里云：https://ram.console.aliyun.com/manage/ak
     * 3. 腾讯云：https://console.cloud.tencent.com/cam/capi
     * 4. 七牛云：https://portal.qiniu.com/user/key
     * 5. 华为云：https://support.huaweicloud.com/qs-obs/obs_qs_0005.html
     */
    @ApiDesc(value = "accessKey 不能为空,默认值", required = 0)
    private String accessKey;
    /**  访问 Secret */
    @ApiDesc(value = "accessSecret 不能为空,默认值", required = 0)
    private String accessSecret;


    public static FileUploadApp build() {
       return new FileUploadApp();
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSalt() {
        return clientSalt;
    }

    public void setClientSalt(String clientSalt) {
        this.clientSalt = clientSalt;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getAccessExpirationTime() {
        return accessExpirationTime;
    }

    public void setAccessExpirationTime(String accessExpirationTime) {
        this.accessExpirationTime = accessExpirationTime;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public String getAccessUrlFormat() {
        return accessUrlFormat;
    }

    public void setAccessUrlFormat(String accessUrlFormat) {
        this.accessUrlFormat = accessUrlFormat;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public long getFileAppStorageConfigId() {
        return fileAppStorageConfigId;
    }

    public void setFileAppStorageConfigId(long fileAppStorageConfigId) {
        this.fileAppStorageConfigId = fileAppStorageConfigId;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}

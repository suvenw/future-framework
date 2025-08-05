package com.suven.framework.file.config;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.suven.framework.common.constants.GlobalConfigConstants;

import java.util.HashMap;
import java.util.Map;

import static com.suven.framework.common.constants.GlobalConfigConstants.*;


/**
 * 文件上传相关配置文件
 */
@Configuration
@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_FILE_OSS_ENABLED,  matchIfMissing = true)
//@NacosConfigurationProperties( groupId= SERVICE_NAME , dataId = TOP_SERVER_FILE_OSS_NAME,prefix= TOP_SERVER_FILE_OSS_CONFIG, type = ConfigType.PROPERTIES, autoRefreshed = true)
@ConfigurationProperties(value = TOP_SERVER_FILE_OSS_CONFIG)
public class FileConfigSetting {



    /** 文件写入的磁盘路径 **/
    private String path =  "D://upload"  ;
    /** wifi下最大文件块大小为6M **/
    private long  wifiSize = 6 * 1024 * 1024;
    /** 4G 下最大文件块大小为6M **/
    private long  g4Size = 6L * 1024 * 1024;
    /** 完整文件大小限制 为10M **/
    private long  fileSize =  10L * 1024 * 1024;

    private String fileType =  "m4a,mp3,png,jpg,jpeg,gif" ;
    private String domain ="http://127.0.0.1";
    private boolean checkParam =false;

    private FileOSSConfig oss = new FileOSSConfig();

    @Bean("ossClient")
    @ConditionalOnProperty(name= TOP_SERVER_FILE_OSS_ENABLED,matchIfMissing = false)
    public OSS buildOSS(){
        OSS ossClient = new OSSClientBuilder().build(oss.endpoint, oss.accessKeyId, oss.accessKeySecret);
        return ossClient;
    }



    /**    上传成功再返回的文件路径**/
    public String getOssPath(String bucketName,String fileName){
        //上传成功再返回的文件路径
        String url;
        if(getOss().isHttps()){
            url =  getOss().getEndpoint().replaceFirst("https://","https://"+ bucketName+".")+"/"+fileName;
        }else {
            url =  getOss().getEndpoint().replaceFirst("http://","http://"+ bucketName+".")+"/"+fileName;
        }
        return url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if(null != path){
        this.path = path;
        }
    }

    public long getWifiSize() {
        return wifiSize;
    }

    public void setWifiSize(long wifiSize) {
        if( wifiSize > 0) {
            this.wifiSize = wifiSize;
        }
    }

    public long getG4Size() {
        return g4Size;
    }

    public void setG4Size(long g4Size) {
        if( g4Size > 0){
            this.g4Size = g4Size;
        }

    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        if( fileSize > 0) {
            this.fileSize = fileSize;
        }
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        if(null != fileType){
        this.fileType = fileType;
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isCheckParam() {
        return checkParam;
    }

    public void setCheckParam(boolean checkParam) {
        this.checkParam = checkParam;
    }

    public boolean validatorFileType(String fileType){
        if(null == fileType || "".equals(fileType)){
            return false;
        }
        String type = fileType.toLowerCase();
        String[] files = this.getFileType().split(",|;");
        for(String file : files){
            if(file.equals(type)){
                return true;
            }
        }
        return false;

    }

    public boolean validatorFileSize(long  fileSize){
        if(fileSize < 0 ){
            return false;
        }
       return (fileSize >= this.getFileSize());

    }

    public FileOSSConfig getOss() {
        return oss;
    }

    public void setOss(FileOSSConfig oss) {
        this.oss = oss;
    }


    /**
     * 阿里云服务oss 存储配置文件实现类
     */
    public static class FileOSSConfig {

        private String domain ="http://127.0.0.1";
        private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        private String accessKeyId = "LTAIZc2IX6pLi7b3";
        private String accessKeySecret = "T9wsDLO5Iz543AZZOvT7x9RKglYhWV";
        private String bucketName = "bucketName";
        private String rootPath;
        private String bucketKeyValue;
        private boolean https =false;

        private Map<String,String> bucketNameKVMap = new HashMap<>();


        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            if(null != endpoint) {
                this.endpoint = endpoint;
            }
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            if(null != accessKeyId) {
                this.accessKeyId = accessKeyId;
            }
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            if(null != accessKeySecret) {
                this.accessKeySecret = accessKeySecret;
            }
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            if(null != bucketName) {
                this.bucketName = bucketName;
            }
        }

        public boolean isHttps() {
            return https;
        }

        public void setHttps(boolean https) {
            this.https = https;
        }

        public String getRootPath() {
            return rootPath;
        }

        public void setRootPath(String rootPath) {
            this.rootPath = rootPath;
        }

        public String getBucketKeyValue() {
            return bucketKeyValue;
        }

        public void setBucketKeyValue(String bucketKeyValue) {
            this.bucketKeyValue = bucketKeyValue;
            if(null != bucketKeyValue && !"".equals(bucketKeyValue)){
               String[] keyValues =  bucketKeyValue.split(",|;");
               if(keyValues != null && keyValues.length%2 == 0){
                   for (int i = 0 ,j = keyValues.length; i<j; i++) {
                       bucketNameKVMap.put(keyValues[i],keyValues[i+1]);
                       i++;
                   }
               }
            }
        }

        public String  getBucketName(String bucketNameKey){
                if(null == bucketNameKey || null == bucketNameKVMap || bucketNameKVMap.isEmpty()){
                    return getBucketName();
                }
               String bucketName =  bucketNameKVMap.get(bucketNameKey);
                if(null == bucketName){
                    bucketName = getBucketName();
                }
                return bucketName;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }



}

//package top.suven.future.file.config;
//
//
//import com.alibaba.nacos.api.config.ConfigType;
//import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.suven.framework.common.constants.GlobalConfigConstants;
//
//import static com.alibaba.nacos.api.common.Constants.DEFAULT_GROUP;
//
//
///**
// * 文件上传相关配置文件
// */
//@Configuration("fileOSSConfig")
//@ConfigurationProperties(value = GlobalConfigConstants.TOP_SERVER_FILE_OSS_NAME)
//@NacosConfigurationProperties( groupId= DEFAULT_GROUP, dataId = "apple",prefix=GlobalConfigConstants.TOP_SERVER_FILE_OSS_CONFIG, type = ConfigType.PROPERTIES, autoRefreshed = true)
//@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_FILE_OSS_ENABLED,  matchIfMissing = true)
//public class FileOSSConfig {
//
//
//    private String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
//    private String accessKeyId = "LTAIZc2IX6pLi7b3";
//    private String accessKeySecret = "T9wsDLO5Iz543AZZOvT7x9RKglYhWV";
//    private String bucketName = "rmxzsoft";
//    private String rootPath;
//
//
//    @Bean("ossClient")
//    @ConditionalOnProperty(name= GlobalConfigConstants.TOP_SERVER_FILE_OSS_ENABLED,matchIfMissing = false)
//    public OSS buildOSS(){
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        return ossClient;
//    }
//
//
//    public String getEndpoint() {
//        return endpoint;
//    }
//
//    public void setEndpoint(String endpoint) {
//        if(null != endpoint)
//        this.endpoint = endpoint;
//    }
//
//    public String getAccessKeyId() {
//        return accessKeyId;
//    }
//
//    public void setAccessKeyId(String accessKeyId) {
//        if(null != accessKeyId)
//        this.accessKeyId = accessKeyId;
//    }
//
//    public String getAccessKeySecret() {
//        return accessKeySecret;
//    }
//
//    public void setAccessKeySecret(String accessKeySecret) {
//        if(null != accessKeySecret)
//        this.accessKeySecret = accessKeySecret;
//    }
//
//    public String getBucketName() {
//        return bucketName;
//    }
//
//    public void setBucketName(String bucketName) {
//        if(null != bucketName)
//        this.bucketName = bucketName;
//    }
//
//    public String getRootPath() {
//        return rootPath;
//    }
//
//    public void setRootPath(String rootPath) {
//        this.rootPath = rootPath;
//    }
//}

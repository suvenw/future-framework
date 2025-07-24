package com.suven.framework.core.aliyun.sms;


import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(name = GlobalConfigConstants.TOP_THIRD_ALIYUN_CONFIG_ENABLED,  matchIfMissing = false)
@ConfigurationProperties(prefix = GlobalConfigConstants.TOP_THIRD_ALIYUN_CONFIG)
//@NacosConfigurationProperties( groupId= GlobalConfigConstants.SERVICE_NAME, dataId = GlobalConfigConstants.TOP_THIRD_ALIYUN_CONFIG_NAME, prefix= GlobalConfigConstants.TOP_THIRD_ALIYUN_CONFIG, type = ConfigType.PROPERTIES,autoRefreshed = true)

public class AliyunSmsConfigSetting {


//    private final static String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
//    private final static String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//
//    private final static String accessKeyId = "LTAIZc2IX6pLi7b3";
//    private final static String accessSecret = "T9wsDLO5Iz543AZZOvT7x9RKglYhWV";

//    private final static String regionId = "cn-cle"; //地域名；
    /** 注册软件标题[爱看app]"云通信" 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版**/
    private String signName;
    /** "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改） **/
    private String product;
    /** accessKeyId 密匙ID **/
    private String domain;
    /** accessSecret 密匙**/
    private String accessKeyId;
    /** **/
    private String accessSecret;
    /** "cn-cle"; //地域名；**/
    private String regionId;
    private Template template = new Template();


    private int connectTimeout;
    private int readTimeout;

//    private String singName = ""; //"云通信" 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
//    private String templateCode = "";//"SMS_1000000" 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//





    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }


    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getRegionId() {
        return regionId;
    }


    public int getConnectTimeout() {
        return connectTimeout;
    }

    public AliyunSmsConfigSetting setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public AliyunSmsConfigSetting setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }





    /** 注册,邦定手机,登陆, 找回密码 **/
    public static class  Template{
        private String register = "";
        private String phoneBind  = "";
        private String login  = "";
        private String passwordReset = "";
        private String phoneRelieve = "";
        private String tradePassword = "";

        public String getPhoneRelieve() {
            return phoneRelieve;
        }

        public void setPhoneRelieve(String phoneRelieve) {
            this.phoneRelieve=phoneRelieve;
        }

        public String getTradePassword() {
            return tradePassword;
        }

        public void setTradePassword(String tradePassword) {
            this.tradePassword=tradePassword;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getPhoneBind() {
            return phoneBind;
        }

        public void setPhoneBind(String phoneBind) {
            this.phoneBind = phoneBind;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPasswordReset() {
            return passwordReset;
        }

        public void setPasswordReset(String passwordReset) {
            this.passwordReset = passwordReset;
        }
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Template getTemplate() {
        return template;
    }




}

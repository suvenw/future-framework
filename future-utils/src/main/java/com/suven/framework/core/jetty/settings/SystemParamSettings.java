package com.suven.framework.core.jetty.settings;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
//@ConfigurationProperties(value = GlobalConfigConstants.SYSTEM_PARAM_SETTINGS)
@ConditionalOnProperty(name = GlobalConfigConstants.SYSTEM_PARAM_ENABLED,  matchIfMissing = true)

//@NacosConfigurationProperties( groupId= SERVICE_NAME, dataId = SYSTEM_PARAM_SETTINGS_NAME,prefix= SYSTEM_PARAM_SETTINGS,  autoRefreshed = true)
public class SystemParamSettings {

    private String documentRoot = "/data/server/";

    private String contextPath;

    //是否验证参数加密
    private boolean checkParamSign = true;
    //是否开始aop切面日志
    private boolean logAop;

    private boolean isHeaderToken = false;
    private String headerToBodyField = "userId,accessToken";

    private boolean checkBody = false;

    /** 用于项目环境与参数管理,不作动态配置**/
    private int port;
    private String host;
    private String serverName = "serverName";
    private String serverModel = "jetty";

    public String getDocumentRoot() {
        return documentRoot;
    }

    public void setDocumentRoot(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean isCheckParamSign() {
        return checkParamSign;
    }

    public void setCheckParamSign(boolean checkParamSign) {
        this.checkParamSign = checkParamSign;
    }

    public boolean isLogAop() {
        return logAop;
    }

    public void setLogAop(boolean logAop) {
        this.logAop = logAop;
    }

    public boolean isHeaderToken() {
        return isHeaderToken;
    }

    public void setHeaderToken(boolean headerToken) {
        isHeaderToken = headerToken;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerModel() {
        return serverModel;
    }

    public void setServerModel(String serverModel) {
        this.serverModel = serverModel;
    }

    public boolean isCheckBody() {
        return checkBody;
    }

    public void setCheckBody(boolean checkBody) {
        this.checkBody = checkBody;
    }

    public String getHeaderToBodyField() {
        return headerToBodyField;
    }
    public List<String > getHeaderField() {
        if(null != headerToBodyField){
           return Arrays.asList(headerToBodyField.split(","));
        }
        return new ArrayList<>();
    }


    public void setHeaderToBodyField(String headerToBodyField) {
        this.headerToBodyField = headerToBodyField;
    }
}

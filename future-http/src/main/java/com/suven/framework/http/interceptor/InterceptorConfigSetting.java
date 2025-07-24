package com.suven.framework.http.interceptor;

import com.suven.framework.core.ObjectTrue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * Description (说明): 处理器配置文件实现类
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @date 创建时间: 2023-12-18
 **/
@Configuration
@ConditionalOnProperty(name = InterceptorConstants.SAAS_CLOUD_HANDLER_CONFIG_ENABLED,  matchIfMissing = true)
@ConfigurationProperties(prefix = InterceptorConstants.SAAS_CLOUD_HANDLER_CONFIG)
//@RefreshScope
@Setter
@Getter
public class InterceptorConfigSetting {

    private boolean enabled  = true;
    private  HandlerInterceptorSetting handler = new HandlerInterceptorSetting();
    private  ServiceSetting service = new ServiceSetting();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public HandlerInterceptorSetting getHandler() {
        return handler;
    }

    public void setHandler(HandlerInterceptorSetting handler) {
        this.handler = handler;
    }

    public ServiceSetting getService() {
        return service;
    }

    public void setService(ServiceSetting service) {
        this.service = service;
    }

    @Setter
    @Getter
    public static class ServiceSetting{
        private boolean isHeaderToken = true;
        private String headerField;
        private String excludeField;
        //是否验证参数加密
        private boolean isParamSign= true;
        //是否开始aop切面日志
        private boolean logAop;

        private String headerToBodyField = "userId,accessToken";

        private boolean checkBody = false;

        /** 用于项目环境与参数管理,不作动态配置**/
        private int port;
        private String host;
        private String serverName = "serverName";
        private String serverModel = "jetty";

        public List<String> getHeaderFieldList(){
            if (ObjectTrue.isEmpty(headerField)){
                return new ArrayList<>();
            }
            return Arrays.asList(headerField.split(",|:"));
        }
        public List<String> getExcludeFieldList(){
            if (ObjectTrue.isEmpty(excludeField)){
                return new ArrayList<>();
            }
            return Arrays.asList(excludeField.split(",|:"));
        }

        public boolean isHeaderToken() {
            return isHeaderToken;
        }

        public void setHeaderToken(boolean headerToken) {
            isHeaderToken = headerToken;
        }

        public String getHeaderField() {
            return headerField;
        }

        public void setHeaderField(String headerField) {
            this.headerField = headerField;
        }

        public String getExcludeField() {
            return excludeField;
        }

        public void setExcludeField(String excludeField) {
            this.excludeField = excludeField;
        }

        public boolean isParamSign() {
            return isParamSign;
        }

        public void setParamSign(boolean paramSign) {
            isParamSign = paramSign;
        }

        public boolean isLogAop() {
            return logAop;
        }

        public void setLogAop(boolean logAop) {
            this.logAop = logAop;
        }

        public String getHeaderToBodyField() {
            return headerToBodyField;
        }

        public void setHeaderToBodyField(String headerToBodyField) {
            this.headerToBodyField = headerToBodyField;
        }

        public boolean isCheckBody() {
            return checkBody;
        }

        public void setCheckBody(boolean checkBody) {
            this.checkBody = checkBody;
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
    }

    @Setter
    @Getter
    public static class HandlerInterceptorSetting{
        private String white;
        /***Handler 处理器,是否上下架设置,排除法,多个用英文豆号,分隔,eg: "1,2,3" **/
        private String exclude ;
        private String paths;
        private String excludePaths ;

        //是否下架
        public boolean  isExclude(int order) {
            if (exclude == null){
                return false;
            }
            return Arrays.asList( exclude.split(";|,")).contains(String.valueOf(order));
        }
        // 是否白名单
        public boolean isWhite(int isRun){
            if (white == null){
                return false;
            }
            return Arrays.asList( white.split(";|,")).contains(String.valueOf(isRun));
        }
        public List<String> excludePathList(){
            if (excludePaths == null){
                return new ArrayList<>();
            }
            return Arrays.asList( excludePaths.split(";|,")) ;
        }
        public List<String> pathList(){
            if (paths == null){
                return new ArrayList<>();
            }
            return Arrays.asList( paths.split(";|,")) ;
        }

        public String getWhite() {
            return white;
        }

        public void setWhite(String white) {
            this.white = white;
        }

        public String getExclude() {
            return exclude;
        }

        public void setExclude(String exclude) {
            this.exclude = exclude;
        }

        public String getPaths() {
            return paths;
        }

        public void setPaths(String paths) {
            this.paths = paths;
        }

        public String getExcludePaths() {
            return excludePaths;
        }

        public void setExcludePaths(String excludePaths) {
            this.excludePaths = excludePaths;
        }
    }
}

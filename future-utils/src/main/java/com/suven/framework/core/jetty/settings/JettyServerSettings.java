package com.suven.framework.core.jetty.settings;


import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Title: JettyServerSettings.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 * Description: (说明)  jetty service 服务启动环境配置类
 * Copyright: (c) 2018 gc by https://www.suven.top
 */

@Configuration
@ConfigurationProperties(prefix = GlobalConfigConstants.JETTY_SERVER_SETTINGS)
@ConditionalOnProperty(name = GlobalConfigConstants.JETTY_SERVER_ENABLED,  matchIfMissing = true)

//@NacosConfigurationProperties( groupId= SERVICE_NAME, dataId = JETTY_SERVER_SETTINGS_NAME,prefix= JETTY_SERVER_SETTINGS,  autoRefreshed = true)
public class JettyServerSettings {





    private JettyLowResourceMonitorSettings lowResources = new JettyLowResourceMonitorSettings();
    private JettyThreadPoolSettings threadPool = new JettyThreadPoolSettings();
    private ViewResolverSettings view = new ViewResolverSettings();
    private HttpDosFilterSettings dos = new HttpDosFilterSettings();
    private InterServerConfigSetting inter = new InterServerConfigSetting();

    @Override
    public String toString() {
        return "JettyServerSettings{" +
                "lowResources=" + lowResources +
                ", threadPool=" + threadPool +
                ", view=" + view +
                ", dos=" + dos +
                ", inter=" + inter +
                '}';
    }

    public JettyLowResourceMonitorSettings getLowResources() {
        return lowResources;
    }
    public void setLowResources(JettyLowResourceMonitorSettings lowResources) {
        this.lowResources = lowResources;
    }
    public JettyThreadPoolSettings getThreadPool() {
        return threadPool;
    }
    public void setThreadPool(JettyThreadPoolSettings threadPool) {
        this.threadPool = threadPool;
    }

    public ViewResolverSettings getView() {
        return view;
    }

    public void setView(ViewResolverSettings view) {
        this.view = view;
    }

    public HttpDosFilterSettings getDos() {
        return dos;
    }

    public void setDos(HttpDosFilterSettings dos) {
        this.dos = dos;
    }

    public InterServerConfigSetting getInter() {
        return inter;
    }

    public void setInter(InterServerConfigSetting inter) {
        this.inter = inter;
    }

    public static class ViewResolverSettings{
        private String prefix = "/templates/_views/";
        private String contentType = "text/html;charset=UTF-8";
        private String suffix = ".html";
        private boolean devMode = true;
        private boolean cache = false;
        private int order = 0;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public boolean isDevMode() {
            return devMode;
        }

        public void setDevMode(boolean devMode) {
            this.devMode = devMode;
        }

        public boolean isCache() {
            return cache;
        }

        public void setCache(boolean cache) {
            this.cache = cache;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
    }
    public static  class JettyLowResourceMonitorSettings {
        private int period = 1000;
        private int idleTimeout = 200;
        private boolean monitorThreads = true;
        private int maxConnections = 0;
        private int maxMemory;
        private int maxLowResourcesTime = 5000;

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public int getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(int idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public boolean isMonitorThreads() {
            return monitorThreads;
        }

        public void setMonitorThreads(boolean monitorThreads) {
            this.monitorThreads = monitorThreads;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }

        public int getMaxMemory() {
            return maxMemory;
        }

        public void setMaxMemory(int maxMemory) {
            this.maxMemory = maxMemory;
        }

        public int getMaxLowResourcesTime() {
            return maxLowResourcesTime;
        }

        public void setMaxLowResourcesTime(int maxLowResourcesTime) {
            this.maxLowResourcesTime = maxLowResourcesTime;
        }

        @Override
        public String toString() {
            return "JettyLowResourceMonitorSettings{" +
                    "period=" + period +
                    ", idleTimeout=" + idleTimeout +
                    ", monitorThreads=" + monitorThreads +
                    ", maxConnections=" + maxConnections +
                    ", maxMemory=" + maxMemory +
                    ", maxLowResourcesTime=" + maxLowResourcesTime +
                    '}';
        }
    }

    public static class JettyThreadPoolSettings {
        private int minThreads = 8;
        private int maxThreads = 200;
        private int idleTimeout = 30000;
        private boolean detailedDump;

        public int getMinThreads() {
            return minThreads;
        }

        public void setMinThreads(int minThreads) {
            this.minThreads = minThreads;
        }

        public int getMaxThreads() {
            return maxThreads;
        }

        public void setMaxThreads(int maxThreads) {
            this.maxThreads = maxThreads;
        }

        public int getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(int idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public boolean isDetailedDump() {
            return detailedDump;
        }

        public void setDetailedDump(boolean detailedDump) {
            this.detailedDump = detailedDump;
        }

        @Override
        public String toString() {
            return "JettyThreadPoolSettings{" +
                    "minThreads=" + minThreads +
                    ", maxThreads=" + maxThreads +
                    ", idleTimeout=" + idleTimeout +
                    ", detailedDump=" + detailedDump +
                    '}';
        }
    }
    /**
     ＊ 拒绝服务过滤器
     *  < P>
     *  这个过滤器对于限制是有用的
     *  暴露于请求洪泛的滥用，无论是恶意的，还是由于
     *  配置错误的客户端。
     * < P>
     * 筛选器跟踪来自连接的请求数量。
     * 其次。如果超过限制，则请求被拒绝、延迟或
     * 节流。
     * < P>
     * 当请求被节流时，它被放置在优先级队列中。优先权是
     * 首先给经过身份验证的用户和具有HTTP会话的用户，然后
     * 可以通过IP地址识别的连接。联系
     * 没有识别它们的方法被给予最低优先级。
     * < P>
     ＊{Link Lang-ExtUsUrSerID（ServestRevestRead Read）}函数应该是
     * 实现，以便唯一地识别已验证的用户。
     * < P>
     * 下面的init参数控制过滤器的行为：
     ＊<dl>
     * <dt> * Max RealStestScSc1</dt>
     * <dd>每个连接的最大请求数
     * 其次。超过此要求的请求被延迟，
     * 然后节流。< /dd>
     ＊<dt> DelayMs </dt>
     * <dd>是在速率限制下对所有请求的延迟，
     * 在它们被考虑之前。1意味着拒绝请求，
     *  0意味着没有延迟，否则就是延迟。</dd>
     * <dt> Max Waistm </dt >
     * <dd>阻塞等待节气门信号的时间有多长。< /dd>
     ＊<dt>节流请求< /dt >
     * <<dd>是速率限制上的请求数量
     * 立即考虑。< /<dd>
     ＊dt>节流器< /dt >
     * <dd>异步等待信号量多长时间？< /dd>
     * <dt> * Max RealestMs</dt>
     * <dd>允许这个请求运行多长时间。</<dd>
     * <dt> MaxIDeLeTrackMS</dt>
     * <dd>跟踪连接请求速率的时间，
     * 在决定用户已经离开，并丢弃它</dd>
     * <dt>插入者< /dt >
     * <dd>如果为true，则将DOSFutter头插入到响应中。默认为true。< /dd>
     * dt>轨迹会话< /dt>
     * <dd>如果为真，会话存在时使用会话跟踪使用率。默认为true。< /dD>
     *  dt>远程端口< /dt>
     * <dd>如果TRUE和会话跟踪未被使用，则通过IP +端口（有效连接）跟踪速率。默认为false .< /dd>
     * <dt> IPWELLIST < /dt >
     * <dd>一个逗号分隔的IP地址列表，将不受速率限制</dd>
     * <dt> 管理ADT< /dt >
     * <dd>如果设置为true，则将该servlet设置为{@链接servlet上下文}属性
     * 筛选器名称作为属性名称。这允许上下文外部机制（如JMX通过{@链接上下文HythHyther-SyMaReDeMy属性}）
     * 管理筛选器的配置。< /<dd>
     ＊<dt> ToMoNoCudio</dt >
     * <dd>如果有太多请求，将发送状态代码。默认为429（请求太多），但503（不可用）是
     * 另一个选项< /dD>
     * </dL>
     * < P>
     * 此过滤器应该配置为{@ Link Debug类型}请求}和{@ Link DeXCurtType<代码> &；异步支持& gt；真和lt；/asyc支持& gt；< />代码>。
 */

    public static class HttpDosFilterSettings{
        private String urlPatterns = "/top/suven/base/*,/server/*";
        private String exclusions = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";
        private  long  delayMs = -1;
        private  long  maxWaitMs = 50;
        private  long  maxRequestMs = 50000;
        private  long  maxIdleTrackerMs = 30000L;
        private  boolean  insertHeaders = false;
        private  boolean  trackSessions = false;
        private  boolean  remotePort = true;
        private  int throttledRequests = 5;
        private  int maxRequestsPerSec = 20;


        public Map<String, String> toMap() {
            Field[] fields = FieldUtils.getAllFields(this.getClass());;
            Map<String, String> map = new HashMap<>();
            if(null == fields){
                return map;
            }
            map.remove("urlPatterns");
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    map.put(field.getName(), String.valueOf(field.get(this)));
                } catch (Exception e) {
                }
            }
            return  map;
        }

        public String getUrlPatterns() {
            return urlPatterns;
        }

        public void setUrlPatterns(String urlPatterns) {
            this.urlPatterns = urlPatterns;
        }

        public String getExclusions() {
            return exclusions;
        }

        public void setExclusions(String exclusions) {
            this.exclusions = exclusions;
        }

        public long getDelayMs() {
            return delayMs;
        }

        public void setDelayMs(long delayMs) {
            this.delayMs = delayMs;
        }



        public long getMaxWaitMs() {
            return maxWaitMs;
        }

        public void setMaxWaitMs(long maxWaitMs) {
            this.maxWaitMs = maxWaitMs;
        }

        public long getMaxRequestMs() {
            return maxRequestMs;
        }

        public void setMaxRequestMs(long maxRequestMs) {
            this.maxRequestMs = maxRequestMs;
        }

        public long getMaxIdleTrackerMs() {
            return maxIdleTrackerMs;
        }

        public void setMaxIdleTrackerMs(long maxIdleTrackerMs) {
            this.maxIdleTrackerMs = maxIdleTrackerMs;
        }

        public boolean isInsertHeaders() {
            return insertHeaders;
        }

        public void setInsertHeaders(boolean insertHeaders) {
            this.insertHeaders = insertHeaders;
        }

        public boolean isTrackSessions() {
            return trackSessions;
        }

        public void setTrackSessions(boolean trackSessions) {
            this.trackSessions = trackSessions;
        }

        public boolean isRemotePort() {
            return remotePort;
        }

        public void setRemotePort(boolean remotePort) {
            this.remotePort = remotePort;
        }

        public int getThrottledRequests() {
            return throttledRequests;
        }

        public void setThrottledRequests(int throttledRequests) {
            this.throttledRequests = throttledRequests;
        }

        public int getMaxRequestsPerSec() {
            return maxRequestsPerSec;
        }

        public void setMaxRequestsPerSec(int maxRequestsPerSec) {
            this.maxRequestsPerSec = maxRequestsPerSec;
        }
    }

    public static class InterServerConfigSetting {
        private String white;
        private String exclude;
        private String path = "/**";
        private String excludePath;

        public String getExclude() {
            return exclude;
        }

        public InterServerConfigSetting setExclude(String exclude) {
            this.exclude = exclude;
            return this;
        }

        public List<String> getPaths() {
            return converterPath(path);
        }

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            if(null != path) {
                this.path = path;
            }
        }

        public String getExcludePath() {
            return excludePath;
        }

        public void setExcludePath(String excludePath) {
            if(null != excludePath){
                this.excludePath = excludePath;
            }

        }

        public String getWhite() {
            return white;
        }

        public void setWhite(String white) {
            this.white = white;
        }

        public boolean isWhite(int isRun){

            List<String> list = converterPath(this.white);
            if(list.isEmpty()){
                return false;
            }
            return list.contains(String.valueOf(isRun));
        }

        public List<String> getExcludePaths() {
            return converterPath(excludePath);
        }

        public boolean isExclude(int order){

            List<String> list = converterPath(exclude);
            if(list.isEmpty()){
                return false;
            }
            return list.contains(String.valueOf(order));
        }

        private List<String> converterPath(String path){
            if(path == null || "".equals(path)){
                return new ArrayList<>();
            }
            String[] inter = path.split(";|,");
            if(inter == null || inter.length == 0){
                return new ArrayList<>();
            }
            return new ArrayList<>(Arrays.asList(inter));
        }
    }
}

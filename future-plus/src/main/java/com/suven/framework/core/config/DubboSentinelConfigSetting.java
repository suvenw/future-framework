//package top.suven.future.core.config;
//
//import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//import static top.suven.future.common.constants.GlobalConfigConstants.*;
//
///**
// * @ClassName:DubboSentinelConfigSetting.java
// * Description:
// * @Author suven.wang
// * date 2018/9/4 14:12
// * Copyright: (c) 2018 gc by https://www.suven.top
// * @version : 1.0.0
// * --------------------------------------------------------
// * modifyer    modifyTime                 comment
// * <p>
// * --------------------------------------------------------
// */
//@Configuration("dubboSentinelConfigSetting")
//@ConditionalOnProperty(prefix = TOP_DUBBO_SENTINEL_ENABLED,  matchIfMissing = false)
//@ConfigurationProperties(TOP_DUBBO_SENTINEL)
//@NacosConfigurationProperties( groupId= SERVICE_NAME, dataId = TOP_DUBBO_SENTINEL, prefix=TOP_DUBBO_SENTINEL,  autoRefreshed = true)
//public class DubboSentinelConfigSetting {
//
//
//    private String scanPackages;
//    //是否启动流量管控
//    private boolean isOpenFlow;
//    //客户端监听端口（默认8719）
//    private int apiPort;
//    //上报地址和端口 ip:port
//    private String dashboardServer;
//    //客户端服务名称
//    private String projectName;
//    //日志地址
//    private String logPath;
//    //流量限制阀值
//    private int count;
//    //流控阀值类型0 线程 1 QPS
//    private int grade = 0;// RuleConstant.FLOW_GRADE_QPS;
//    //流控模式(默认0)  0 直接 1 关联 2 链路
//    private int strategy=-1;
//    //流控方式(默认0) 0  快速失败   1  Warm Up   2  排队等待
//    private int controlBehavior = -1;
//    //是否启动降级管控
//    private boolean isDeGrade = false;
//    //降级阀值类型(默认0)  0 RT 1 异常比列
//    private int deGrade = -1;
//
//    private  TopDubboAsyncConfig async = new TopDubboAsyncConfig();
//
//    public int getApiPort() {
//        return apiPort;
//    }
//
//    public void setApiPort(int apiPort) {
//        this.apiPort = apiPort;
//    }
//
//    public String getDashboardServer() {
//        return dashboardServer;
//    }
//
//    public void setDashboardServer(String dashboardServer) {
//        this.dashboardServer = dashboardServer;
//    }
//
//    public String getProjectName() {
//        return projectName;
//    }
//
//    public void setProjectName(String projectName) {
//        this.projectName = projectName;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public int getGrade() {
//        return grade;
//    }
//
//    public void setGrade(int grade) {
//        this.grade = grade;
//    }
//
//    public int getDeGrade() {
//        return deGrade;
//    }
//
//    public void setDeGrade(int deGrade) {
//        this.deGrade = deGrade;
//    }
//
//    public int getStrategy() {
//        return strategy;
//    }
//
//    public void setStrategy(int strategy) {
//        this.strategy = strategy;
//    }
//
//    public boolean isOpenFlow() {
//        return isOpenFlow;
//    }
//
//    public void setOpenFlow(boolean openFlow) {
//        isOpenFlow = openFlow;
//    }
//
//    public boolean isDeGrade() {
//        return isDeGrade;
//    }
//
//    public void setDeGrade(boolean deGrade) {
//        isDeGrade = deGrade;
//    }
//
//    public int getControlBehavior() {
//        return controlBehavior;
//    }
//
//    public void setControlBehavior(int controlBehavior) {
//        this.controlBehavior = controlBehavior;
//    }
//
//    public String getLogPath() {
//        return logPath;
//    }
//
//    public void setLogPath(String logPath) {
//        this.logPath = logPath;
//    }
//
//    public String getScanPackages() {
//        return scanPackages;
//    }
//
//    public void setScanPackages(String scanPackages) {
//        this.scanPackages = scanPackages;
//    }
//
//    public TopDubboAsyncConfig getAsync() {
//        return async;
//    }
//
//    public void setAsync(TopDubboAsyncConfig async) {
//        this.async = async;
//    }
//
//    public static class TopDubboAsyncConfig {
//
//        private boolean isStartAsync;
//        private int asyncCorePoolSize;
//        private int asyncMaxPoolSize;
//        private int asyncQueueCapacity;
//        private String asyncThreadNamePrefix;
//        private int asyncKeepAliveSeconds;
//        private boolean asyncAllowCoreThreadTimeOut;
//        private int poolSize;
//
//        public int getAsyncCorePoolSize() {
//            return asyncCorePoolSize;
//        }
//
//        public void setAsyncCorePoolSize(int asyncCorePoolSize) {
//            this.asyncCorePoolSize = asyncCorePoolSize;
//        }
//
//        public int getAsyncMaxPoolSize() {
//            return asyncMaxPoolSize;
//        }
//
//        public void setAsyncMaxPoolSize(int asyncMaxPoolSize) {
//            this.asyncMaxPoolSize = asyncMaxPoolSize;
//        }
//
//        public int getAsyncQueueCapacity() {
//            return asyncQueueCapacity;
//        }
//
//        public void setAsyncQueueCapacity(int asyncQueueCapacity) {
//            this.asyncQueueCapacity = asyncQueueCapacity;
//        }
//
//        public String getAsyncThreadNamePrefix() {
//            return asyncThreadNamePrefix;
//        }
//
//        public void setAsyncThreadNamePrefix(String asyncThreadNamePrefix) {
//            this.asyncThreadNamePrefix = asyncThreadNamePrefix;
//        }
//
//        public int getAsyncKeepAliveSeconds() {
//            return asyncKeepAliveSeconds;
//        }
//
//        public void setAsyncKeepAliveSeconds(int asyncKeepAliveSeconds) {
//            this.asyncKeepAliveSeconds = asyncKeepAliveSeconds;
//        }
//
//        public boolean isAsyncAllowCoreThreadTimeOut() {
//            return asyncAllowCoreThreadTimeOut;
//        }
//
//        public void setAsyncAllowCoreThreadTimeOut(boolean asyncAllowCoreThreadTimeOut) {
//            this.asyncAllowCoreThreadTimeOut = asyncAllowCoreThreadTimeOut;
//        }
//
//        public int getPoolSize() {
//            return poolSize;
//        }
//
//        public void setPoolSize(int poolSize) {
//            this.poolSize = poolSize;
//        }
//
//        public boolean isStartAsync() {
//            return isStartAsync;
//        }
//
//        public void setStartAsync(boolean startAsync) {
//            isStartAsync = startAsync;
//        }
//    }
//}

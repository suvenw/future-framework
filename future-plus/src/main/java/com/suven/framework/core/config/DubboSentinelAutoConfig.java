//package top.suven.future.core.config;
//
//
//import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
//import com.alibaba.csp.sentinel.init.InitExecutor;
//import com.alibaba.csp.sentinel.slots.block.RuleConstant;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
//import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
//import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
//import com.alibaba.csp.sentinel.transport.config.TransportConfig;
//import com.alibaba.csp.sentinel.util.AppNameUtil;
//import org.apache.dubbo.config.annotation.Service;
//import org.reflections.Reflections;
//import org.reflections.scanners.TypeAnnotationsScanner;
//import org.springframework.beans.BeansException;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import jakarta.annotation.PostConstruct;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * Title: DubboSentinelAutoConfig.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) dubbo sentinel 自动加载实现类
// */
//@Configuration
//@ConditionalOnClass({DubboSentinelConfigSetting.class})
//public class DubboSentinelAutoConfig  implements ApplicationContextAware {
//
//    protected ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//
//    @Bean
//    public SentinelResourceAspect sentinelResourceAspect() {
//        return new SentinelResourceAspect();
//    }
//    /**
//     * Title: DUBBO-SENTINEL启动
//     * Description: dubbo流量哨兵启动
//     * @param
//     * @return
//     * @throw
//     * @author lixiangling
//     * date 2018/9/4 15:07
//     *  --------------------------------------------------------
//     *  modifyer    modifyTime                 comment
//     *
//     *  --------------------------------------------------------
//     */
//    @PostConstruct
//    @ConditionalOnBean({DubboSentinelConfigSetting.class})
//    public void init(){
//        DubboSentinelConfigSetting sentinelConfig = applicationContext.getBean(DubboSentinelConfigSetting.class);
//        if(null == sentinelConfig){
//            return;
//        }
//        Reflections reflections = new Reflections(sentinelConfig.getScanPackages(), new TypeAnnotationsScanner());
//        Set<Class<?>> classes =  reflections.getTypesAnnotatedWith(Service.class);
//        if(classes == null || classes.isEmpty()){
//            return;
//        }
//        List<FlowRule> flowRuleList = new ArrayList<>();
//        List<DegradeRule> degradeRuleList = new ArrayList<>();
//        for(Class<?> refClass : classes){
//            Class[] parentClasses = refClass.getInterfaces();
//            if(parentClasses == null || parentClasses.length == 0){
//                continue;
//            }
//            String className = parentClasses[0].getName();
//            Method[] methods = parentClasses[0].getDeclaredMethods();
//            if(methods == null || methods.length == 0){
//                continue;
//            }
//            setDubboSentinel(className,methods,sentinelConfig,flowRuleList,degradeRuleList);
//        }
//        if(sentinelConfig.isOpenFlow()||sentinelConfig.isDeGrade()){
//            System.setProperty("user.home", sentinelConfig.getLogPath());
//            System.setProperty(TransportConfig.CONSOLE_SERVER,sentinelConfig.getDashboardServer());
//            System.setProperty(AppNameUtil.APP_NAME,sentinelConfig.getProjectName());
//            if(sentinelConfig.getApiPort() > -1) {
//                System.setProperty(TransportConfig.SERVER_PORT, String.valueOf(sentinelConfig.getApiPort()));
//            }
//            InitExecutor.doInit();
//        }
//
//        if(sentinelConfig.isOpenFlow()&&!flowRuleList.isEmpty()){
//            FlowRuleManager.loadRules(flowRuleList);
//        }
//        if(sentinelConfig.isDeGrade()&&!flowRuleList.isEmpty()){
//            DegradeRuleManager.loadRules(degradeRuleList);
//        }
//        // remoteAddress 代表 Nacos 服务端的地址
//        // groupId 和 dataId 对应 Nacos 中相应配置
////        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<List<FlowRule>>(remoteAddress, groupId, dataId,
////                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
////        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
//
//    }
//
//    private void setDubboSentinel(String className, Method[] methods,DubboSentinelConfigSetting sentinelConfig,List<FlowRule> flowRuleList,List<DegradeRule> degradeRuleList){
//        for (Method method : methods){
//            String methodName = method.toString();
//            methodName = methodName.substring(methodName.indexOf(className)+className.length()+1,methodName.lastIndexOf(")")+1);
//            FlowRule flowRule = new FlowRule();
//            flowRule.setResource(String.format("%s:%s",className,methodName));
//            flowRule.setCount(sentinelConfig.getCount());
//            flowRule.setGrade(sentinelConfig.getGrade());
//            if(sentinelConfig.getStrategy() > -1) {
//                flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
//            }
//            if(sentinelConfig.getControlBehavior() > -1) {
//                flowRule.setControlBehavior(sentinelConfig.getControlBehavior());
//            }
//            flowRuleList.add(flowRule);
//
//            DegradeRule degradeRule = new DegradeRule();
//            degradeRule.setGrade(sentinelConfig.getDeGrade());
//            degradeRule.setCount(sentinelConfig.getCount());
//            degradeRule.setResource(flowRule.getResource());
//            degradeRuleList.add(degradeRule);
//        }
//    }
//
//
//}
package com.suven.framework.core.jetty;


import com.suven.framework.core.jetty.settings.JettyServerSettings;
import com.suven.framework.core.spring.SpringBootAutoConfigSetting;
import com.suven.framework.http.inters.IProjectModule;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Title: AbstractRpcServerApplication.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) RPC SERVER 启动服务实现类，加载提升到30秒启动完成
 */

@EnableTransactionManagement
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
//        ,DelegatingWebMvcConfiguration.class
       })
@Configuration
//@ComponentScan(basePackages={GlobalConstants.COMPONENT_SCAN_BASE_PACKAGES})
public  abstract  class AbstractRpcServerApplication extends JettyWebServerFactory implements CommandLineRunner,ApplicationContextAware {

    private static  final Logger logger = LoggerFactory.getLogger(AbstractRpcServerApplication.class);

    @SuppressWarnings("unused")
    private ApplicationContext applicationContext;
    private static String host = null;

    @Autowired
    @SuppressWarnings("all")
    private SpringBootAutoConfigSetting configSetting;

    @Autowired @SuppressWarnings("all")
    protected JettyServerSettings jettyServerSettings;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected  abstract IProjectModule getServiceName();

    public void run(String... args){
        try {
            host = InetAddress.getLocalHost().getHostAddress();
            getServicePort();
        } catch (UnknownHostException e) {
            logger.error("get server host Exception e:", e);
        }
        logger.warn("Started RPC SERVER INTO RUN Successfully ...");
    }





    public static void buildRpc(Class<?> clazz, String ... args){
        jettyStart(clazz,args);
    }

    public static void jettyStart(Class<?> clazz, String ... args){
        long startTime = System.currentTimeMillis();
        logger.warn("jetty Start class[{}} \n {}",  clazz, ServiceLogoIcon.serviceStartedLogo);
//        new SpringApplicationBuilder(clazz).sources(clazz).web(WebApplicationType.NONE).run(args);
        new SpringApplicationBuilder().sources(clazz).web(WebApplicationType.NONE).run(args);

        long endTime = System.currentTimeMillis() - startTime;
        logger.warn(ServiceLogoIcon.serviceSuccessLogo);
        logger.warn("{} Jetty Started JVM  ServerConnector RPC to Host({}) Successfully ... having Time[{}]Seconds" ,
                clazz.getName(),host,endTime);
    }

    /**
     * 端口解决实现
     * @return
     */
    @Override
    public int getServicePort(){
        return 0;
    }

    /**
     * jetty 服务实现,Metric监控,threadPool,LowResourceMonitor
     * @return JettyEmbeddedServletContainerFactory
     */
    public JettyServerSettings jettyServer(){
        return jettyServerSettings;
    }

    /**
     * 初始化信息
     */
    @SuppressWarnings("unused")
    private void init() {

    }


    /**
     * Title: DUBBO-SENTINEL启动
     * Description: dubbo流量哨兵启动
     * @param
     * @return
     * @throw
     * @author lixiangling
     * date 2018/9/4 15:07
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
//    @PostConstruct
//    public void init(){
//        DubboSentinelConfig dubboSentinelConfig = applicationContext.getBean(DubboSentinelConfig.class);
//        Reflections reflections = new Reflections(GlobalConstants.DEPICT_PACKAGE, new TypeAnnotationsScanner());
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
//            setDubboSentinel(methods,className,dubboSentinelConfig,flowRuleList,degradeRuleList);
//        }
//        if(dubboSentinelConfig.isOpenFlow()||dubboSentinelConfig.isDeGrade()){
//            System.setProperty("user.home", dubboSentinelConfig.getLogPath());
//            System.setProperty(TransportConfig.CONSOLE_SERVER,dubboSentinelConfig.getDashboardServer());
//            System.setProperty(AppNameUtil.APP_NAME,dubboSentinelConfig.getProjectName());
//            if(dubboSentinelConfig.getApiPort()>-1) {
//                System.setProperty(TransportConfig.SERVER_PORT, String.valueOf(dubboSentinelConfig.getApiPort()));
//            }
//            InitExecutor.doInit();
//        }
//
//        if(dubboSentinelConfig.isOpenFlow()&&!flowRuleList.isEmpty()){
//            FlowRuleManager.loadRules(flowRuleList);
//        }
//        if(dubboSentinelConfig.isDeGrade()&&!flowRuleList.isEmpty()){
//            DegradeRuleManager.loadRules(degradeRuleList);
//        }
//
//    }
//
//    private void setDubboSentinel(Method[] methods,String className, DubboSentinelConfig dubboSentinelConfig,List<FlowRule> flowRuleList,List<DegradeRule> degradeRuleList){
//        for (Method method : methods){
//            String methodName = method.toString();
//            methodName = methodName.substring(methodName.indexOf(className)+className.length()+1,methodName.lastIndexOf(")")+1);
//            FlowRule flowRule = new FlowRule();
//            flowRule.setResource(String.format("%s:%s",className,methodName));
//            flowRule.setCount(dubboSentinelConfig.getCount());
//            flowRule.setGrade(dubboSentinelConfig.getGrade());
//            if(dubboSentinelConfig.getStrategy() > -1) {
//                flowRule.setStrategy(RuleConstant.STRATEGY_CHAIN);
//            }
//            if(dubboSentinelConfig.getControlBehavior() > -1) {
//                flowRule.setControlBehavior(dubboSentinelConfig.getControlBehavior());
//            }
//            flowRuleList.add(flowRule);
//
//            DegradeRule degradeRule = new DegradeRule();
//            degradeRule.setGrade(dubboSentinelConfig.getDeGrade());
//            degradeRule.setCount(dubboSentinelConfig.getCount());
//            degradeRule.setResource(flowRule.getResource());
//            degradeRuleList.add(degradeRule);
//        }
//    }


}

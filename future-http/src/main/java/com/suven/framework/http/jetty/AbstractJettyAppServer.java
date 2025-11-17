package com.suven.framework.http.jetty;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.data.entity.GlobalServiceInfo;
import com.suven.framework.http.inters.IProjectModule;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import com.suven.framework.core.jetty.JettyWebServerFactory;
import com.suven.framework.core.jetty.settings.JettyServerSettings;
import com.suven.framework.core.jetty.settings.SystemParamSettings;
import com.suven.framework.core.spring.SpringBootAutoConfigSetting;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import com.suven.framework.core.jetty.ServiceLogoIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


import java.net.InetAddress;



/**
 * Title: AbstractJettyAppServer.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口服务抽象模板实现类,继承了JettyWebServerFactory,实现了自己定义的端口JettyServicePort接口,
 * 从而 达到启动业端口多样化,可配置性更高更方便;
 */



@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class})
@ComponentScan(basePackages={GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES})
public  abstract  class AbstractJettyAppServer extends JettyWebServerFactory implements WebServerFactoryCustomizer  {

    protected static Logger logger = LoggerFactory.getLogger(AbstractJettyAppServer.class);

    @Autowired
    protected JettyServerSettings jettyServerSettings;

    @Autowired
    protected SystemParamSettings systemParamSettings;


    @Autowired
    private SpringBootAutoConfigSetting configSetting;


    private   static int port = 0;

    protected  abstract IProjectModule getServiceName();



    /**
     * 应用服务启动实现方法
     * @param clazz
     * @param args
     */
    protected static void jettyStart(Class<?> clazz,String[] args){
        logger.warn("jetty Start class[{}] \n {}",  clazz, ServiceLogoIcon.serviceStartedLogo );
        long startTime = System.currentTimeMillis();
        getPortFromArgs(args);
        ApplicationContext application = SpringApplication.run(clazz, args);
        printServiceLog(application,clazz,startTime);


    }

    private static void printServiceLog(ApplicationContext application,Class<?> clazz, long startTime){
        try {

            long endTime = System.currentTimeMillis() - startTime;
            Environment env = application.getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
//            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            GlobalServiceInfo.getInstance().init(ip,port,path);
            logger.warn(ServiceLogoIcon.serviceSuccessLogo
                    +
                    "\n----------------------------------------------------------\n\t" +
                    "Application jetty Service Started is running! Access URLs:\n\t" +
                    "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                    "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                    "doc-ui: \thttp://" + ip + ":" + port + path + "/docs.html\n\t" +
                    "Doc: \t\thttp://" + ip + ":" + port + path + "/docs.html\n" +
                    "----------------------------------------------------------");

            logger.warn("[{}] Jetty Started on JVM ServerConnector  Successfully ... having Time [{}] Seconds" ,clazz.getName(),endTime);

        }catch (Exception e){}
    }




    @Override
    public void customize(WebServerFactory factory) {
        port = getServicePort();
    }

    /**
     * 启动命令输入端口解释
     * @param args
     * @return
     */
    private static int getPortFromArgs(String[] args) {
        if (null != args && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignore) {  }
        }
        return port;
    }




    /**
     * 端口启动实现原理;
     * 默认1. 先按启动脚本的端口号,2.当端口号小于0,再按配置文件,3.当端口号还是小0, 按代码编写分配的端口号;
     * @return
     */

    @Override
    public int getServicePort(){
        try {
            IProjectModule moduleEnum = getServiceName();
            if(null == moduleEnum){
                throw new RuntimeException(" ServerApplication main getServiceName return moduleEnum is null ");
            }
            systemParamSettings.setServerName(moduleEnum.getModule());
            GlobalServiceInfo.getInstance().setServiceName(moduleEnum.getModule());
            if(port > 0 ){//如果脚本输入的端口大于0,以启动脚本为准;
                return port;
            }//否则以配置文件的辅;
            String prefix = GlobalConfigConstants.JETTY_SERVER_PORT_SETTINGS + moduleEnum.getConfigModulePort();
            port = configSetting.property(prefix,moduleEnum.getPort());
            if(port > 0){
                return port;
            }else {//最后以代码分配填写的
                port = moduleEnum.getPort();
            }
        } catch (Exception e) {
            logger.error("get server host Exception e:", e);
        }
        return port;
    }

    /**
     * jetty 服务实现,Metric监控,threadPool,LowResourceMonitor
     * @return JettyEmbeddedServletContainerFactory
     */
    @Override
    public JettyServerSettings jettyServer(){
        return jettyServerSettings;
    }

}

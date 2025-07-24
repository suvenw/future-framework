package com.suven.framework.core.jetty;

import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import com.suven.framework.core.jetty.settings.HttpMetricHelper;
import com.suven.framework.core.jetty.settings.JettyServerSettings;
import com.suven.framework.http.inters.JettyServicePort;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;


/**
 * @Title: JettyWebServerFactory.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) Jeety web SERVER 启动服务Bean管理对象
 */

public abstract class  JettyWebServerFactory implements JettyServicePort {



    private static Logger logger = LoggerFactory.getLogger(JettyWebServerFactory.class);


    public abstract JettyServerSettings jettyServer();



    @Bean
    public JettyServletWebServerFactory jettyServerFactory() {
       int port =  getServicePort();
        final JettyServletWebServerFactory factory ;
        factory = port > 0 ? new JettyServletWebServerFactory(port) : new JettyServletWebServerFactory();
        factory.addServerCustomizers
                ( customizers ->   {
                    final QueuedThreadPool threadPool = new InstrumentedQueuedThreadPool(HttpMetricHelper.getMetric());
                    JettyServerSettings.JettyThreadPoolSettings jettyThreadPoolSettings  = jettyServer().getThreadPool();
                    JettyServerSettings.JettyLowResourceMonitorSettings jettyResourceSettings  = jettyServer().getLowResources();

                    logger.warn("JettyServerSettings.JettyThreadPoolSettings =:[{}]", jettyThreadPoolSettings.toString());
                    logger.warn("JettyServerSettings.JettyLowResourceMonitorSettings =:[{}]", jettyResourceSettings.toString());

                    threadPool.setMaxThreads(jettyThreadPoolSettings.getMaxThreads());
                    threadPool.setMinThreads(jettyThreadPoolSettings.getMinThreads());
                    threadPool.setIdleTimeout(jettyThreadPoolSettings.getIdleTimeout());
                    threadPool.setDetailedDump(jettyThreadPoolSettings.isDetailedDump());
                    Server server = new Server(threadPool);


                    LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
                    lowResourcesMonitor.setPeriod(jettyResourceSettings.getPeriod());
                    lowResourcesMonitor.setLowResourcesIdleTimeout(jettyResourceSettings.getIdleTimeout());
                    lowResourcesMonitor.setMonitorThreads(jettyResourceSettings.isMonitorThreads());
//            lowResourcesMonitor.setMaxConnections(jettyResourceSettings.getMaxConnections());
                    lowResourcesMonitor.setMaxMemory(jettyResourceSettings.getMaxMemory());
                    lowResourcesMonitor.setMaxLowResourcesTime(jettyResourceSettings.getMaxLowResourcesTime());
                    server.addBean(lowResourcesMonitor);
                });
//        factory.setDocumentRoot(new File(jettyServer.getDocumentRoot()));

        return factory;
    }
}

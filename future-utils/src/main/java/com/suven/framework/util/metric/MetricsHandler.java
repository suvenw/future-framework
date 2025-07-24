package com.suven.framework.util.metric;

import com.suven.framework.util.constants.Env;
import com.suven.framework.util.ips.ServerUtils;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;



/**
 * @author Alex
 */
public class MetricsHandler implements InitializingBean, MethodInterceptor {
	
	public Logger logger = LoggerFactory.getLogger(getClass());

    private String picServer;

    private long METRICS_PERIOD_TIME = 5*60;//秒

    private int picPort;

    private String project;

    private String subProject;

    public void setPicServer(String picServer) {
        this.picServer = picServer;
    }

    public void setPicPort(int picPort) {
        this.picPort = picPort;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setSubProject(String subProject) {
        this.subProject = subProject;
    }

    private final MetricRegistry metrics = MetricHelper.getMetric();
    private String ip = Env.isDev() ? ServerUtils.getHostAddress("192.168") : ServerUtils.getHostAddress("10.1");

    @Override
    public void afterPropertiesSet() {
    	logger.warn("AfterPropertiesSet picServer:[{}],picPort:[{}],project:[{}],subProject:[{}],ip:[{}]", picServer, picPort, project, subProject, ip);
    	
        Graphite graphite = new Graphite(new InetSocketAddress(picServer, picPort));
        GraphiteReporter reporter = GraphiteReporter.forRegistry(metrics)
                .prefixedWith(project)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        
        if (!Env.isDev()) {
        	reporter.start(METRICS_PERIOD_TIME, TimeUnit.SECONDS);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
//    	logger.warn("controller method invoke, name:{}", name(subProject, ip.replace('.', '_'), "interfaces", invocation.getMethod().getName()));
    	
        Timer timer = metrics.timer(MetricRegistry.name(project, ip.replace('.', '_'), "interfaces", invocation.getMethod().getName()));
        try (Timer.Context ignored = timer.time()) {
            return invocation.proceed();
        }
    }
}
package com.suven.framework.core.jetty.settings;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.*;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;


/**
 * Title: HttpMetricHelper.java
 * @author suven
 * date   2019-10-18 12:35:25
 * @version V1.0
 * Description: (说明)    注意：服务监控指标实现类,包括jvm内存,GC,线程,buffers等metrics信息采摘
 * Copyright: (c) 2018 gc by https://www.suven.top
 */
public abstract class HttpMetricHelper {
    private static MetricRegistry metricRegistry = new MetricRegistry();
    private static HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    static {
        metricRegistry.register("jvm.gc", new GarbageCollectorMetricSet());
        metricRegistry.register("jvm.memory", new MemoryUsageGaugeSet());
        metricRegistry.register("jvm.thread-states", new ThreadStatesGaugeSet());
        metricRegistry.register("jvm.fd.usage", new FileDescriptorRatioGauge());
        metricRegistry.register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()));
        healthCheckRegistry.register("deadlocks", new ThreadDeadlockHealthCheck());


        //一个小时报一次出来，总比没有好
        Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger("metrics")).withLoggingLevel(Slf4jReporter.LoggingLevel.WARN).build();
        reporter.start(10, TimeUnit.MINUTES);
    }

    public static MetricRegistry getMetric() {
        return metricRegistry;
    }

    public static HealthCheckRegistry getHealthCheck() {
        return healthCheckRegistry;
    }

    public static Meter meter(Class<?> klass, String... names) {
        return metricRegistry.meter(MetricRegistry.name(klass, names));
    }

    public static Timer timer(Class<?> klass, String... names) {
        return metricRegistry.timer(MetricRegistry.name(klass, names));
    }
}

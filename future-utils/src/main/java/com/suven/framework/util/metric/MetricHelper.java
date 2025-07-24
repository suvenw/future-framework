package com.suven.framework.util.metric;

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

public abstract class MetricHelper {
    private static MetricRegistry metricRegistry = new MetricRegistry();
    private static HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
    private static long METRICS_PERIOD_TIME = 10*60;//秒

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
        reporter.start(METRICS_PERIOD_TIME, TimeUnit.SECONDS);
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

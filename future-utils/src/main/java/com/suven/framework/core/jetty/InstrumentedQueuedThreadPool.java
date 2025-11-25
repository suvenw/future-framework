package com.suven.framework.core.jetty;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * Compatibility replacement for Dropwizard's Jetty9 instrumentation so that legacy
 * code relying on {@code com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool}
 * keeps working after upgrading to Jetty 12.
 */
public class InstrumentedQueuedThreadPool extends QueuedThreadPool {

    private final MetricRegistry metricRegistry;
    private final String metricPrefix;

    public InstrumentedQueuedThreadPool(MetricRegistry metricRegistry) {
        this(metricRegistry, "jetty");
    }

    public InstrumentedQueuedThreadPool(MetricRegistry metricRegistry, String metricPrefix) {
        super();
        this.metricRegistry = metricRegistry;
        this.metricPrefix = metricPrefix == null ? "jetty" : metricPrefix;
        registerGauges();
    }

    public InstrumentedQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout,
                                        MetricRegistry metricRegistry, String metricPrefix) {
        super(maxThreads, minThreads, idleTimeout);
        this.metricRegistry = metricRegistry;
        this.metricPrefix = metricPrefix == null ? "jetty" : metricPrefix;
        registerGauges();
    }

    private void registerGauges() {
        registerGauge("threads", this::getThreads);
        registerGauge("idleThreads", this::getIdleThreads);
        registerGauge("busyThreads", this::getBusyThreads);
        registerGauge("queueSize", this::getQueueSize);
        registerGauge("utilization", () -> {
            int threads = getThreads();
            return threads == 0 ? 0d : ((double) getBusyThreads()) / threads;
        });
        registerGauge("isLowOnThreads", () -> isLowOnThreads() ? 1 : 0);
    }

    private <T> void registerGauge(String suffix, Gauge<T> gauge) {
        final String metricName = MetricRegistry.name(metricPrefix, "thread-pool", suffix);
        metricRegistry.remove(metricName);
        metricRegistry.register(metricName, gauge);
    }
}


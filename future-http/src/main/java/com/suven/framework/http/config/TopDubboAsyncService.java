package com.suven.framework.http.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextStoppedEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//@Service
public class TopDubboAsyncService implements ApplicationListener<ApplicationContextEvent> {

    final ScheduledExecutorService scheduledExecutor;

    public TopDubboAsyncService(ScheduledExecutorService scheduledExecutor) {
        this.scheduledExecutor = scheduledExecutor;
    }

    public <T> CompletableFuture<T> timeoutAdapter(CompletableFuture<T> future, int timeoutMs) {
        CompletableFuture<T> adapter = new CompletableFuture<>();
        future.whenComplete((result, cause) -> {
            if (cause != null) {
                adapter.completeExceptionally(cause);
            }
            else {
                adapter.complete(result);
            }
        });
        scheduledExecutor.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new TimeoutException("Timeout after " + timeoutMs));
            }
        }, timeoutMs, TimeUnit.MILLISECONDS);

        return adapter;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if(event.getApplicationContext().getParent() != null)
            return;

        else if(event instanceof ContextStoppedEvent) {
            if(scheduledExecutor == null)
                return;

            try {

                scheduledExecutor.shutdown();
                if(!scheduledExecutor.awaitTermination(2000, TimeUnit.MILLISECONDS)) {
                    scheduledExecutor.shutdownNow();
                }

            } catch (InterruptedException e) {
                scheduledExecutor.shutdownNow();
            }
        }
    }
}

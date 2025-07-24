package com.suven.framework.http.config;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName:
 * Description:
 * @Author lixiangling
 * @Date 2018/7/26 21:37
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * --------------------------------------------------------
 */
@ConditionalOnProperty(name = GlobalConfigConstants.TOP_DUBBO_ASYNC_ENABLED,  matchIfMissing = true)
@ConfigurationProperties(GlobalConfigConstants.TOP_DUBBO_ASYNC)
public class TopDubboAsyncConfig {

    private boolean isStartAsync;
    private int asyncCorePoolSize;
    private int asyncMaxPoolSize;
    private int asyncQueueCapacity;
    private String asyncThreadNamePrefix;
    private int asyncKeepAliveSeconds;
    private boolean asyncAllowCoreThreadTimeOut;
    private int poolSize;

    public int getAsyncCorePoolSize() {
        return asyncCorePoolSize;
    }

    public void setAsyncCorePoolSize(int asyncCorePoolSize) {
        this.asyncCorePoolSize = asyncCorePoolSize;
    }

    public int getAsyncMaxPoolSize() {
        return asyncMaxPoolSize;
    }

    public void setAsyncMaxPoolSize(int asyncMaxPoolSize) {
        this.asyncMaxPoolSize = asyncMaxPoolSize;
    }

    public int getAsyncQueueCapacity() {
        return asyncQueueCapacity;
    }

    public void setAsyncQueueCapacity(int asyncQueueCapacity) {
        this.asyncQueueCapacity = asyncQueueCapacity;
    }

    public String getAsyncThreadNamePrefix() {
        return asyncThreadNamePrefix;
    }

    public void setAsyncThreadNamePrefix(String asyncThreadNamePrefix) {
        this.asyncThreadNamePrefix = asyncThreadNamePrefix;
    }

    public int getAsyncKeepAliveSeconds() {
        return asyncKeepAliveSeconds;
    }

    public void setAsyncKeepAliveSeconds(int asyncKeepAliveSeconds) {
        this.asyncKeepAliveSeconds = asyncKeepAliveSeconds;
    }

    public boolean isAsyncAllowCoreThreadTimeOut() {
        return asyncAllowCoreThreadTimeOut;
    }

    public void setAsyncAllowCoreThreadTimeOut(boolean asyncAllowCoreThreadTimeOut) {
        this.asyncAllowCoreThreadTimeOut = asyncAllowCoreThreadTimeOut;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public boolean isStartAsync() {
        return isStartAsync;
    }

    public void setStartAsync(boolean startAsync) {
        isStartAsync = startAsync;
    }
}

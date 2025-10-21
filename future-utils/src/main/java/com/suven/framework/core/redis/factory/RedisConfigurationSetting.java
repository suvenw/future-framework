package com.suven.framework.core.redis.factory;


import com.suven.framework.core.redis.RedisConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Objects;



@Configuration
@ConditionalOnBean(RedisProperties.class)
@ConditionalOnProperty(name = RedisConstants.SPRING_REDIS_ENABLED, matchIfMissing = true)
@ConfigurationProperties(prefix =RedisConstants.SPRING_REDIS_CONFIG)
public class RedisConfigurationSetting  extends RedisProperties{

    private RedisClient client = new RedisClient();
    private  boolean enabled = true;


    public static class RedisClient {
        private boolean enabled = true;
        private boolean daoOpen =  true;
        private boolean read =  true;
        private boolean write =  true;
        private String model =  "standalone";//standalone,sentinel,masterSlave，cluster，group 默认为 cluster; 具体细节见 RedisConnectionEnum 类
        private String clientType = "spring"; //"spring" or "jedis"
        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isDaoOpen() {
            return daoOpen;
        }

        public void setDaoOpen(boolean daoOpen) {
            this.daoOpen = daoOpen;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public boolean isWrite() {
            return write;
        }

        public void setWrite(boolean write) {
            this.write = write;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setClientType(String clientType) {
            this.clientType = Objects.nonNull(clientType) ? clientType.trim().toLowerCase() : this.clientType;
        }
    }

    public RedisClient getClient() {
        return client;
    }

    public void setClient(RedisClient client) {
        this.client = client;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
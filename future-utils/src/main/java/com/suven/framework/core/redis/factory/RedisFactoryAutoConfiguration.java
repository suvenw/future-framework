package com.suven.framework.core.redis.factory;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *  redis 多模式统一管理实现启动配置实现类 RedisAutoConfiguration
 */

@Configuration
@EnableConfigurationProperties({RedisProperties.class, RedisConfigurationSetting.class})
@ConditionalOnBean({RedisProperties.class, RedisConfigurationSetting.class})
@ConditionalOnProperty( name = {"spring.redis.enabled"}, matchIfMissing = true)
@AutoConfigureBefore({RedisAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
@ConditionalOnClass({RedisTemplate.class, RedisConnectionFactory.class})
public class RedisFactoryAutoConfiguration {

    /**
     * 注入写强 redis 配置文件,增加开关和启动类型
     */
   private RedisConfigurationSetting redisConfig;

    public RedisFactoryAutoConfiguration(RedisConfigurationSetting redisConfig) {
        this.redisConfig = redisConfig;
    }

    /**
     * 通过模式文件,创建 不同模式下的 Redis 连接管理工厂对象实现 RedisConnectionFactory
     * **/
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        return (new RedisConnectionStrategyFactory(this.redisConfig)).createConnectionFactory();
    }





    /**
     * 通过 RedisConnectionFactory 对象对应的模式实现,实现统一的模板 RedisTemplate api实现对象
     * @param redisConnectionFactory RedisConnectionFactory  多模式下的连接管理工厂
     * @return
     */
    @ConditionalOnMissingBean(name = {"redisTemplate"})
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // Key serializers
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // Value serializers
        GenericJackson2JsonRedisSerializer genericJson = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(genericJson);
        redisTemplate.setHashValueSerializer(genericJson);
        JavaTimeModule timeModule = new JavaTimeModule();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.serialize(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}




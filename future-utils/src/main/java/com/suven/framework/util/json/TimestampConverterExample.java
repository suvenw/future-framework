package com.suven.framework.util.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 时间戳转换功能使用示例
 */
public class TimestampConverterExample {
    
    private static final Logger log = LoggerFactory.getLogger(TimestampConverterExample.class);
    
    public static void main(String[] args) {
        log.info("=== 时间戳转换功能示例 ===");
        
        // 示例1：LocalDateTime 时间戳转换
        demonstrateLocalDateTimeConversion();
        
        // 示例2：Date 时间戳转换
        demonstrateDateConversion();
        
        // 示例3：对象时间戳转换
        demonstrateObjectConversion();
        
        log.info("=== 示例完成 ===");
    }
    
    /**
     * 演示 LocalDateTime 时间戳转换
     */
    private static void demonstrateLocalDateTimeConversion() {
        log.info("\n--- LocalDateTime 时间戳转换示例 ---");
        
        LocalDateTime now = LocalDateTime.now();
        log.info("原始 LocalDateTime: {}", now);
        
        // 使用默认格式（字符串）
        String defaultJson = JacksonUtil.toJson(now);
        log.info("默认格式 JSON: {}", defaultJson);
        
        // 使用时间戳格式
        String timestampJson = JacksonUtil.toJsonWithTimestamp(now);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式转换回 LocalDateTime
        LocalDateTime converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, LocalDateTime.class);
        log.info("转换后的 LocalDateTime: {}", converted);
    }
    
    /**
     * 演示 Date 时间戳转换
     */
    private static void demonstrateDateConversion() {
        log.info("\n--- Date 时间戳转换示例 ---");
        
        Date now = new Date();
        log.info("原始 Date: {}", now);
        
        // 使用默认格式（字符串）
        String defaultJson = JacksonUtil.toJson(now);
        log.info("默认格式 JSON: {}", defaultJson);
        
        // 使用时间戳格式
        String timestampJson = JacksonUtil.toJsonWithTimestamp(now);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式转换回 Date
        Date converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, Date.class);
        log.info("转换后的 Date: {}", converted);
    }
    
    /**
     * 演示对象时间戳转换
     */
    private static void demonstrateObjectConversion() {
        log.info("\n--- 对象时间戳转换示例 ---");
        
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(new Date());
        
        log.info("原始对象: {}", user);
        
        // 使用默认格式（字符串）
        String defaultJson = JacksonUtil.toJson(user);
        log.info("默认格式 JSON: {}", defaultJson);
        
        // 使用时间戳格式
        String timestampJson = JacksonUtil.toJsonWithTimestamp(user);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式转换回对象
        User converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, User.class);
        log.info("转换后的对象: {}", converted);
    }
    
    /**
     * 用户对象示例
     */
    public static class User {
        private Long id;
        private String name;
        private LocalDateTime createTime;
        private Date updateTime;
        
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public LocalDateTime getCreateTime() {
            return createTime;
        }
        
        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }
        
        public Date getUpdateTime() {
            return updateTime;
        }
        
        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
        
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
}

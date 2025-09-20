package com.suven.framework.util.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 时间戳转换功能测试
 */
public class TimestampConverterTest {
    
    private static final Logger log = LoggerFactory.getLogger(TimestampConverterTest.class);
    
    public static void main(String[] args) {
        TimestampConverterTest test = new TimestampConverterTest();
        test.testLocalDateTimeTimestampConversion();
        test.testDateTimestampConversion();
        test.testObjectWithTimestampFields();
        log.info("所有时间戳转换测试完成！");
    }
    
    public void testLocalDateTimeTimestampConversion() {
        // 测试 LocalDateTime 时间戳转换
        LocalDateTime now = LocalDateTime.now();
        log.info("原始 LocalDateTime: {}", now);
        
        // 转换为时间戳格式的 JSON
        String timestampJson = JacksonUtil.toJsonWithTimestamp(now);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式 JSON 转换回 LocalDateTime
        LocalDateTime converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, LocalDateTime.class);
        log.info("转换后的 LocalDateTime: {}", converted);
        
        // 验证转换是否正确（允许1秒误差）
        long originalTimestamp = now.toInstant(java.time.ZoneOffset.ofHours(8)).toEpochMilli();
        long convertedTimestamp = converted.toInstant(java.time.ZoneOffset.ofHours(8)).toEpochMilli();
        assert Math.abs(originalTimestamp - convertedTimestamp) < 1000 : "时间戳转换失败";
        
        log.info("LocalDateTime 时间戳转换测试通过");
    }
    
    public void testDateTimestampConversion() {
        // 测试 Date 时间戳转换
        Date now = new Date();
        log.info("原始 Date: {}", now);
        
        // 转换为时间戳格式的 JSON
        String timestampJson = JacksonUtil.toJsonWithTimestamp(now);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式 JSON 转换回 Date
        Date converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, Date.class);
        log.info("转换后的 Date: {}", converted);
        
        // 验证转换是否正确（允许1秒误差）
        long originalTimestamp = now.getTime();
        long convertedTimestamp = converted.getTime();
        assert Math.abs(originalTimestamp - convertedTimestamp) < 1000 : "时间戳转换失败";
        
        log.info("Date 时间戳转换测试通过");
    }
    
    public void testObjectWithTimestampFields() {
        // 测试包含时间字段的对象
        TestObject testObj = new TestObject();
        testObj.setName("测试对象");
        testObj.setCreateTime(LocalDateTime.now());
        testObj.setUpdateTime(new Date());
        
        log.info("原始对象: {}", testObj);
        
        // 转换为时间戳格式的 JSON
        String timestampJson = JacksonUtil.toJsonWithTimestamp(testObj);
        log.info("时间戳格式 JSON: {}", timestampJson);
        
        // 从时间戳格式 JSON 转换回对象
        TestObject converted = JacksonUtil.parseObjectWithTimestamp(timestampJson, TestObject.class);
        log.info("转换后的对象: {}", converted);
        
        // 验证转换是否正确
        assert testObj.getName().equals(converted.getName()) : "名称转换失败";
        assert testObj.getCreateTime() != null && converted.getCreateTime() != null : "创建时间转换失败";
        assert testObj.getUpdateTime() != null && converted.getUpdateTime() != null : "更新时间转换失败";
        
        log.info("对象时间戳转换测试通过");
    }
    
    /**
     * 测试对象
     */
    public static class TestObject {
        private String name;
        private LocalDateTime createTime;
        private Date updateTime;
        
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
            return "TestObject{" +
                    "name='" + name + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
}

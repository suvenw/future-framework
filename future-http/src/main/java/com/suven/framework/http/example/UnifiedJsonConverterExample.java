package com.suven.framework.http.example;

import com.suven.framework.util.json.JacksonUtil;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 统一 JSON 转换示例控制器
 * 展示如何使用优化后的 JacksonHttpMessageConverter 实现统一的业务 JSON 转换
 */
@RestController
@RequestMapping("/api/unified-json")
public class UnifiedJsonConverterExample {
    
    /**
     * 示例1：系统级统一转换（字符串格式）
     * 使用 JacksonHttpMessageConverter 进行系统级转换
     */
    @GetMapping("/system-string-format")
    public UserData getSystemStringFormat() {
        UserData data = new UserData();
        data.setId(1L);
        data.setName("系统级字符串格式");
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(new Date());
        return data; // 系统自动使用 JacksonUtil 的配置进行转换
    }
    
    /**
     * 示例2：系统级统一转换（时间戳格式）
     * 需要在配置中切换到时间戳格式
     */
    @GetMapping("/system-timestamp-format")
    public UserData getSystemTimestampFormat() {
        UserData data = new UserData();
        data.setId(2L);
        data.setName("系统级时间戳格式");
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(new Date());
        return data; // 系统自动使用时间戳格式进行转换
    }
    
    /**
     * 示例3：手动使用 JacksonUtil 进行转换
     * 展示 JacksonUtil 和 JacksonHttpMessageConverter 的一致性
     */
    @GetMapping("/manual-conversion")
    public String getManualConversion() {
        UserData data = new UserData();
        data.setId(3L);
        data.setName("手动转换示例");
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(new Date());
        
        // 使用 JacksonUtil 进行手动转换
        String json = JacksonUtil.toJson(data);
        return "手动转换结果: " + json;
    }
    
    /**
     * 示例4：接收统一格式的请求体
     * 系统会自动将 JSON 转换为对象，时间格式由配置决定
     */
    @PostMapping("/receive-unified")
    public String receiveUnifiedData(@RequestBody UserData data) {
        // 这里 data 中的时间字段已经根据系统配置进行了转换
        return "接收成功: " + data.getName() + 
               ", 创建时间: " + data.getCreateTime() + 
               ", 更新时间: " + data.getUpdateTime();
    }
    
    /**
     * 示例5：对比不同转换方式的结果
     */
    @GetMapping("/compare-conversion")
    public ConversionResult compareConversion() {
        UserData data = new UserData();
        data.setId(4L);
        data.setName("转换对比");
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(new Date());
        
        ConversionResult result = new ConversionResult();
        result.setOriginalData(data);
        result.setSystemJson(data); // 系统级转换结果
        result.setManualJson(JacksonUtil.toJson(data)); // 手动转换结果
        
        return result;
    }
    
    /**
     * 用户数据对象
     */
    public static class UserData {
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
            return "UserData{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
    
    /**
     * 转换结果对比对象
     */
    public static class ConversionResult {
        private UserData originalData;
        private UserData systemJson;
        private String manualJson;
        
        public UserData getOriginalData() {
            return originalData;
        }
        
        public void setOriginalData(UserData originalData) {
            this.originalData = originalData;
        }
        
        public UserData getSystemJson() {
            return systemJson;
        }
        
        public void setSystemJson(UserData systemJson) {
            this.systemJson = systemJson;
        }
        
        public String getManualJson() {
            return manualJson;
        }
        
        public void setManualJson(String manualJson) {
            this.manualJson = manualJson;
        }
    }
}


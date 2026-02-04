package com.suven.framework.http.example;

import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.util.json.JacksonUtil;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 序列化测试示例控制器
 * 用于测试和验证 JacksonHttpMessageConverter 的序列化功能
 */
@RestController
@RequestMapping("/api/serialization-test")
public class SerializationTestExample {
    
    /**
     * 测试1：空 Object 序列化
     * 测试 ResponseResultVo 中的 data 字段为 new Object() 时的序列化
     */
    @GetMapping("/empty-object")
    public ResponseResultVo testEmptyObject() {
        ResponseResultVo result = ResponseResultVo.build();
        // data 字段默认为 new Object()，测试是否能正常序列化
        return result;
    }
    
    /**
     * 测试2：null 值序列化
     * 测试 data 字段为 null 时的序列化
     */
    @GetMapping("/null-data")
    public ResponseResultVo testNullData() {
        ResponseResultVo result = ResponseResultVo.build();
        result.setData(null);
        return result;
    }
    
    /**
     * 测试3：字符串数据序列化
     * 测试 data 字段为字符串时的序列化
     */
    @GetMapping("/string-data")
    public ResponseResultVo testStringData() {
        ResponseResultVo result = ResponseResultVo.build();
        result.setData("测试字符串数据");
        return result;
    }
    
    /**
     * 测试4：时间数据序列化
     * 测试包含时间字段的对象的序列化
     */
    @GetMapping("/time-data")
    public ResponseResultVo testTimeData() {
        ResponseResultVo result = ResponseResultVo.build();
        
        TimeData timeData = new TimeData();
        timeData.setId(1L);
        timeData.setName("时间数据测试");
        timeData.setCreateTime(LocalDateTime.now());
        timeData.setUpdateTime(new Date());
        
        result.setData(timeData);
        return result;
    }
    
    /**
     * 测试5：复杂对象序列化
     * 测试包含嵌套对象的序列化
     */
    @GetMapping("/complex-data")
    public ResponseResultVo testComplexData() {
        ResponseResultVo result = ResponseResultVo.build();
        
        ComplexData complexData = new ComplexData();
        complexData.setId(1L);
        complexData.setName("复杂数据测试");
        complexData.setTimeData(new TimeData());
        complexData.getTimeData().setId(2L);
        complexData.getTimeData().setName("嵌套时间数据");
        complexData.getTimeData().setCreateTime(LocalDateTime.now());
        complexData.getTimeData().setUpdateTime(new Date());
        
        result.setData(complexData);
        return result;
    }
    
    /**
     * 测试6：手动序列化对比
     * 使用 JacksonUtil 手动序列化，对比结果
     */
    @GetMapping("/manual-serialization")
    public String testManualSerialization() {
        ResponseResultVo result = ResponseResultVo.build();
        result.setData("手动序列化测试");
        
        // 使用 JacksonUtil 手动序列化
        String json = JacksonUtil.toJson(result);
        return "手动序列化结果: " + json;
    }
    
    /**
     * 测试7：接收序列化数据
     * 测试反序列化功能
     */
    @PostMapping("/receive-data")
    public String receiveData(@RequestBody ResponseResultVo data) {
        return "接收成功: " + data.getData();
    }
    
    /**
     * 时间数据对象
     */
    public static class TimeData {
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
    }
    
    /**
     * 复杂数据对象
     */
    public static class ComplexData {
        private Long id;
        private String name;
        private TimeData timeData;
        
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
        
        public TimeData getTimeData() {
            return timeData;
        }
        
        public void setTimeData(TimeData timeData) {
            this.timeData = timeData;
        }
    }
}


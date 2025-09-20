# JacksonHttpMessageConverter 序列化问题解决方案

## 问题描述

在使用 `JacksonHttpMessageConverter` 时遇到以下错误：

```
No serializer found for class java.lang.Object and no properties discovered to create BeanSerializer 
(to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) 
(through reference chain: com.suven.framework.http.data.vo.ResponseResultVo["data"])
```

## 问题原因

1. **空对象序列化问题**：`ResponseResultVo` 中的 `data` 字段被初始化为 `new Object()`，Jackson 无法序列化一个空的 `Object` 实例
2. **配置缺失**：ObjectMapper 缺少处理空对象和未知属性的配置

## 解决方案

### 1. 优化 JacksonHttpMessageConverter

在 `JacksonHttpMessageConverter` 中添加了 `configureObjectMapper` 方法，配置以下特性：

```java
private static void configureObjectMapper(ObjectMapper mapper) {
    // 禁用 FAIL_ON_EMPTY_BEANS，允许序列化空对象
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    
    // 禁用 FAIL_ON_UNKNOWN_PROPERTIES，允许未知属性
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    // 允许序列化空值
    mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
    
    // 配置日期格式
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    
    // 配置时区
    mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
}
```

### 2. 统一使用 JacksonUtil 配置

- **字符串格式**：使用 `JacksonUtil.getInstance()` 的默认配置
- **时间戳格式**：创建新的 ObjectMapper 并配置时间戳序列化器
- **统一配置**：所有 ObjectMapper 都应用相同的通用配置

### 3. 支持的时间格式

- **STRING**：字符串格式 `yyyy-MM-dd HH:mm:ss`
- **TIMESTAMP**：时间戳格式（毫秒时间戳）

## 使用方式

### 1. 默认使用（时间戳格式）

```java
@Bean
@Primary
public JacksonHttpMessageConverter defaultJacksonConverter() {
    return new JacksonHttpMessageConverter(); // 默认使用时间戳格式
}
```

### 2. 指定格式

```java
// 字符串格式
JacksonHttpMessageConverter stringConverter = new JacksonHttpMessageConverter(TimeFormat.STRING);

// 时间戳格式
JacksonHttpMessageConverter timestampConverter = new JacksonHttpMessageConverter(TimeFormat.TIMESTAMP);
```

### 3. 静态工厂方法

```java
// 创建字符串格式转换器
JacksonHttpMessageConverter stringConverter = JacksonHttpMessageConverter.createStringConverter();

// 创建时间戳格式转换器
JacksonHttpMessageConverter timestampConverter = JacksonHttpMessageConverter.createTimestampConverter();
```

## 配置示例

### application.yml

```yaml
future:
  http:
    time-format:
      enabled: true
      format: TIMESTAMP  # 或 STRING
```

## 测试验证

使用 `SerializationTestExample` 控制器测试各种序列化场景：

1. **空对象序列化**：`GET /api/serialization-test/empty-object`
2. **null 值序列化**：`GET /api/serialization-test/null-data`
3. **字符串数据序列化**：`GET /api/serialization-test/string-data`
4. **时间数据序列化**：`GET /api/serialization-test/time-data`
5. **复杂对象序列化**：`GET /api/serialization-test/complex-data`
6. **手动序列化对比**：`GET /api/serialization-test/manual-serialization`

## 优势

1. **问题解决**：彻底解决了 `Object` 类型序列化问题
2. **统一配置**：与 `JacksonUtil` 保持一致的序列化配置
3. **灵活配置**：支持字符串和时间戳两种时间格式
4. **向后兼容**：不影响现有代码的使用
5. **易于维护**：集中管理序列化配置

## 注意事项

1. 确保 `JacksonHttpMessageConverter` 被正确注册为主要的消息转换器
2. 如果需要自定义序列化器，可以在 `ConverterJacksonSerializer` 中添加
3. 时区配置为 GMT+8，可根据需要调整
4. 建议在生产环境中测试各种数据类型的序列化结果

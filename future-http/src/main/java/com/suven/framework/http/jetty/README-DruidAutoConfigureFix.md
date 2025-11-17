# DruidDataSourceAutoConfigure 排除问题修复

## 问题描述

在 Spring Boot 3.x 中启动应用时遇到以下错误：

```
java.lang.IllegalStateException: The following classes could not be excluded because they are not auto-configuration classes:
	- com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
```

## 问题原因

1. **Spring Boot 3.x 兼容性问题**：在 Spring Boot 3.x 中，`DruidDataSourceAutoConfigure` 可能不是一个自动配置类，或者类名已经改变
2. **重复排除**：项目已经有自定义的 `DruidDataSourceAutoConfig` 来处理 Druid 配置，不需要再排除 `DruidDataSourceAutoConfigure`
3. **类不存在**：在 Spring Boot 3.x 中，Druid 的自动配置类可能已经不存在或名称已改变

## 解决方案

从 `AbstractJettyAppServer` 的 `@SpringBootApplication` 注解中移除了 `DruidDataSourceAutoConfigure.class` 的排除：

### 修改前

```java
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,KafkaAutoConfiguration.class})
```

### 修改后

```java
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class})
```

同时移除了相关的 import：

```java
// 移除了这行
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
```

## 为什么可以移除

1. **自定义配置已存在**：项目中有 `DruidDataSourceAutoConfig` 类（位于 `future-db` 模块），它已经处理了 Druid 数据源的配置
2. **避免冲突**：自定义配置类已经排除了 `DruidDataSourceAutoConfigure`，不需要在主类中再次排除
3. **Spring Boot 3.x 兼容性**：在 Spring Boot 3.x 中，Druid 的自动配置可能已经改变，直接排除可能导致错误

## 相关文件

- **修改文件**：`future-http/src/main/java/com/suven/framework/http/jetty/AbstractJettyAppServer.java`
- **自定义 Druid 配置**：`future-db/src/main/java/com/suven/framework/core/db/druid/DruidDataSourceAutoConfig.java`

## 验证

修复后，应用可以正常启动，不再出现 `DruidDataSourceAutoConfigure` 相关的错误。

## 注意事项

1. 确保 `DruidDataSourceAutoConfig` 配置类正确配置了数据源
2. 如果将来需要排除 Druid 的自动配置，应该检查 Spring Boot 3.x 中正确的类名
3. 建议使用条件注解（如 `@ConditionalOnClass`）来避免类似问题

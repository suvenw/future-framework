# MyBatis-Plus 拦截器使用手册

## 目录

- [概述](#概述)
- [拦截器列表](#拦截器列表)
- [详细说明](#详细说明)
  - [1. PaginationInnerInterceptor - 分页拦截器](#1-paginationinnerinterceptor---分页拦截器)
  - [2. TenantLineInnerInterceptor - 多租户拦截器](#2-tenantlineinnerinterceptor---多租户拦截器)
  - [3. BlockAttackInnerInterceptor - 防止全表更新/删除拦截器](#3-blockattackinnerinterceptor---防止全表更新删除拦截器)
  - [4. IllegalSQLInnerInterceptor - 非法SQL拦截器](#4-illegalsqlinnerinterceptor---非法sql拦截器)
  - [5. DataPermissionInterceptor - 数据权限拦截器](#5-datapermissioninterceptor---数据权限拦截器)
  - [6. DataChangeRecorderInnerInterceptor - 数据变更记录拦截器](#6-datachangerecorderinnerinterceptor---数据变更记录拦截器)
  - [7. BaseMultiTableInnerInterceptor - 多表操作拦截器](#7-basemultitableinnerinterceptor---多表操作拦截器)
- [配置示例](#配置示例)
- [最佳实践](#最佳实践)
- [常见问题](#常见问题)

---

## 概述

MyBatis-Plus 提供了丰富的内置拦截器（Inner Interceptor），用于增强 MyBatis 的功能。这些拦截器通过 `MybatisPlusInterceptor` 统一管理，可以按需组合使用。

### 拦截器执行顺序

拦截器按照添加到 `MybatisPlusInterceptor` 的顺序执行，建议的执行顺序：

1. **多租户拦截器** (TenantLineInnerInterceptor) - 最先执行，确保所有 SQL 都包含租户条件
2. **数据权限拦截器** (DataPermissionInterceptor) - 在租户之后，添加数据权限过滤
3. **分页拦截器** (PaginationInnerInterceptor) - 在权限过滤之后，进行分页处理
4. **防止攻击拦截器** (BlockAttackInnerInterceptor) - 防止危险操作
5. **非法SQL拦截器** (IllegalSQLInnerInterceptor) - 最后检查 SQL 合法性

---

## 拦截器列表

| 拦截器类 | 功能说明 | 优先级 |
|---------|---------|--------|
| `PaginationInnerInterceptor` | 自动分页处理 | ⭐⭐⭐⭐⭐ |
| `TenantLineInnerInterceptor` | 多租户数据隔离 | ⭐⭐⭐⭐⭐ |
| `BlockAttackInnerInterceptor` | 防止全表更新/删除 | ⭐⭐⭐⭐⭐ |
| `IllegalSQLInnerInterceptor` | 检测非法SQL语句 | ⭐⭐⭐⭐ |
| `DataPermissionInterceptor` | 数据权限控制 | ⭐⭐⭐⭐ |
| `DataChangeRecorderInnerInterceptor` | 数据变更记录 | ⭐⭐⭐ |
| `BaseMultiTableInnerInterceptor` | 多表操作基类 | ⭐⭐ |

---

## 详细说明

### 1. PaginationInnerInterceptor - 分页拦截器

**功能：** 自动处理分页查询，将 `IPage` 参数转换为对应的数据库分页 SQL。

**使用场景：**
- 列表查询需要分页
- 需要统一分页逻辑
- 防止全表查询导致性能问题

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建分页拦截器
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        
        // 设置数据库类型（可选，会自动识别）
        // paginationInterceptor.setDbType(DbType.MYSQL);
        
        // 设置单页分页条数限制，默认无限制
        paginationInterceptor.setMaxLimit(1000L);
        
        // 设置溢出总页数后进行处理（默认不处理）
        paginationInterceptor.setOverflow(false);
        
        // 添加分页拦截器
        interceptor.addInnerInterceptor(paginationInterceptor);
        
        return interceptor;
    }
}
```

**自定义分页拦截器（本项目实现）：**

本项目实现了 `MybatisPageInnerInterceptor`，扩展了以下功能：

1. **默认分页限制：** 当查询没有分页参数时，自动添加默认 1000 条限制
2. **分页保护：** 防止无分页查询导致全表扫描

```java
public class MybatisPageInnerInterceptor extends PaginationInnerInterceptor {
    
    private static final long DEFAULT_PAGE_LIMIT = 1000L;
    
    public MybatisPageInnerInterceptor() {
        super();
        // 设置默认最大分页限制为 1000 条
        this.setMaxLimit(DEFAULT_PAGE_LIMIT);
    }
    
    @Override
    public void beforeQuery(...) {
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
        
        // 如果没有分页参数，创建默认分页对象，限制为 1000 条
        if (null == page) {
            page = new Page<>(1, DEFAULT_PAGE_LIMIT);
            if (parameter instanceof Map) {
                ((Map<String, Object>) parameter).put("page", page);
            }
        }
        // ... 其他逻辑
    }
}
```

**使用示例：**

```java
// 方式1：使用 IPage 参数
@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPage(IPage<User> page, @Param("name") String name);
}

// 方式2：使用 Page 对象
Page<User> page = new Page<>(1, 10); // 第1页，每页10条
IPage<User> result = userMapper.selectPage(page, "张三");

// 方式3：无分页参数时，自动应用默认限制（本项目特性）
List<User> users = userMapper.selectList(null); // 自动限制为1000条
```

---

### 2. TenantLineInnerInterceptor - 多租户拦截器

**功能：** 自动在 SQL 中添加租户条件，实现多租户数据隔离。

**使用场景：**
- SaaS 多租户系统
- 需要按租户隔离数据
- 避免手动在每个查询中添加租户条件

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建多租户拦截器
        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
        
        // 设置租户处理器
        tenantInterceptor.setTenantLineHandler(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // 从当前上下文获取租户ID
                String tenantId = TenantContext.getCurrentTenantId();
                return new StringValue(tenantId);
            }
            
            @Override
            public String getTenantIdColumn() {
                // 返回租户字段名，默认为 tenant_id
                return "tenant_id";
            }
            
            @Override
            public boolean ignoreTable(String tableName) {
                // 返回 true 表示忽略该表，不添加租户条件
                return "sys_config".equals(tableName);
            }
        });
        
        // 添加多租户拦截器（建议放在第一位）
        interceptor.addInnerInterceptor(tenantInterceptor);
        
        return interceptor;
    }
}
```

**自定义多租户拦截器（本项目实现）：**

本项目实现了 `MyBatisTenantLineInnerInterceptor`，支持忽略特定 Statement：

```java
public class MyBatisTenantLineInnerInterceptor extends TenantLineInnerInterceptor {
    
    private List<String> IGNORE_STATEMENT_NAMES = new ArrayList<>();
    
    @Override
    public void beforeQuery(...) {
        // 如果 statementId 在忽略列表中，跳过租户处理
        if (this.getIgnoreStatementNames().contains(ms.getId())) {
            return;
        }
        super.beforeQuery(...);
    }
}
```

**使用示例：**

```java
// 所有查询自动添加租户条件
// SELECT * FROM user WHERE tenant_id = 'tenant001' AND name = '张三'
List<User> users = userMapper.selectList(
    new QueryWrapper<User>().eq("name", "张三")
);

// 忽略租户的表（通过配置）
// SELECT * FROM sys_config  (不添加租户条件)
List<Config> configs = configMapper.selectList(null);
```

**租户处理器实现：**

```java
@Component
public class MybatisIgnoreTenantLineHandler implements IgnoreTenantLineHandler {
    
    @Override
    public Expression getTenantId() {
        String tenantId = TenantContext.getCurrentTenantId();
        if (tenantId == null) {
            throw new RuntimeException("无法获取当前租户ID");
        }
        return new StringValue(tenantId);
    }
    
    @Override
    public boolean ignoreTable(String tableName) {
        // 根据表名判断是否忽略租户
        return isIgnoreByTableName(tableName);
    }
    
    // 支持通过注解配置忽略租户
    public boolean isIgnoreByTableName(String tableName) {
        // 检查表是否有 @TenantIgnore 注解
        // ...
    }
}
```

---

### 3. BlockAttackInnerInterceptor - 防止全表更新/删除拦截器

**功能：** 防止执行没有 WHERE 条件的 UPDATE 或 DELETE 语句，避免误操作导致全表数据被修改或删除。

**使用场景：**
- 生产环境安全防护
- 防止误操作
- 代码审查辅助

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建防止攻击拦截器
        BlockAttackInnerInterceptor blockAttackInterceptor = new BlockAttackInnerInterceptor();
        
        // 添加防止攻击拦截器
        interceptor.addInnerInterceptor(blockAttackInterceptor);
        
        return interceptor;
    }
}
```

**拦截的 SQL 示例：**

```sql
-- ❌ 会被拦截，抛出异常
UPDATE user SET status = 1;
DELETE FROM user;

-- ✅ 正常执行
UPDATE user SET status = 1 WHERE id = 1;
DELETE FROM user WHERE id = 1;
```

**异常信息：**

```
com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: 
禁止全表更新/删除操作，请添加 WHERE 条件
```

---

### 4. IllegalSQLInnerInterceptor - 非法SQL拦截器

**功能：** 检测并拦截非法的 SQL 语句，如包含危险关键字、语法错误等。

**使用场景：**
- SQL 注入防护
- 代码安全检查
- 开发环境调试

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建非法SQL拦截器
        IllegalSQLInnerInterceptor illegalSQLInterceptor = new IllegalSQLInnerInterceptor();
        
        // 添加非法SQL拦截器
        interceptor.addInnerInterceptor(illegalSQLInterceptor);
        
        return interceptor;
    }
}
```

**拦截规则：**

- 检测 SQL 注入关键字
- 检测危险操作（如 DROP、TRUNCATE 等）
- 检测语法错误

**使用建议：**

- 生产环境建议启用
- 开发环境可以关闭以提高调试效率

---

### 5. DataPermissionInterceptor - 数据权限拦截器

**功能：** 根据用户权限自动在 SQL 中添加数据权限过滤条件。

**使用场景：**
- 基于角色的数据访问控制（RBAC）
- 部门数据隔离
- 行级数据权限控制

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建数据权限拦截器
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        
        // 设置数据权限处理器
        dataPermissionInterceptor.setDataPermissionHandler(new DataPermissionHandler() {
            @Override
            public Expression getSqlSegment(WhereSegment whereSegment, String mappedStatementId) {
                // 根据当前用户权限返回过滤条件
                String userId = UserContext.getCurrentUserId();
                String deptId = UserContext.getCurrentDeptId();
                
                // 返回权限过滤条件，例如：dept_id = 'dept001' OR creator_id = 'user001'
                return new EqualsTo(new Column("dept_id"), new StringValue(deptId));
            }
        });
        
        // 添加数据权限拦截器
        interceptor.addInnerInterceptor(dataPermissionInterceptor);
        
        return interceptor;
    }
}
```

**使用示例：**

```java
// 自动添加数据权限条件
// SELECT * FROM order WHERE dept_id = 'dept001' AND status = 'pending'
List<Order> orders = orderMapper.selectList(
    new QueryWrapper<Order>().eq("status", "pending")
);
```

---

### 6. DataChangeRecorderInnerInterceptor - 数据变更记录拦截器

**功能：** 自动记录数据变更历史，支持审计日志功能。

**使用场景：**
- 数据变更审计
- 操作日志记录
- 数据恢复支持

**配置示例：**

```java
@Configuration
public class MyBatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 创建数据变更记录拦截器
        DataChangeRecorderInnerInterceptor dataChangeRecorder = 
            new DataChangeRecorderInnerInterceptor();
        
        // 设置变更记录处理器
        dataChangeRecorder.setDataChangeRecorder(new DataChangeRecorder() {
            @Override
            public void record(DataChangeEvent event) {
                // 记录数据变更事件
                log.info("数据变更: 表={}, 操作={}, 变更前={}, 变更后={}", 
                    event.getTableName(),
                    event.getOperation(),
                    event.getBeforeData(),
                    event.getAfterData()
                );
                
                // 保存到数据库或发送到消息队列
                dataChangeLogService.save(event);
            }
        });
        
        // 添加数据变更记录拦截器
        interceptor.addInnerInterceptor(dataChangeRecorder);
        
        return interceptor;
    }
}
```

**记录内容：**

- 表名
- 操作类型（INSERT、UPDATE、DELETE）
- 变更前的数据
- 变更后的数据
- 操作时间
- 操作人

---

### 7. BaseMultiTableInnerInterceptor - 多表操作拦截器

**功能：** 多表操作拦截器的基类，用于处理涉及多个表的复杂 SQL 操作。

**使用场景：**
- 多表关联查询
- 跨表数据操作
- 复杂业务逻辑处理

**说明：**

这是一个抽象基类，通常不直接使用，而是用于扩展自定义的多表操作拦截器。

**扩展示例：**

```java
public class CustomMultiTableInterceptor extends BaseMultiTableInnerInterceptor {
    
    @Override
    protected void processSelect(Statement statement, int index, String sql, Object obj) {
        // 处理 SELECT 语句
        if (statement instanceof Select) {
            Select select = (Select) statement;
            // 添加自定义逻辑
        }
    }
    
    @Override
    protected void processUpdate(Statement statement, int index, String sql, Object obj) {
        // 处理 UPDATE 语句
    }
    
    @Override
    protected void processDelete(Statement statement, int index, String sql, Object obj) {
        // 处理 DELETE 语句
    }
}
```

---

## 配置示例

### 完整配置示例

```java
package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 拦截器配置
 * 
 * 通过 saas.server.mybatis.enabled=true 启用
 */
@Configuration
@ConditionalOnProperty(prefix = "saas.server.mybatis", name = "enabled", havingValue = "true", matchIfMissing = false)
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 1. 多租户拦截器（最先执行）
        interceptor.addInnerInterceptor(new MyBatisTenantLineInnerInterceptor());
        
        // 2. 数据权限拦截器（可选）
        // interceptor.addInnerInterceptor(new DataPermissionInterceptor());
        
        // 3. 分页拦截器
        interceptor.addInnerInterceptor(new MybatisPageInnerInterceptor());
        
        // 4. 防止全表更新/删除拦截器
        BlockAttackInnerInterceptor blockAttackInterceptor = new BlockAttackInnerInterceptor();
        interceptor.addInnerInterceptor(blockAttackInterceptor);
        
        // 5. 非法SQL拦截器（可选，生产环境建议启用）
        // IllegalSQLInnerInterceptor illegalSQLInterceptor = new IllegalSQLInnerInterceptor();
        // interceptor.addInnerInterceptor(illegalSQLInterceptor);
        
        // 6. 数据变更记录拦截器（可选）
        // DataChangeRecorderInnerInterceptor dataChangeRecorder = new DataChangeRecorderInnerInterceptor();
        // interceptor.addInnerInterceptor(dataChangeRecorder);
        
        return interceptor;
    }

    @Bean
    public IgnoreTenantLineHandler tenantLineHandler() {
        return new MybatisIgnoreTenantLineHandler();
    }
}
```

### 配置文件示例

```yaml
# application.yml
saas:
  server:
    mybatis:
      enabled: true  # 启用 MyBatis-Plus 拦截器

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: true
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.suven.framework.**.entity
```

---

## 最佳实践

### 1. 拦截器执行顺序

**推荐顺序：**

```
1. TenantLineInnerInterceptor        (多租户)
2. DataPermissionInterceptor        (数据权限)
3. PaginationInnerInterceptor        (分页)
4. BlockAttackInnerInterceptor       (防止攻击)
5. IllegalSQLInnerInterceptor         (非法SQL检测)
6. DataChangeRecorderInnerInterceptor (数据变更记录)
```

### 2. 性能优化建议

- **分页拦截器：** 设置合理的 `maxLimit`，防止单次查询数据量过大
- **多租户拦截器：** 合理配置忽略表，避免不必要的 SQL 解析
- **数据权限拦截器：** 缓存权限规则，减少重复计算

### 3. 安全建议

- **生产环境必须启用：** `BlockAttackInnerInterceptor`
- **生产环境建议启用：** `IllegalSQLInnerInterceptor`
- **多租户系统必须启用：** `TenantLineInnerInterceptor`

### 4. 开发调试

- 开发环境可以关闭部分拦截器以提高调试效率
- 使用条件注解 `@ConditionalOnProperty` 控制拦截器启用

---

## 常见问题

### Q1: 分页拦截器不生效？

**A:** 检查以下几点：
1. 确保参数类型是 `IPage` 或其子类
2. 确保拦截器已正确添加到 `MybatisPlusInterceptor`
3. 检查 Mapper 方法签名是否正确

### Q2: 多租户拦截器导致查询结果为空？

**A:** 可能原因：
1. 租户ID未正确设置到上下文
2. 表结构缺少租户字段
3. 租户字段名配置错误

**解决方案：**
```java
// 确保在查询前设置租户ID
TenantContext.setCurrentTenantId("tenant001");
List<User> users = userMapper.selectList(null);
```

### Q3: 如何忽略特定表的租户处理？

**A:** 两种方式：

**方式1：** 在 `TenantLineHandler.ignoreTable()` 中配置
```java
@Override
public boolean ignoreTable(String tableName) {
    return "sys_config".equals(tableName);
}
```

**方式2：** 使用 `@TenantIgnore` 注解（本项目支持）
```java
@TenantIgnore(reason = "系统配置表不需要租户隔离")
@TableName("sys_config")
public class SysConfig {
    // ...
}
```

### Q4: 如何自定义分页限制？

**A:** 修改 `MybatisPageInnerInterceptor` 的 `DEFAULT_PAGE_LIMIT` 常量，或通过配置注入：

```java
@Bean
public MybatisPageInnerInterceptor mybatisPageInnerInterceptor(
    @Value("${mybatis.page.default-limit:1000}") long defaultLimit) {
    MybatisPageInnerInterceptor interceptor = new MybatisPageInnerInterceptor();
    interceptor.setMaxLimit(defaultLimit);
    return interceptor;
}
```

### Q5: 拦截器执行顺序如何控制？

**A:** 拦截器按照添加到 `MybatisPlusInterceptor` 的顺序执行：

```java
MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
interceptor.addInnerInterceptor(interceptor1); // 先执行
interceptor.addInnerInterceptor(interceptor2); // 后执行
```

---

## 相关资源

- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [MyBatis-Plus GitHub](https://github.com/baomidou/mybatis-plus)
- [项目配置类](../MyBatisPlusConfig.java)
- [自定义分页拦截器](../MybatisPageInnerInterceptor.java)
- [自定义多租户拦截器](../MyBatisTenantLineInnerInterceptor.java)

---

**文档版本：** 1.0  
**最后更新：** 2025-11-21  
**维护者：** Future Framework Team


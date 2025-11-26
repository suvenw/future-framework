package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Mybatis插件拦截器，用于处理多租户问题
 *
 * 1.如果系统处理登录状态，可以通过用户的状态获取tenantId来路由到指定的数据库，并自动拼接上tenantId来区分租户信息
 *
 * 2.如果是定时任务、触发事件，由于无法获取状态，需要自行完成租户信息的隔离
 *
 *  MyBatis 允许你在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
 *  前面是允许用插件拦截的类名，括号里是允许用插件拦截的方法名
 Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 ParameterHandler (getParameterObject, setParameters)
 ResultSetHandler (handleResultSets, handleOutputParameters)
 StatementHandler (prepare, parameterize, batch, update, query)
 在项目中配置和引入MyBatis-Plus框架和TenantLineInnerInterceptor，您可以按照以下步骤进行操作：

 添加MyBatis-Plus和相关依赖：在您的项目构建工具（如Maven或Gradle）的配置文件中，添加MyBatis-Plus和相关依赖。以下是一个Maven示例：
 xml
 复制
 <!-- MyBatis-Plus核心依赖 -->
 <dependency>
 <groupId>com.baomidou</groupId>
 <artifactId>mybatis-plus-boot-starter</artifactId>
 <version>最新版本</version>
 </dependency>

 <!-- MyBatis-Plus分页插件依赖 -->
 <dependency>
 <groupId>com.baomidou</groupId>
 <artifactId>mybatis-plus-extension</artifactId>
 <version>最新版本</version>
 </dependency>
 请确保将最新版本替换为MyBatis-Plus和分页插件的实际最新版本。

 配置数据源和MyBatis-Plus：在您的项目配置文件（如application.properties或application.yml）中，配置数据库连接信息和MyBatis-Plus相关配置。以下是一个示例：
 yaml
 复制
 spring:
 datasource:
 url: jdbc:mysql://localhost:3306/mydatabase
 username: your_username
 password: your_password

 mybatis-plus:
 mapper-locations: classpath:mapper/*.xml
 type-aliases-package: com.example.entity
 在上面的示例中，我们配置了数据库连接信息和MyBatis-Plus的一些基本配置，如Mapper文件的位置和实体类的包路径。

 创建MyBatis-Plus配置类：创建一个Java配置类，用于配置MyBatis-Plus和TenantLineInnerInterceptor。以下是一个示例：

 在上面的示例中，我们创建了一个名为MyBatisPlusConfig的配置类，并注入了MybatisPlusInterceptor作为Bean。在这个Bean中，我们添加了分页插件PaginationInnerInterceptor和租户拦截器TenantLineInnerInterceptor作为内部拦截器。

 启用MyBatis-Plus和TenantLineInnerInterceptor：根据您的项目架构，您可能需要在启动类或其他配置类中启用MyBatis-Plus和TenantLineInnerInterceptor。以下是一个示例：
 在上面的示例中，我们使用@MapperScan注解指定了Mapper接口的扫描路径，以便MyBatis-Plus能够自动扫描和注册这些接口。

 配置租户字段和租户ID获取方法：根据您的需求，您需要配置租户字段和租户ID获取方法。您可以创建一个实现ITenantLineHandler接口的自定义租户处理器，并在步骤3的MyBatisPlusConfig配置类中添加该处理器。
 这些步骤完成后，您的项目将配置和引入了MyBatis-Plus框架和TenantLineInnerInterceptor。您可以使用@Autowired注解注入相应的Mapper接口，并在查询中使用QueryWrapper来构建条件，从而实现多租户的数据隔离功能。
 */
@Configuration
@ConditionalOnProperty(prefix = "saas.server.mybatis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyBatisPlusConfig {

    /**
     * MyBatis-Plus 拦截器配置
     * 
     * <p>包含分页插件，多租户插件通过条件配置类单独管理</p>
     * 
     * <p>注意：使用 BeanFactory 来延迟查找租户拦截器，避免在禁用多租户时加载相关类</p>
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(BeanFactory beanFactory) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new MybatisPageInnerInterceptor());
        
        // 如果启用了多租户功能，将租户拦截器添加到最前面（多租户应该最先执行）
        // 使用 BeanFactory 来延迟查找，避免在禁用时加载类
        try {
            if (beanFactory.containsBean("tenantLineInnerInterceptor")) {
                Object tenantInterceptor = beanFactory.getBean("tenantLineInnerInterceptor");
                // 使用反射或直接转换，确保类型安全
                if (tenantInterceptor instanceof com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor) {
                    interceptor.addInnerInterceptor((com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor) tenantInterceptor);
                }
            }
        } catch (Exception e) {
            // 如果租户拦截器不存在或加载失败，忽略错误，继续使用分页插件
            // 这确保了在禁用多租户时不会影响其他功能
        }
        return interceptor;
    }
}
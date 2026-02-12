package com.suven.framework.upload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集成测试基类
 * ===============================
 *
 * 提供完整的 Spring Boot 集成测试支持
 *
 * 功能特性：
 * 1. Spring Boot 上下文加载
 * 2. 事务管理 - 测试数据自动回滚
 * 3. 数据库访问测试支持
 * 4. 服务层完整功能测试
 * 5. 多数据源测试支持
 *
 * 测试配置：
 * - @SpringBootTest           : 加载完整应用上下文
 * - @ActiveProfiles("test")   : 激活测试配置
 * - @Transactional           : 事务回滚保证数据隔离
 *
 * 使用示例：
 * <pre>
 * {@code
 * @ExtendWith(SpringExtension.class)
 * @SpringBootTest(classes = Application.class)
 * @ActiveProfiles("test")
 * @Transactional
 * class UserServiceIntegrationTest extends AbstractIntegrationTest {
 *
 *     @Autowired
 *     private UserService userService;
 *
 *     @Autowired
 *     private UserRepository userRepository;
 *
 *     @Test
 *     void testCreateUser() {
 *         // given
 *         UserRequestDTO request = createUserRequest();
 *
 *         // when
 *         UserResponseDTO result = userService.createUser(request);
 *
 *         // then
 *         assertNotNull(result.getId());
 *         assertEquals(request.getName(), result.getName());
 *     }
 * }
 * }
 * </pre>
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
        com.suven.framework.upload.UploadApplication.class
})
@ActiveProfiles("test")
@Transactional
public abstract class AbstractIntegrationTest {

    /**
     * 日志实例
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试执行前的初始化操作
     */
    @BeforeEach
    void setUp() {
        beforeEach();
    }

    /**
     * 子类可重写的初始化方法
     */
    protected void beforeEach() {
        logger.info("集成测试初始化完成");
    }

    /**
     * 记录测试开始日志
     *
     * @param testName 测试方法名称
     */
    protected void logTestStart(String testName) {
        logger.info("========== 集成测试开始: {} ==========", testName);
    }

    /**
     * 记录测试结束日志
     *
     * @param testName 测试方法名称
     */
    protected void logTestEnd(String testName) {
        logger.info("========== 集成测试结束: {} ==========", testName);
    }
}

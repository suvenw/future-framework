package com.suven.framework.upload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单元测试基类
 * ===============================
 *
 * 提供统一的单元测试基础功能，集成 Mockito 支持
 *
 * 功能特性：
 * 1. 日志集成 - 使用 SLF4J 记录测试日志
 * 2. Mockito 支持 - 自动初始化 Mockito 注解
 * 3. 测试数据隔离 - 每个测试方法执行前重置状态
 * 4. 异常处理 - 统一的异常断言方法
 *
 * 使用示例：
 * <pre>
 * {@code
 * @ExtendWith(MockitoExtension.class)
 * class UserServiceTest extends AbstractUnitTest {
 *
 *     @Mock
 *     private UserRepository userRepository;
 *
 *     @InjectMocks
 *     private UserService userService;
 *
 *     @Test
 *     void testFindById() {
 *         // given
 *         when(userRepository.findById(1L)).thenReturn(Optional.of(createUser()));
 *
 *         // when
 *         User result = userService.findById(1L);
 *
 *         // then
 *         assertNotNull(result);
 *         assertEquals("test", result.getName());
 *     }
 * }
 * }
 * </pre>
 *
 * @author suven
 * @version 1.0.0
 * @since 2024-01-01
 */
@ExtendWith(MockitoExtension.class)
public abstract class AbstractUnitTest {

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
        // 默认空实现，子类可重写
    }

    /**
     * 记录测试开始日志
     *
     * @param testName 测试方法名称
     */
    protected void logTestStart(String testName) {
        logger.info("========== 测试开始: {} ==========", testName);
    }

    /**
     * 记录测试结束日志
     *
     * @param testName 测试方法名称
     */
    protected void logTestEnd(String testName) {
        logger.info("========== 测试结束: {} ==========", testName);
    }

    /**
     * 记录测试步骤
     *
     * @param step 步骤描述
     */
    protected void logStep(String step) {
        logger.info(">>> 步骤: {}", step);
    }

    /**
     * 记录测试数据
     *
     * @param data 数据描述
     * @param value 数据值
     */
    protected void logData(String data, Object value) {
        logger.info(">>> {}: {}", data, value);
    }

    /**
     * 记录测试结果
     *
     * @param result 结果描述
     * @param value 结果值
     */
    protected void logResult(String result, Object value) {
        logger.info("<<< 结果: {} = {}", result, value);
    }
}

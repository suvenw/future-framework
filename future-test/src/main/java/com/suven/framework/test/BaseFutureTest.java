package com.suven.framework.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Future Framework 基础测试类
 * 所有测试类应继承此类
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class BaseFutureTest {

    @BeforeEach
    void setUp() {
        beforeEach();
    }

    @AfterEach
    void tearDown() {
        afterEach();
    }

    /**
     * 子类可覆盖此方法，在每个测试方法前执行
     */
    protected void beforeEach() {
        // 默认空实现
    }

    /**
     * 子类可覆盖此方法，在每个测试方法后执行
     */
    protected void afterEach() {
        // 默认空实现
    }
}

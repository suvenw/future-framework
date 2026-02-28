package com.suven.framework.test.utils;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * 测试数据生成工具
 */
public class TestDataUtils {

    private static final Faker FAKER = new Faker(Locale.CHINA);
    private static final Random RANDOM = new Random();

    /**
     * 生成随机字符串
     */
    public static String randomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成随机整数
     */
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * 生成随机金额
     */
    public static BigDecimal randomAmount() {
        return BigDecimal.valueOf(RANDOM.nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 生成随机手机号
     */
    public static String randomPhone() {
        return "1" + (3 + RANDOM.nextInt(7)) + String.format("%09d", RANDOM.nextInt(1000000000));
    }

    /**
     * 生成随机邮箱
     */
    public static String randomEmail() {
        return FAKER.internet().emailAddress();
    }

    /**
     * 生成随机姓名
     */
    public static String randomName() {
        return FAKER.name().fullName();
    }

    /**
     * 生成当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 生成随机日期
     */
    public static LocalDate randomDate() {
        return LocalDate.now().minusDays(randomInt(0, 365));
    }
}

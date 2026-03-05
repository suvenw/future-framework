package com.suven.framework.util.ids;


import com.suven.framework.util.bean.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 全局 ID 枚举类
 * <p>
 * 用于管理分布式 ID 生成器的 workerId 和 dataCenterId 配置
 * 实现雪花算法（Snowflake）中的机器标识符（workerId）和数据中心标识符（dataCenterId）
 * </p>
 * <p>
 * 功能特性：
 * <ul>
 *   <li>预定义常用的业务模块 ID 配置</li>
 *   <li>确保 workerId 唯一性，避免冲突</li>
 *   <li>支持按 workerId 快速查询对应的枚举值</li>
 *   <li>静态初始化时自动检查重复配置</li>
 * </ul>
 * </p>
 * <p>
 * 雪花算法说明：
 * <ul>
 *   <li>workerId：工作机器 ID（0-31），用于标识不同的机器或实例</li>
 *   <li>dataCenterId：数据中心 ID（0-31），用于标识不同的数据中心或机房</li>
 *   <li>workerId 和 dataCenterId 的组合必须全局唯一</li>
 * </ul>
 * </p>
 *
 * @author suven.wang
 * @version V1.0
 * @see IGlobalId
 * @see GlobalId
 */
@SuppressWarnings({"unused", "unchecked"})
public enum GlobalIdEnum implements IGlobalId {


        /**
         * 用户模块
         * <p>用于用户相关业务的 ID 生成</p>
         */
        USER(1, 1, "用户"),

        /**
         * 验证模块
         * <p>用于 OAuth 认证、登录验证等相关业务的 ID 生成</p>
         */
        OAUTH(2, 1, "验证"),

        /**
         * 资源模块
         * <p>用于系统资源、权限等相关业务的 ID 生成</p>
         */
        RESOURCE(3, 1, "资源"),

        /**
         * 支付模块
         * <p>用于支付、订单支付等相关业务的 ID 生成</p>
         */
        PAY(4, 1, "支付"),

        /**
         * 资产模块
         * <p>用于资产、账户余额等相关业务的 ID 生成</p>
         */
        ASSETS(5, 1, "资产"),

        /**
         * 设备模块
         * <p>用于移动端设备、PAD 等设备相关业务的 ID 生成</p>
         */
        PAD(6, 1, "设备"),

        /**
         * 订单模块
         * <p>用于业务订单、交易订单等相关业务的 ID 生成</p>
         */
        ORDER(7, 1, "订单"),

        /**
         * 活动模块
         * <p>用于营销活动、促销活动等相关业务的 ID 生成</p>
         */
        ACTIVITY(8, 1, "活动"),

        /**
         * 桌面模块
         * <p>用于桌面应用、工作台等相关业务的 ID 生成</p>
         */
        DESKTOP(9, 1, "桌面"),

        /**
         * 配置模块
         * <p>用于系统配置、参数配置等相关业务的 ID 生成</p>
         */
        CONFIG(10, 1, "配置"),

        /**
         * 定时任务模块
         * <p>用于定时任务、调度任务等相关业务的 ID 生成</p>
         */
        TASK(11, 1, "定时任务"),

        /**
         * 消息队列模块
         * <p>用于 MQ 消息、异步任务等相关业务的 ID 生成</p>
         */
        MQ(12, 1, "消息队列"),

        /**
         * 第三方服务模块
         * <p>用于第三方接口、外部服务调用等相关业务的 ID 生成</p>
         */
        THREE(13, 1, "第三方服务"),

        /**
         * 应用市场模块
         * <p>用于应用市场、应用管理等相关业务的 ID 生成</p>
         */
        MARKET(14, 1, "应用市场"),

        /**
         * 日志模块
         * <p>用于系统日志、操作日志等相关业务的 ID 生成</p>
         */
        LOG(15, 1, "日志"),

        /**
         * 资源库模块
         * <p>用于资源库、文件存储等相关业务的 ID 生成</p>
         */
        DEPOT(16, 1, "资源库")
        ;
        /**
         * 工作机器 ID（Worker ID）
         * <p>
         * 用于标识不同的机器或实例，范围：0-31（最大 32 个）
         * 在雪花算法中占 5 位，二进制表示为 00000-11111
         * </p>
         * <p>
         * 注意：必须在同一个数据中心内保持唯一性
         * </p>
         */
        private int workerId;

        /**
         * 数据中心 ID（Data Center ID）
         * <p>
         * 用于标识不同的数据中心或机房，范围：0-31（最大 32 个）
         * 在雪花算法中占 5 位，二进制表示为 00000-11111
         * </p>
         * <p>
         * 注意：必须在同一个系统中保持全局唯一性
         * </p>
         */
        private int dataCenterId;

        /**
         * 项目名称
         * <p>
         * 用于标识业务模块或项目的名称，便于日志记录和调试
         * </p>
         */
        private String projectName;

        /**
         * 日志记录器
         * <p>
         * 用于记录枚举初始化过程中的错误信息和调试信息
         * </p>
         */
        private static final Logger logger = LoggerFactory.getLogger(GlobalIdEnum.class);

        /**
         * Worker ID 到枚举值的映射缓存
         * <p>
         * 用于快速通过 workerId 查询对应的 IGlobalId 实例
         * Key: workerId
         * Value: IGlobalId 实现类实例
         * </p>
         */
        private static final Map<Integer, IGlobalId> CLIENT_TYPE_ENUM_MAP = new HashMap<>();

        /**
         * 构造函数
         * <p>
         * 初始化枚举常量，设置 workerId、dataCenterId 和 projectName
         * </p>
         *
         * @param workerId     工作机器 ID，范围 0-31
         * @param dataCenterId 数据中心 ID，范围 0-31
         * @param projectName  项目名称，用于业务标识
         */
        GlobalIdEnum(int workerId, int dataCenterId, String projectName) {
            this.workerId = workerId;
            this.dataCenterId = dataCenterId;
            this.projectName = projectName;
        }

        /**
         * 获取工作机器 ID
         *
         * @return workerId 值，范围 0-31
         */
        @Override
        public int getWorkerId() {
            return workerId;
        }

        /**
         * 获取数据中心 ID
         *
         * @return dataCenterId 值，范围 0-31
         */
        @Override
        public int getDataCenterId() {
            return dataCenterId;
        }

        /**
         * 获取项目名称
         *
         * @return 项目名称字符串
         */
        @Override
        public String getProjectName() {
            return projectName;
        }

    /**
     * 静态初始化块
     * <p>
     * 在类加载时执行，用于：
     * <ul>
     *   <li>扫描所有实现 IGlobalId 接口的枚举类</li>
     *   <li>验证所有枚举常量的 workerId 是否唯一</li>
     *   <li>构建 workerId 到枚举实例的映射缓存</li>
     * </ul>
     * </p>
     * <p>
     * 如果发现重复的 workerId，会记录错误日志并抛出 RuntimeException
     * </p>
     */
    static {
        // 获取所有实现 IGlobalId 接口的枚举类
        List<Enum> list = EnumUtils.getEnumListByInterfaceClass(IGlobalId.class);

        // 如果存在实现类，则进行存在性检查
        if (list != null && !list.isEmpty()) {
            checkExist(list);
        }
    }




    /**
     * 检查枚举常量的唯一性并构建映射缓存
     * <p>
     * 功能：
     * <ul>
     *   <li>检查所有枚举常量的 workerId 是否重复</li>
     *   <li>如果发现重复，记录错误日志并抛出异常</li>
     *   <li>构建 workerId 到 IGlobalId 实例的映射缓存</li>
     * </ul>
     * </p>
     *
     * @param list 实现 IGlobalId 接口的所有枚举常量列表
     * @throws RuntimeException 如果发现重复的 workerId
     */
    @SuppressWarnings("unchecked")
    private static void checkExist(List<Enum> list) {
        // 用于检测重复 workerId 的临时集合
        Set<Integer> checkCodeSet = new HashSet<>();

        if (list != null && !list.isEmpty()) {
            for (Enum msg : list) {
                IGlobalId imsg = (IGlobalId) msg;

                // 将枚举实例存入映射缓存
                CLIENT_TYPE_ENUM_MAP.put(imsg.getWorkerId(), imsg);

                // 检查 workerId 是否重复
                if (checkCodeSet.contains(imsg.getWorkerId())) {
                    logger.error("IGlobalId 存在重复配置，IGlobalId: [{}] workerId: [{}] 和 dataCenterId: [{}] 已存在",
                            imsg, imsg.getWorkerId(), imsg.getDataCenterId());
                    throw new RuntimeException("IGlobalId 存在重复配置，IGlobalId: " + imsg +
                            " workerId: " + imsg.getWorkerId() + " 和 dataCenterId: " +
                            imsg.getDataCenterId() + " 已存在");
                }

                // 记录已检查的 workerId
                checkCodeSet.add(imsg.getWorkerId());
            }

            // 释放临时集合
            checkCodeSet = null;
        }
    }
    /**
     * 根据 workerId 获取对应的 IGlobalId 实例
     * <p>
     * 从缓存映射中查找指定 workerId 对应的枚举常量
     * </p>
     *
     * @param workerId 要查询的工作机器 ID，范围 0-31
     * @return 对应的 IGlobalId 实例，如果不存在则返回 null
     *
     * @apiNote 示例用法：
     * <pre>{@code
     * // 获取用户模块的配置
     * IGlobalId userConfig = GlobalIdEnum.getClientTypeEnum(1);
     * System.out.println(userConfig.getProjectName()); // 输出: 用户
     *
     * // 获取订单模块的配置
     * IGlobalId orderConfig = GlobalIdEnum.getClientTypeEnum(7);
     * System.out.println(orderConfig.getProjectName()); // 输出: 订单
     * }</pre>
     */
    public static IGlobalId getClientTypeEnum(long workerId) {
        return CLIENT_TYPE_ENUM_MAP.get((int) workerId);
    }
        
}
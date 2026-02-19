package com.suven.framework.config;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Nacos 服务注册配置
 * 用于在应用启动完成后手动触发服务注册
 */
@Configuration
public class NacosRegistryConfig implements ApplicationRunner {

    @Autowired(required = false)
    private NacosAutoServiceRegistration nacosAutoServiceRegistration;

    @PostConstruct
    public void init() {
        System.out.println("NacosRegistryConfig 初始化完成");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner 执行 - 检查 Nacos 注册状态");
        
        if (nacosAutoServiceRegistration != null) {
            System.out.println("NacosAutoServiceRegistration 存在，检查是否已启动");
            try {
                // 尝试手动触发注册
                nacosAutoServiceRegistration.start();
                System.out.println("Nacos 服务注册已触发");
            } catch (Exception e) {
                System.err.println("Nacos 服务注册失败: " + e.getMessage());
            }
        } else {
            System.err.println("NacosAutoServiceRegistration 不存在，无法注册服务");
        }
    }
}

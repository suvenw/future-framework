package com.suven.framework.dubbo;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.metadata.MetadataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;


@EnableDiscoveryClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
         KafkaAutoConfiguration.class})
@EnableDubbo(scanBasePackages = {"com.suven"})
public class DubboServiceMain {
    public static void main(String[] args)  throws Exception{
        ApplicationContext application = SpringApplication.run(DubboServiceMain.class, args);
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("example-app");
        return applicationConfig;
    }

//    @Bean
//    public DubboMetadataServiceExporter dubboMetadataServiceExporter(MetadataService metadataService, ApplicationConfig applicationConfig) {
//        DubboMetadataServiceExporter exporter = new DubboMetadataServiceExporter();
//        exporter.setMetadataService(metadataService);
//        exporter.setApplicationConfig(applicationConfig);
//        return exporter;
//    }

}
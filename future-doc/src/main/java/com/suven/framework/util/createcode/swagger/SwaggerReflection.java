package com.suven.framework.util.createcode.swagger;

import com.suven.framework.http.api.DocConstants;
import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-12-23
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  接口文档扫描类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ConditionalOnProperty(name = DocConstants.TOP_SERVER_API_ENABLED,  matchIfMissing = false)
@AutoConfigureBefore({SwaggerReflectionsDoc.class})
@Configuration
public class SwaggerReflection {

    private Logger logger = LoggerFactory.getLogger(SwaggerReflection.class);

    @Autowired
    private ApplicationContext applicationContext;

    private Reflections reflections ;


//    @Bean("initReflections")
    @ConditionalOnMissingBean
    @PostConstruct
    public Reflections initReflections() {
        logger.info(" -------------Swagger initReflections document  start init --------- PostConstruct -------------"  );
        Map<String, ApiPackages> apiPackagesMap = applicationContext.getBeansOfType(ApiPackages.class);
        Set<String> packageList  = new HashSet(16);
//        packageList.add("com");
        if(apiPackagesMap != null && !apiPackagesMap.isEmpty()){
            apiPackagesMap.values().forEach(com ->{
                if(null != com.getPackages()) {
                    String[] packages = com.getPackages().split(";|,");
                    packageList.addAll(Arrays.asList(packages));
                }
            });

        }
        reflections = new Reflections(packageList);
        return reflections;
    }

    @Bean("swaggerInfo")
    public SwaggerInfo initSwaggerInfo() {
        return SwaggerInfo.init();
    }

    public SwaggerServer getSwaggerServer(){
        try{
            String ip = InetAddress.getLocalHost().getHostAddress();
            Environment env = applicationContext.getEnvironment();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
           return SwaggerServer.build(path,ip,port);
        }catch(Exception e){
            logger.warn("SwaggerInfo init bean ");
        }
        return SwaggerServer.init();
    }

    public void initSwaggerInfo(SwaggerResultBean bean){
        SwaggerInfo swaggerInfo =   SwaggerInfo.init();
        String ip = "127.0.0.1";
        try{
            SwaggerInfo info = applicationContext.getBean(SwaggerInfo.class);
            if(info != null){
                swaggerInfo = info;
            }
        }catch(Exception e){
            logger.error("SwaggerInfo init bean ----------------------");
        }
        SwaggerServer  swaggerServer = this.getSwaggerServer();
        //修改访问地址信息;
        swaggerInfo.setTermsOfService(swaggerServer.getService());
        bean.setBasePath(swaggerServer.getPath()).setHost(swaggerServer.getHost()).setSwagger(swaggerInfo.getVersion());
        bean.setInfo(swaggerInfo);
    }

    public Reflections getReflections(){
        return this.reflections;
    }


}

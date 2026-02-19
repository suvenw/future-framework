package com.suven.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.inters.IProjectModule;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.http.jetty.AbstractJettyAppServer;

@EnableDiscoveryClient
@MapperScan(basePackages="com.suven.framework.*.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
@ComponentScan(basePackages={GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES})
//@NacosPropertySource( groupId= SERVICE_NAME, dataId = TOP_SERVER_HTTP_FILE_NAME,   autoRefreshed = true)
public class FileMain extends AbstractJettyAppServer {
    public static void main(String[] args) {
        jettyStart(FileMain.class,args);
    }

    @Override
    protected IProjectModule getServiceName() {
        return ProjectModuleEnum.HTTP_FILE;
    }
}

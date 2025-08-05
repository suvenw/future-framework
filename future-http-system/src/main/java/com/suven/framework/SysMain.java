package com.suven.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.inters.IProjectModule;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.http.jetty.AbstractJettyAppServer;


@MapperScan(basePackages="com.suven.framework.*.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
@ComponentScan(basePackages={GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES})

public class SysMain extends AbstractJettyAppServer {
    public static void main(String[] args) {

        jettyStart(SysMain.class,args);
    }

    @Override
    protected IProjectModule getServiceName() {
        return ProjectModuleEnum.HTTP_SYSTEM;
    }
}
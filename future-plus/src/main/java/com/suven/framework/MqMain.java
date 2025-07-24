package com.suven.framework;


import com.suven.framework.http.inters.IProjectModule;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.core.jetty.AbstractRpcServerApplication;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */
public class MqMain extends AbstractRpcServerApplication {

    public static void main(String[] args) {
        buildRpc(MqMain.class,args);
    }

    @Override
    protected IProjectModule getServiceName() {
        return ProjectModuleEnum.HTTP_DEFAULT;
    }
}

package com.suven.framework.http.inters;



import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * Description:
 * @Author lixiangling
 * date 2018/6/28 11:57
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * --------------------------------------------------------
 */
public enum ProjectModuleEnum implements IProjectModule, ProjectModuleParameter {
    ALL(0,MODULE_ALL,MODULE_ALL, SERVER_HTTP_TYPE),

    HTTP_DEFAULT(PORT_HTTP_DEFAULT,MODULE_DEFAULT,MODULE_DEFAULT, SERVER_HTTP_TYPE),

    HTTP_USER( PORT_HTTP_USER,MODULE_NAME_USER,MODULE_HTTP_USER , SERVER_HTTP_TYPE),

    HTTP_OAUTH( PORT_HTTP_OAUTH,MODULE_NAME_OAUTH, MODULE_HTTP_OAUTH , SERVER_HTTP_TYPE),

    HTTP_FILE( PORT_HTTP_FILE,MODULE_NAME_FILE, MODULE_HTTP_FILE , SERVER_HTTP_TYPE),

    HTTP_RESOURCE( PORT_HTTP_RESOURCE,MODULE_NAME_RESOURCE, MODULE_HTTP_RESOURCE , SERVER_HTTP_TYPE),

    HTTP_CONFIG( PORT_HTTP_CONFIG,MODULE_NAME_CONFIG, MODULE_HTTP_CONFIG , SERVER_HTTP_TYPE),

    HTTP_VIDEO(PORT_HTTP_VIDEO, MODULE_NAME_VIDEO, MODULE_HTTP_VIDEO , SERVER_HTTP_TYPE),

    HTTP_ORDER( PORT_HTTP_ORDER,MODULE_NAME_ORDER, MODULE_HTTP_ORDER , SERVER_HTTP_TYPE),

    HTTP_PAY(PORT_HTTP_PAY, MODULE_NAME_PAY, MODULE_HTTP_PAY , SERVER_HTTP_TYPE),

    HTTP_ASSETS( PORT_HTTP_ASSETS,MODULE_NAME_ASSETS, MODULE_HTTP_ASSETS , SERVER_HTTP_TYPE),

    HTTP_ACTIVITY( PORT_HTTP_ACTIVITY,MODULE_NAME_ACTIVITY, MODULE_HTTP_ACTIVITY , SERVER_HTTP_TYPE),

    HTTP_GIFT( PORT_HTTP_GIFT,MODULE_NAME_GIFT, MODULE_HTTP_GIFT , SERVER_HTTP_TYPE),

    HTTP_GOODS( PORT_HTTP_GOODS,MODULE_NAME_GOODS, MODULE_HTTP_GOODS , SERVER_HTTP_TYPE),


    HTTP_MEDIA(PORT_HTTP_MEDIA,MODULE_NAME_MEDIA, MODULE_HTTP_MEDIA , SERVER_HTTP_TYPE),

    HTTP_STATISTIC(PORT_HTTP_STATISTIC,MODULE_NAME_STATISTIC, MODULE_HTTP_STATISTIC, SERVER_HTTP_TYPE),

    HTTP_SYSTEM(PORT_HTTP_SYSTEM,MODULE_NAME_SYSTEM, MODULE_HTTP_SYSTEM, SERVER_HTTP_TYPE),



    /** RPC 服务类型 **/

    RPC_USER( PORT_RPC_USER ,MODULE_NAME_USER ,MODULE_RPC_USER , SERVER_RPC_TYPE),

    RPC_OAUTH( PORT_RPC_OAUTH,MODULE_NAME_OAUTH,MODULE_RPC_OAUTH , SERVER_RPC_TYPE),


    RPC_FILE( PORT_RPC_FILE ,MODULE_NAME_FILE ,MODULE_RPC_FILE , SERVER_RPC_TYPE),

    RPC_RESOURCE( PORT_RPC_RESOURCE ,MODULE_NAME_RESOURCE ,MODULE_RPC_RESOURCE , SERVER_RPC_TYPE),

    RPC_CONFIG( PORT_RPC_CONFIG ,MODULE_NAME_CONFIG ,MODULE_RPC_CONFIG , SERVER_RPC_TYPE),

    RPC_VIDEO(PORT_RPC_VIDEO ,MODULE_NAME_VIDEO, MODULE_RPC_VIDEO , SERVER_RPC_TYPE),

    RPC_ORDER( PORT_RPC_ORDER ,MODULE_NAME_ORDER,MODULE_RPC_ORDER , SERVER_RPC_TYPE),

    RPC_PAY(PORT_RPC_PAY ,MODULE_NAME_PAY, MODULE_RPC_PAY , SERVER_RPC_TYPE),

    RPC_ASSETS( PORT_RPC_ASSETS  ,MODULE_NAME_ASSETS,MODULE_RPC_ASSETS , SERVER_RPC_TYPE),

    RPC_ACTIVITY( PORT_RPC_ACTIVITY ,MODULE_NAME_ACTIVITY,MODULE_RPC_ACTIVITY , SERVER_RPC_TYPE),

    RPC_GIFT( PORT_RPC_GIFT ,MODULE_NAME_GIFT,MODULE_RPC_GIFT , SERVER_RPC_TYPE),

    RPC_GOODS( PORT_RPC_GOODS,MODULE_NAME_GOODS, MODULE_RPC_GOODS , SERVER_RPC_TYPE),

    PRC_MEDIA(PORT_RPC_MEDIA ,MODULE_NAME_MEDIA,MODULE_RPC_MEDIA, SERVER_RPC_TYPE),




;


    private int port;
    private String module;
    private String serverName;
    private int serverType;

    private static Map<String, ProjectModuleEnum> enumMap = new HashMap<>();
    static {

        for(ProjectModuleEnum type : values()) {
            enumMap.put(type.serverName, type);
        }
    }

    ProjectModuleEnum(int port ,String module, String serverName,int serverType){
        this.port = port;
        this.module = module;
        this.serverName = serverName;
        this.serverType = serverType;
    }

    public boolean isRpc(){
        return this.serverType ==  SERVER_RPC_TYPE ? true : false;
    }

    public static IProjectModule getModuleServerNameEnum(String serverName){
        ProjectModuleEnum moduleEnum =  enumMap.get(serverName) ;
        moduleEnum = moduleEnum == null ? HTTP_DEFAULT : moduleEnum;
        return moduleEnum;
    }
    public String getModule() {
        return module;
    }
    public int getPort() {
        return port;
    }

    public String getServerName() {
        return serverName;
    }

    public String getConfigModulePort() {
       String configModule = this.module;
        StringBuilder sb =  new StringBuilder();
        if(isRpc()){
            sb.append(CONFIG_RPC);
        }else {
            sb.append(CONFIG_HTTP);
        }
        return  sb.append(configModule).toString();
    }

    public String getRunModuleServerName() {
        StringBuilder sb =  new StringBuilder();
        if(isRpc()){
            sb.append(SERVER_RPC);
        }else {
            sb.append(SERVER_HTTP);
        }
        return  sb.append(module).toString();
    }




}

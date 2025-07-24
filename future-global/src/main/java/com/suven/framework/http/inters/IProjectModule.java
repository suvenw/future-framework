package com.suven.framework.http.inters;



public interface IProjectModule {

    /** 是否RPC类型服务 **/
    boolean isRpc();

    /** 微服务的模块名称字符 eg:"user/order" **/
    String getModule();

    /**
     * 代码分配的项目端口号,用于main启动调试项目使用;具体实现流程如下:
     * 默认1. 先按java sh启动脚本配置的端口号,2.当端口号小于0,再按项目配置文件配置的端口,3.当端口号还是小0, 按代码编写分配的端口号;
     * @return
     */
    int getPort() ;

    /** 微服务的模块,通过模块名在配置文件获取端口的值
     *  实现规则: eg: top.server.port.rpc.user ,top.server.port.http.user
     *  top.server.port 为前缀,
     *  rpc 为项目类型,rpc/http
     *  user 为项目的模块名称;
     * **/

    String getConfigModulePort();

    /** 微服务的模块,通过模块名在配置文件获取运行名称的值
     *  实现规则: eg: top.server.port.rpc.user ,top.server.port.http.user
     *  top.server.port 为前缀,
     *  rpc 为项目类型,rpc/http
     *  user 为项目的模块名称;
     * **/
    String getRunModuleServerName();



}

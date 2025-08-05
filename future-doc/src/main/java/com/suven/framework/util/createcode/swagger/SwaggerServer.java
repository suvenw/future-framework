package com.suven.framework.util.createcode.swagger;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-12-23
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): 文档服务信息;
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class SwaggerServer {

    private  String path = "";
    private String ip = "";
    private   String port = "";
    private   String host = "";
    private   String service = "";



    public static SwaggerServer build(){
        return new SwaggerServer();
    }

    public static SwaggerServer build(String path, String ip, String port) {
        if(!path.startsWith("/")){
            path = "/"+path;
        }
        SwaggerServer ss = build();
        ss.path = path;
        ss.ip = ip;
        ss.port = port;
        ss.service= ip+":"+port+path;
        ss.host = ip+":"+port;
        return ss;
    }
    public static SwaggerServer init() {

        return build("api", "127.0.0.1","8080");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}

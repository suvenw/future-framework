package com.suven.framework.http.data.entity;


/**
 * @author Joven.wang
 * @version V1.0
 * Title: GlobalServiceInfo.java
 * date 2019年2月20日
 * Description: TODO(说明)
 * 用于记录登前启动服务的 ip地址,port 端口号,url 访问地址; serviceName 服务名称的实体Bean类;
 */
public class GlobalServiceInfo {

    private static GlobalServiceInfo instance;

    private String ip;
    private String port;
    private String path;
    private String serviceName;

    private GlobalServiceInfo(){

    }

    public static GlobalServiceInfo getInstance(){
        if(instance == null){
            instance = new  GlobalServiceInfo();
        }
        return instance;
    }

    public void init(String ip, int port, String path) {
        this.ip = ip;
        this.port = String.valueOf(port);
        this.path = path;
    }



    public String getIp() {
        return ip;
    }

    public GlobalServiceInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getPort() {
        return port;
    }

    public GlobalServiceInfo setPort(String port) {
        this.port = port;
        return this;
    }

    public String getPath() {
        return path;
    }

    public GlobalServiceInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public GlobalServiceInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }
}

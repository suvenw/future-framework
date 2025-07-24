package com.suven.framework.core.jetty;

import com.suven.framework.util.bean.BeanUtil;

import java.io.Serializable;

/**
 * @Title: JettyWebServerFactory.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) Jeety web SERVER Rpc 服务公共实现类
 */
public class RpcBaseRequest implements Serializable {
    private static final long serialVersionUID = 4173393989648840004L;

    private int appId;     //客户端 XXX登录申请的appid
    private int version;        //版本
    private int platform ;    //平台
    private String device;   //设备标识
    private int channel;      //app第三方渠道号
    private String ip;
    private String imei;

    public static RpcBaseRequest build(){
        return new RpcBaseRequest();
    }

    public <T extends RpcBaseRequest> T clone(Object source) {
        BeanUtil.copyProperties(source, this);
        return (T)this;
    }


    public int getVersion() {
        return version;
    }

    public RpcBaseRequest setVersion(int version) {
        this.version = version;
        return this;
    }

    public int getPlatform() {
        return platform;
    }

    public RpcBaseRequest setPlatform(int platform) {
        this.platform = platform;
        return this;
    }

    public int getAppId() {
        return appId;
    }

    public RpcBaseRequest setAppId(int appId) {
        this.appId = appId;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public RpcBaseRequest setDevice(String device) {
        this.device = device;
        return this;
    }

    public int getChannel() {
        return channel;
    }

    public RpcBaseRequest setChannel(int channel) {
        this.channel = channel;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public RpcBaseRequest setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getImei() {
        return imei;
    }

    public RpcBaseRequest setImei(String imei) {
        this.imei = imei;
        return this;
    }
}

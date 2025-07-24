package com.suven.framework.http.validator;



import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven.wang@ XXX.net
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 基于 Gaven LoadingCache 缓存实现软件版本号校验信息的Bean类;
 */



public class VersionHandlerVo implements Cloneable, IBeanClone, Serializable {



    private int platform;
    private int version;
    private int channelCode;
    private String device;
    private int forceUpdate;
    private String download;
    private String description;
    private Date publishDate;
    private String versionName;


    public static VersionHandlerVo build(){
        return new VersionHandlerVo();
    }

//    public VersionHandlerVo clone(Object source){
//        IBeanClone.super.clone(source);
//        return this;
//    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(int channelCode) {
        this.channelCode = channelCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean forceUpdate() {
        return forceUpdate == 1 ? true : false;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public VersionHandlerVo clone() {
        VersionHandlerVo version = null;
        try{
            version = (VersionHandlerVo)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return version;
    }

    @Override
    public String toString() {
        return "VersionVo{" +
                "platform=" + platform +
                ", version=" + version +
                ", channelCode=" + channelCode +
                ", device='" + device + '\'' +
                ", forceUpdate=" + forceUpdate +
                ", download='" + download + '\'' +
                ", description='" + description + '\'' +
                ", publishDate=" + publishDate +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}

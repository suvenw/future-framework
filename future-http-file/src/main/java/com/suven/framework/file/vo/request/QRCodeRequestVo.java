package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;


/**
 * ClassName: QRCodeUserInfoRequestVo.java
 * @author 作者 : suven
 * CreateDate 创建时间: 2021-08-03 14:19:17
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 http业务接口交互数据请求参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class QRCodeRequestVo extends RequestParserVo {


//    @ApiDesc(value =  "文件总大小",required = 1)
//    private long fileSize;

    @ApiDesc(value = "二维码基础地址，以前端传为主，默认值：https://www.baidu.com?xxx=11&yyy=2222", required = 1)
    private String qrUrl = "";
    /**
     * ossKey 上传key
     */
    @ApiDesc(value = "上传阿里云oss bucketName ,默认值为im", required = 0)
    private String ossKey = "im";

    @ApiDesc(value =  "阿里云文件自定义完整路径和名称字,若不传,则以prefixPath为前缀生成uuid文件,若传则以文件名保存 eg: im/head/userName.png",required = 0)
    private String ossPath;

    /**
     * fileType 文件格式
     */
    @ApiDesc(value = "文件格式（jpg / png）", required = 0)
    private String fileType = "png";


    @ApiDesc(value = "二维码中心图标logo：https://www.baidu.com/logo.png , 若没有,可以为空", required = 0)
    private String logoCodeUrl = "";

    @ApiDesc(value = "二维码宽度，以前端传为主，默认值：400", required = 0)
    private int width = 400;

    @ApiDesc(value = "二维码高度，以前端传为主，默认值：400", required = 0)
    private int height = 400;


    public static QRCodeRequestVo build() {
        return new QRCodeRequestVo();
    }

//
//    public long getFileSize() {
//        return fileSize;
//    }
//
//    public void setFileSize(long fileSize) {
//        this.fileSize = fileSize;
//    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getLogoCodeUrl() {
        return logoCodeUrl;
    }

    public void setLogoCodeUrl(String logoCodeUrl) {
        this.logoCodeUrl = logoCodeUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }
}

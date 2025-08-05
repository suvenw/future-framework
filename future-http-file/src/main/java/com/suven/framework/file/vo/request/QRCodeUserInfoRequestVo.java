package com.suven.framework.file.vo.request;


import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;


/**
 * @ClassName: QRCodeUserInfoRequestVo.java
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2021-08-03 14:19:17
 * @Version 版本: v1.0.0
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

public class QRCodeUserInfoRequestVo extends HttpRequestByIdVo {


    /**
     * user_name 昵称名称
     */
    @ApiDesc(value = "昵称名称", required = 0, isHide = 1)
    private String userName;

    /**
     * qr_code 验证码
     */
    @ApiDesc(value = "二维码信息", required = 0, isHide = 1)
    private String qrCode;


    /**
     * mobile 手机号
     */
    @ApiDesc(value = "手机号", required = 0, isHide = 1)
    private String mobile;

    /**
     * fileType 文件格式
     */
    @ApiDesc(value = "文件格式（jpg / png）", required = 0)
    private String fileType = "png";

    /**
     * folderName 文件夹名称
     */
    @ApiDesc(value = "文件夹名称，eg：im/20220904", required = 0)
    private String folderName = "";

    /**
     * ossPath 上传路径
     */
    @ApiDesc(value = "上传路径", required = 0)
    private String ossPath = "";

    /**
     * ossKey 上传key
     */
    @ApiDesc(value = "上传key", required = 0)
    private String ossKey = "";

    @ApiDesc(value = "二维码基础地址，以前端传为主，默认值：https://www.baidu.com", required = 0)
    private String qrUrl = "https://www.baidu.com";

    @ApiDesc(value = "二维码宽度，以前端传为主，默认值：400", required = 0)
    private int width = 400;

    @ApiDesc(value = "二维码高度，以前端传为主，默认值：400", required = 0)
    private int height = 400;


    public static QRCodeUserInfoRequestVo build() {
        return new QRCodeUserInfoRequestVo();
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public QRCodeUserInfoRequestVo toUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public QRCodeUserInfoRequestVo toQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public QRCodeUserInfoRequestVo toQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
        return this;
    }

    public String getQrUrl() {
        return this.qrUrl;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public QRCodeUserInfoRequestVo toMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMobile() {
        return this.mobile;
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

    public QRCodeUserInfoRequestVo toWidth(int width) {
        this.width = width;
        return this;
    }

    public QRCodeUserInfoRequestVo toHeight(int height) {
        this.height = height;
        return this;
    }


    public QRCodeUserInfoRequestVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public QRCodeUserInfoRequestVo toOssPath(String ossPath) {
        this.ossPath = ossPath;
        return this;
    }

    public QRCodeUserInfoRequestVo toOssKey(String ossKey) {
        this.ossKey = ossKey;
        return this;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public QRCodeUserInfoRequestVo toFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}

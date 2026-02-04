package com.suven.framework.fileinter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * description (说明): 一个appid可以对应多个clientId,一个clientId 对应多个业务场景;
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2023-12-19
 **/
@Setter@Getter
@Accessors(chain = true)
@TableName(value = "file_upload_use_business")
public class FileUploadUseBusiness extends BaseTenantEntity {

    // ===== 业务模块回调 ======
    @ApiDesc(value = "注册应用id", required = 0)
    private String appId; //appid 应用id
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private String clientId; //appid 授权id
    /** 业务公司id **/
    private String  companyId;
    /** 业务公司名称 **/
    private String companyName;
    /** 上传业务产品名称 **/
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;
    /** 上传业务名称 **/
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;
    /** 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式; **/
    @ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用同步方式;", required = 0)
    private String   callbackService;
    @ApiDesc(value = "1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方;", required = 0)
    /** 1条或批量准备数据接口 一种业务 fileBusinessName 对应一个具体的回调接口地址,使用异步方式; **/
    private String   callbackAsyncService;


    /** 回调数据格式, 如何为空时,post json 请求,否则to map<key,Value> 结果处理数据 **/
    @ApiDesc(value = "回调数据格式, 如何为空时,post json 请求,否则to map<key,Value> 结果处理数据 ", required = 0)
    private String dataForm;
    /** 回调数据格式 post/get **/
    @ApiDesc(value = "回调数据格式 post/get", required = 0)
    private String formType;

    /** 是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中**/
    @ApiDesc(value = "是否需要解释数据,根据文件类型实现解决,将数据存储到mongodb中", required = 0)
    private int interpretData;
    //=== 数据 回调业务方 == //
    /** 这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要 **/
    @ApiDesc(value = "这种业务场景的数据,是否要做数据检查: 0.不需要,1.需要", required = 0)
    private int checkData;

    @ApiDesc(value = "有效时间:当前时间+毫秒数,默认按app设计,也可以独立配置", required = 0)
    private String accessExpirationTime; //有效时间
    @ApiDesc(value = "url授权可访问次数,默认按app设计,也可以独立配置", required = 0)
    private int accessCount; // 可访问次数
    //sourcepath = http://app.img.abc.jpg, aesUrl = http://app.img.key.jpg
    //aes format = key=appId+clientId+clientSalt+time+@abc,
    @ApiDesc(value = "生成临时访问url规则", required = 0)
    private String accessUrlFormat;//

    public static FileUploadUseBusiness build(){
        return new FileUploadUseBusiness();
    }
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFileProductName() {
        return fileProductName;
    }

    public void setFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
    }

    public String getFileBusinessName() {
        return fileBusinessName;
    }

    public void setFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
    }

    public String getCallbackService() {
        return callbackService;
    }

    public void setCallbackService(String callbackService) {
        this.callbackService = callbackService;
    }

    public String getCallbackAsyncService() {
        return callbackAsyncService;
    }

    public void setCallbackAsyncService(String callbackAsyncService) {
        this.callbackAsyncService = callbackAsyncService;
    }

    public String getDataForm() {
        return dataForm;
    }

    public void setDataForm(String dataForm) {
        this.dataForm = dataForm;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public int getInterpretData() {
        return interpretData;
    }

    public void setInterpretData(int interpretData) {
        this.interpretData = interpretData;
    }

    public int getCheckData() {
        return checkData;
    }

    public void setCheckData(int checkData) {
        this.checkData = checkData;
    }

    public String getAccessExpirationTime() {
        return accessExpirationTime;
    }

    public void setAccessExpirationTime(String accessExpirationTime) {
        this.accessExpirationTime = accessExpirationTime;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }

    public String getAccessUrlFormat() {
        return accessUrlFormat;
    }

    public void setAccessUrlFormat(String accessUrlFormat) {
        this.accessUrlFormat = accessUrlFormat;
    }
}
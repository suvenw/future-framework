package com.suven.framework.upload.entity;

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
 * description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2023-12-19
 **/
@Setter@Getter
@Accessors(chain = true)
@TableName(value = "file_upload_logger")
public class FileUploadActionWater extends BaseTenantEntity {

    //谁,做了什么操作
    /** 业务公司id **/
    @ApiDesc(value = "业务公司id", required = 0)
    private String  companyId;
    /** 业务公司名称 **/
    @ApiDesc(value = "业务公司名称", required = 0)
    private String companyName;
    /** 业务公司人员的部门id **/
    @ApiDesc(value = "业务公司人员的部门id", required = 0)
    private long  deptId;
    /** 业务公司人员的部门名称 **/
    @ApiDesc(value = "业务公司人员的部门名称", required = 0)
    private String deptName;
    /** 上传人员的id**/
    @ApiDesc(value = "上传人员的id", required = 0)
    private long uploadUserId;
    /** 上传人员的名称 **/
    @ApiDesc(value = "上传人员的名称", required = 0)
    private String uploadUserName;
    // ===== 业务回调 ======
    /** 上传业务产品名称 **/
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;
    /** 上传业务名称 **/
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;


    @ApiDesc(value = "注册应用id", required = 0)
    private String appId; //appid 应用id
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private long clientId;
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;
    /**配置编号,FileStorageEnum: 10.本地,11.FTP,12.SFTP, 20.云存储或minio存储*/
    @ApiDesc(value = "注册应用授权Id,对应 FileStorageEnum", required = 0)
    private String storageConfigId;
    /** 文件存储信息id**/
    @ApiDesc(value = "文件存储信息id", required = 0)
    private long fileUploadStorageId;
    /** 文件名称,原来文件上传的名称**/
    @ApiDesc(value = "文件名称,原来文件上传的名称", required = 0)
    private String fileSourceName;
    @ApiDesc(value = "文件名称,存储到中间件名称,eg, 'abc.jpg' ", required = 0)
    private String fileOssName;

    public static FileUploadActionWater build(){
        return new FileUploadActionWater();
    }



}
package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * SaaS文件操作记录查询请求VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileOperationQueryVo extends HttpRequestByIdPageVo {

    /** 业务公司id */
    @ApiDesc(value = "业务公司id", required = 0)
    private String companyId;

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 0)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private String clientId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

    /** 功能类型 */
    @ApiDesc(value = "功能类型", required = 0)
    private String functionType;

    /** 操作状态 */
    @ApiDesc(value = "操作状态", required = 0)
    private String status;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileSourceName;

    /** 创建时间-开始 */
    @ApiDesc(value = "创建时间-开始", required = 0)
    private LocalDateTime createDateStart;
    
    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private LocalDateTime createDateEnd;

    public static FileOperationQueryVo build() {
        return new FileOperationQueryVo();
    }

    public FileOperationQueryVo toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public FileOperationQueryVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FileOperationQueryVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public FileOperationQueryVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public FileOperationQueryVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public FileOperationQueryVo toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public FileOperationQueryVo toStatus(String status) {
        this.status = status;
        return this;
    }

    public FileOperationQueryVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public FileOperationQueryVo toCreateDateStart(LocalDateTime createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public FileOperationQueryVo toCreateDateEnd(LocalDateTime createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }
}

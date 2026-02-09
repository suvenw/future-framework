package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * SaaS平台文件查询请求VO
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaaSFileQueryRequestVo extends HttpRequestByIdPageVo {

    /** 注册应用id */
    @ApiDesc(value = "注册应用id", required = 1)
    private String appId;

    /** 注册应用授权Id */
    @ApiDesc(value = "注册应用授权Id", required = 1)
    private String clientId;

    /** 使用业务Id */
    @ApiDesc(value = "使用业务Id", required = 0)
    private long useBusinessId;

    /** 上传业务产品名称 */
    @ApiDesc(value = "上传业务产品名称", required = 0)
    private String fileProductName;

    /** 上传业务名称 */
    @ApiDesc(value = "上传业务名称", required = 0)
    private String fileBusinessName;

    /** 文件名称 */
    @ApiDesc(value = "文件名称", required = 0)
    private String fileSourceName;

    /** 文件类型 */
    @ApiDesc(value = "文件类型", required = 0)
    private String fileType;

    /** 文件MD5 */
    @ApiDesc(value = "文件MD5", required = 0)
    private String fileMd5;

    /** 创建时间-开始 */
    @ApiDesc(value = "创建时间-开始", required = 0)
    private Date createDateStart;

    /** 创建时间-结束 */
    @ApiDesc(value = "创建时间-结束", required = 0)
    private Date createDateEnd;

    public static SaaSFileQueryRequestVo build() {
        return new SaaSFileQueryRequestVo();
    }

    public SaaSFileQueryRequestVo toAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public SaaSFileQueryRequestVo toClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SaaSFileQueryRequestVo toUseBusinessId(long useBusinessId) {
        this.useBusinessId = useBusinessId;
        return this;
    }

    public SaaSFileQueryRequestVo toFileProductName(String fileProductName) {
        this.fileProductName = fileProductName;
        return this;
    }

    public SaaSFileQueryRequestVo toFileBusinessName(String fileBusinessName) {
        this.fileBusinessName = fileBusinessName;
        return this;
    }

    public SaaSFileQueryRequestVo toFileSourceName(String fileSourceName) {
        this.fileSourceName = fileSourceName;
        return this;
    }

    public SaaSFileQueryRequestVo toFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public SaaSFileQueryRequestVo toFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public SaaSFileQueryRequestVo toCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
        return this;
    }

    public SaaSFileQueryRequestVo toCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }
}

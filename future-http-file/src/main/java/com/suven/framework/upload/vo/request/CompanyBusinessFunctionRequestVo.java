package com.suven.framework.upload.vo.request;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByPageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公司业务功能申请请求VO
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyBusinessFunctionRequestVo extends HttpRequestByPageVo {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiDesc(value = "ID", required = 0)
    private Long id;

    // ==================== 公司/租户信息 ====================

    /** 公司ID */
    @ApiDesc(value = "公司ID", required = 1)
    private String companyId;

    /** 公司名称 */
    @ApiDesc(value = "公司名称", required = 0)
    private String companyName;

    // ==================== 业务配置信息 ====================

    /** 平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口 */
    @ApiDesc(value = "平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口", required = 1)
    private String platformType;

    /** 业务编码 */
    @ApiDesc(value = "业务编码", required = 1)
    private String businessCode;

    /** 业务名称 */
    @ApiDesc(value = "业务名称", required = 1)
    private String businessName;

    /** 功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载 */
    @ApiDesc(value = "功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载", required = 1)
    private String functionType;

    /** 业务唯一码 */
    @ApiDesc(value = "业务唯一码（系统自动生成）", required = 0)
    private String businessUniqueCode;

    /** 业务回调地址 */
    @ApiDesc(value = "业务回调地址，用于通知业务方文件处理完成", required = 0)
    private String callbackUrl;

    /** 业务查询地址 */
    @ApiDesc(value = "业务查询地址，业务方根据此地址分批查询数据", required = 0)
    private String queryUrl;

    /** 访问方式: GET, POST, PUT, DELETE */
    @ApiDesc(value = "访问方式: GET, POST, PUT, DELETE", required = 0)
    private String accessMethod;

    /** 状态: ACTIVE-启用, INACTIVE-停用, PENDING-待激活 */
    @ApiDesc(value = "状态: ACTIVE-启用, INACTIVE-停用, PENDING-待激活", required = 0)
    private String businessStatus;

    /** 是否需要数据检查: 0-否, 1-是 */
    @ApiDesc(value = "是否需要数据检查: 0-否, 1-是", required = 0)
    private Integer checkData;

    /** 是否需要异步回调: 0-否, 1-是 */
    @ApiDesc(value = "是否需要异步回调: 0-否, 1-是", required = 0)
    private Integer needCallback;

    /** 备注信息 */
    @ApiDesc(value = "备注信息", required = 0)
    private String remark;

    // ==================== 查询条件 ====================

    /** 创建时间开始 */
    @ApiDesc(value = "创建时间开始", required = 0)
    private LocalDateTime createDateStart;

    /** 创建时间结束 */
    @ApiDesc(value = "创建时间结束", required = 0)
    private LocalDateTime createDateEnd;
}

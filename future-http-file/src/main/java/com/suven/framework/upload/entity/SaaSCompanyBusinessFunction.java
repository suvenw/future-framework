package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SaaS公司业务功能信息表
 *
 * 功能：记录租户/公司维度下的业务上传功能配置，包括平台、业务、功能类型、
 *       业务唯一码、业务回调地址、业务查询地址和访问方式等。
 *
 * 与现有 SaaS 上传方案的关系：
 * - 通过 businessUniqueCode 将本表与 SaaSFileInterpretRecord 串联，
 *   作为解释结果与业务配置之间的桥梁；
 * - 业务侧在申请上传/解释能力时，先在本表登记一条配置记录，
 *   上传与回调流程根据 businessUniqueCode 进行绑定与路由。
 *
 * 对应表: saas_company_business_function
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "saas_company_business_function")
public class SaaSCompanyBusinessFunction extends BaseTenantEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 公司/租户信息 ====================

    /** 公司ID */
    @ApiDesc(value = "公司ID", required = 1)
    private String companyId;

    /** 公司名称 */
    @ApiDesc(value = "公司名称", required = 0)
    private String companyName;

    // ==================== 业务配置信息 ====================

    /** 平台类型: WEB-网页, APP-APP, MINI-小程序, API-接口 */
    @ApiDesc(value = "平台类型", required = 1)
    private String platformType;

    /** 业务编码 */
    @ApiDesc(value = "业务编码", required = 1)
    private String businessCode;

    /** 业务名称 */
    @ApiDesc(value = "业务名称", required = 1)
    private String businessName;

    /** 功能类型: IMPORT-导入, EXPORT-导出, UPLOAD-上传, DOWNLOAD-下载 */
    @ApiDesc(value = "功能类型", required = 1)
    private String functionType;

    /** 申请的业务唯一码，用于在上传/回调流程中与解释记录串联 */
    @ApiDesc(value = "业务唯一码", required = 1)
    private String businessUniqueCode;

    /** 业务回调地址，用于通知业务方文件解释完成及处理进度 */
    @ApiDesc(value = "业务回调地址", required = 0)
    private String callbackUrl;

    /** 业务查询地址，业务方根据此地址分批查询解释后的数据 */
    @ApiDesc(value = "业务查询地址", required = 0)
    private String queryUrl;

    /** 访问方式: GET, POST, PUT, DELETE */
    @ApiDesc(value = "访问方式", required = 0)
    private String accessMethod;

    /** 状态: ACTIVE-启用, INACTIVE-停用 */
    @ApiDesc(value = "状态", required = 0)
    private String status;

    /** 备注信息 */
    @ApiDesc(value = "备注", required = 0)
    private String remark;

    // ==================== 构建辅助方法 ====================

    public static SaaSCompanyBusinessFunction build() {
        return new SaaSCompanyBusinessFunction();
    }

    public SaaSCompanyBusinessFunction toCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public SaaSCompanyBusinessFunction toCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public SaaSCompanyBusinessFunction toPlatformType(String platformType) {
        this.platformType = platformType;
        return this;
    }

    public SaaSCompanyBusinessFunction toBusinessCode(String businessCode) {
        this.businessCode = businessCode;
        return this;
    }

    public SaaSCompanyBusinessFunction toBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public SaaSCompanyBusinessFunction toFunctionType(String functionType) {
        this.functionType = functionType;
        return this;
    }

    public SaaSCompanyBusinessFunction toBusinessUniqueCode(String businessUniqueCode) {
        this.businessUniqueCode = businessUniqueCode;
        return this;
    }

    public SaaSCompanyBusinessFunction toCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public SaaSCompanyBusinessFunction toQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
        return this;
    }

    public SaaSCompanyBusinessFunction toAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
        return this;
    }

    public SaaSCompanyBusinessFunction toStatus(String status) {
        this.status = status;
        return this;
    }

    public SaaSCompanyBusinessFunction toRemark(String remark) {
        this.remark = remark;
        return this;
    }
}


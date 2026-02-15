package com.suven.framework.upload.service;

import com.suven.framework.upload.entity.CompanyBusinessFunction;

/**
 * SaaS公司业务功能信息服务接口
 *
 * 负责业务功能配置（saas_company_business_function）的基础查询能力，
 * 主要用于在上传/解释/回调流程中通过 businessUniqueCode 关联业务配置。
 */
public interface CompanyBusinessFunctionService {

    /**
     * 根据业务唯一码获取业务功能配置
     *
     * @param businessUniqueCode 业务唯一码
     * @return SaaSCompanyBusinessFunction 配置记录，可能为 null
     */
    CompanyBusinessFunction getByBusinessUniqueCode(String businessUniqueCode);
}


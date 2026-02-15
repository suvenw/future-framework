package com.suven.framework.upload.dto.enums;

/**
 * SaaS 公司业务功能信息查询条件枚举
 *
 * 按照项目现有 Repository 规范，统一通过枚举描述不同的查询组合。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
public enum CompanyBusinessFunctionQueryEnum {

    /**
     * 按 ID 降序排序（默认）
     */
    DESC_ID,

    /**
     * 根据 business_unique_code 查询
     */
    BY_BUSINESS_UNIQUE_CODE,

    /**
     * 根据 company_id 查询，按 ID 降序
     */
    BY_COMPANY_ID_DESC,

    /**
     * 根据 platform_type 查询，按 ID 降序
     */
    BY_PLATFORM_TYPE_DESC,

    /**
     * 根据 function_type 查询，按 ID 降序
     */
    BY_FUNCTION_TYPE_DESC,

    /**
     * 根据 status 查询，按 ID 降序
     */
    BY_STATUS_DESC,
    ;
}

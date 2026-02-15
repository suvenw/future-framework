package com.suven.framework.upload.dto.enums;

/**
 * SaaS 文件下载记录查询条件枚举
 *
 * 按照项目现有 Repository 规范，统一通过枚举描述不同的查询组合。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
public enum FileDownloadRecordQueryEnum {

    /**
     * 按 ID 降序排序（默认）
     */
    DESC_ID,

    /**
     * 根据 business_unique_code 查询，按 ID 降序
     */
    BY_BUSINESS_UNIQUE_CODE_DESC,

    /**
     * 根据 business_function_id 查询，按 ID 降序
     */
    BY_BUSINESS_FUNCTION_ID_DESC,

    /**
     * 根据 generate_status 查询，按 ID 降序
     */
    BY_GENERATE_STATUS_DESC,
    ;
}

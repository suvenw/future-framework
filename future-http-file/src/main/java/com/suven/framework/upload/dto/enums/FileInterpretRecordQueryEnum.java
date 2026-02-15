package com.suven.framework.upload.dto.enums;

/**
 * SaaS 文件解释记录查询条件枚举
 *
 * 按照项目现有 Repository 规范（如 FileDataDetailedQueryEnum），
 * 统一通过枚举描述不同的查询组合。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
public enum FileInterpretRecordQueryEnum {

    /**
     * 按 ID 降序排序（默认）
     */
    DESC_ID,

    /**
     * 根据 file_upload_id 查询，按 ID 降序
     */
    BY_FILE_UPLOAD_ID_DESC,

    /**
     * 根据 file_upload_id 和 interpret_status 查询，business_process_status = PENDING，按 ID 降序
     */
    BY_FILE_UPLOAD_ID_AND_STATUS_PENDING_DESC,

    /**
     * 根据 business_function_id 查询，按 ID 降序
     */
    BY_BUSINESS_FUNCTION_ID_DESC,

    /**
     * 根据 business_unique_code 查询，按 ID 降序
     */
    BY_BUSINESS_UNIQUE_CODE_DESC,

    /**
     * 根据 business_unique_code 和 interpret_key 查询
     */
    BY_BUSINESS_UNIQUE_CODE_AND_INTERPRET_KEY,

    /**
     * 根据 interpret_key 查询
     */
    BY_INTERPRET_KEY,
    ;
}

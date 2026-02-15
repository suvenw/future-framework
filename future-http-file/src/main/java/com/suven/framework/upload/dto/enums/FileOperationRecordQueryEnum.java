package com.suven.framework.upload.dto.enums;

/**
 * SaaS 文件操作记录查询条件枚举
 *
 * 按照项目现有 Repository 规范（如 FileInterpretRecordQueryEnum），
 * 统一通过枚举描述不同的查询组合。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
public enum FileOperationRecordQueryEnum {

    /**
     * 按 ID 降序排序（默认）
     */
    DESC_ID,

    /**
     * 根据 file_upload_storage_id 查询，按 ID 降序
     */
    BY_FILE_UPLOAD_STORAGE_ID_DESC,

    /**
     * 根据 status 查询，按 ID 降序
     */
    BY_STATUS_DESC,

    /**
     * 根据 company_id 查询，按 ID 降序
     */
    BY_COMPANY_ID_DESC,

    /**
     * 根据 app_id 查询，按 ID 降序
     */
    BY_APP_ID_DESC,

    /**
     * 根据 client_id 查询，按 ID 降序
     */
    BY_CLIENT_ID_DESC,

    /**
     * 根据 use_business_id 查询，按 ID 降序
     */
    BY_USE_BUSINESS_ID_DESC,

    /**
     * 根据 callback_status 查询，按 ID 降序
     */
    BY_CALLBACK_STATUS_DESC,
    ;
}

package com.suven.framework.upload.dto.enums;

/**
 * SaaS 文件字段映射查询条件枚举
 *
 * 按照项目现有 Repository 规范（如 FileDataDetailedQueryEnum），
 * 统一通过枚举描述不同的查询组合。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
public enum FileFieldMappingQueryEnum {

    /**
     * 根据 operation_record_id 查询，并按 sort_order 升序
     */
    BY_OPERATION_ID_ORDER_BY_SORT,
    ;
}

